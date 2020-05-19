package com.prism.db;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.io.FileUtils;
import org.springframework.context.ApplicationContext;

import com.alibaba.fastjson.JSON;
import com.prism.aop.AOP;

public class Prism {
	private List<Map<String, String>> dblist = new ArrayList<Map<String, String>>();
	private ApplicationContext context;
	private String aop;
	private String dataSource;
	private String s_return;

	private String id;


	
	private Map<String, Object> begin(String id, Map<String, Object> param) {

		try {
			if (aop != null) {

				AOP a = (AOP) context.getBean(aop);
				return a.run(id,param);

			}

		} catch (SecurityException e) {
			e.printStackTrace();

		}
		return param;
	}

	private Object getChache(String id, String depend, Map<String, Object> param) {
		if ("".equals(depend)) {
			return null;
		}
		String path = this.getClass().getResource("/").toString().substring(6);
		String jParam = JSON.toJSONString(param);
		String md5 = getMD5(jParam);
		String fname = String.format("%s_%s_%s", getId(), id, md5);
		File fdata = new File(path + "cache/data");
		File fd = new File(fdata, fname);
		if (fd.exists()) {
			try {
				String content = FileUtils.readFileToString(fd);
				@SuppressWarnings("unchecked")
				Map<String, Object> m = JSON.parseObject(content, Map.class);
				return m.get("result");
			} catch (IOException e) {
				return null;
			}
		} else {
			return null;
		}

	}

	private void delChache(String depend) {
		String[] tbls = depend.split("|");

		String path = this.getClass().getResource("/").toString().substring(6);

		File fdata = new File(path + "cache/data");
		for (int i = 0; i < tbls.length; i++) {
			String tbl = tbls[i];
			File ftbl = new File(path + "cache/table/" + tbl);

			File[] files = ftbl.listFiles();
			if (files != null) {
				for (File f : files) {
					String fname = f.getName();
					File fd = new File(fdata, fname);
					fd.delete();
					f.delete();
				}
			}

		}
	}

	private void saveChache(String id, String depend, Map<String, Object> param, Map<String, Object> tparam,
			Object result) {

		if ("".equals(depend)) {
			return;
		}

		String[] tbls = depend.split("\\|");
		String path = this.getClass().getResource("/").toString().substring(6);
		String jParam = JSON.toJSONString(param);
		String fname = String.format("%s_%s_%s", getId(), id, getMD5(jParam));
		Map<String, Object> save = new HashMap<String, Object>();
		save.put("where", tparam);
		save.put("result", result);
		String jResult = JSON.toJSONString(save);

		File fdata = new File(path + "cache/data");
		if (!fdata.exists()) {
			fdata.mkdirs();
		}
		for (int i = 0; i < tbls.length; i++) {
			String tbl = tbls[i];
			File ftbl = new File(path + "cache/table/" + tbl);
			if (!ftbl.exists()) {
				ftbl.mkdirs();
			}
			File fd = new File(ftbl, fname);
			try {
				fd.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		try {
			File fd = new File(fdata, fname);
			FileWriter fw = new FileWriter(fd, false);
			fw.write(jResult);
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String getMD5(String str) {
		try {
			// 生成一个MD5加密计算摘要
			MessageDigest md = MessageDigest.getInstance("MD5");
			// 计算md5函数
			md.update(str.getBytes());
			return new BigInteger(1, md.digest()).toString(16);
		} catch (Exception e) {
			e.printStackTrace();
			return "err";
		}
	}

	public Map<String, Object> db(Map<String, Object> param) {
		if ("".equals(dataSource)) {
			dataSource = "dataSource";
		}

		BasicDataSource ds = (BasicDataSource) context.getBean(dataSource);
		DB db = new DB(ds);

		Map<String, Object> result = new HashMap<String, Object>();
		try {
			db.setConn();
			db.setAutoCommit(false);
			Map<String, Object> tmap = new HashMap<String, Object>();
			tmap.putAll(param);

			for (Map<String, String> m : dblist) {
				String sql = m.get("sql");
				String type = m.get("type");
				String id = m.get("id");
				String key = m.get("key");
				// 依赖
				String depend = m.get("depend");

				sql = sql.replaceAll("\\$str\\(", "\\$p\\.\\_str\\(\\$");
				sql = sql.replaceAll("\\$int\\(", "\\$p\\.\\_int\\(\\$");

				Map<String, Object> b = begin(id, param);
				if (!b.isEmpty()) {
					tmap.putAll(b);
				}

				Object obj;

				// String jParam = JSON.toJSONString(param);
				// String md5 =getMD5(jParam);
				// System.out.println("line204="+jParam);

				switch (type) {
				case "select":

					obj = getChache(id, depend, param);

					if (obj == null) {
						obj = db.select(sql, tmap);
						saveChache(id, depend, param, tmap, obj);
					}

					tmap.put(id, obj);
					result.put(id, obj);

					break;
				case "selectOne":
					obj = getChache(id, depend, param);
					if (obj == null) {
						obj = db.selectOne(sql, tmap);
						saveChache(id, depend, param, tmap, obj);
					}

					tmap.put(id, obj);
					result.put(id, obj);

					break;
				case "selectObj":
					obj = getChache(id, depend, param);
					if (obj == null) {
						obj = db.selectObj(sql, tmap);
						saveChache(id, depend, param, tmap, obj);
					}

					tmap.put(id, obj);
					result.put(id, obj);

					break;
				case "update":
					delChache(depend);
					obj = db.update(sql, tmap);
					tmap.put(id, obj);
					result.put(id, obj);
					break;
				case "delete":
					delChache(depend);
					obj = db.delete(sql, tmap);
					tmap.put(id, obj);
					result.put(id, obj);
					break;
				case "insert":
					boolean updateReturn = false;
					if ("true".equals(key)) {
						updateReturn = true;
					}
					delChache(depend);
					obj = db.insert(sql, tmap, updateReturn);
					tmap.put(id, obj);
					result.put(id, obj);
					break;
				default:
					break;
				}

			}
			db.commit();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		db.closeConn();
		return result;
	}

	public void setDblist(List<Map<String, String>> dblist) {
		this.dblist = dblist;
	}

	public void setContext(ApplicationContext context) {
		this.context = context;
	}

	public void setAop(String aop) {
		this.aop = aop;
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public String getReturn() {
		return s_return;
	}

	public void setReturn(String s_return) {
		this.s_return = s_return;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	private Map<String, Object> view;

	public void setView(Map<String, Object> view) {
		this.view = view;
	}

	public View view() {
		View v = new View(view);

		return v;
	}

	public Map<String, Object> getView() {

		return view;
	}

	public class View extends HashMap<String, Object> {
		public View(Map<String, Object> map) {
			this.putAll(map);
		}

		private static final long serialVersionUID = 7916861803334369542L;

		public String template() {
			return this.get("template") + "";
		}

		public String text() {
			return this.get("text") + "";
		}

		@SuppressWarnings("unchecked")
		public Map<String, String> var() {
			Map<String, String> var = new HashMap<String, String>();
			if (this.containsKey("var")) {
				var = (Map<String, String>) this.get("var");
			}
			return var;
		}
	}
}
