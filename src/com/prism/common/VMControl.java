package com.prism.common;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.context.ApplicationContext;

import com.prism.dbutil.DBCommand;
import com.prism.dbutil.DBConnection;
import com.prism.exception.DAOException;

/*
 * 控件生成器
 */
public class VMControl {
	private HttpServletRequest req;
	private Map<String, String> m_unit = new HashMap<String, String>();
	private ApplicationContext context;

	public VMControl() {
	};

	@SuppressWarnings("unchecked")
	public VMControl(HttpServletRequest req) {
		context = (ApplicationContext) req.getAttribute("context");
		m_unit = (Map<String, String>) context.getBean("m_unit");
		this.req = req;

		// System.out.println(reqMap);
	}

	private VelocityContext vc = new VelocityContext();

	public String control(Map<String, Object> map) {
		myString ms = new myString() {
			@Override
			public String get() {
				String html = m_unit.get(map.get("TYPE"));
				vc = new VelocityContext();
				for (Map.Entry<String, Object> en : map.entrySet()) {
					vc.put(en.getKey(), en.getValue());
				}
				return getResultfromContent(html);
			}
		};
		Map<String, myString> result = new HashMap<String, myString>();

		result.put("RADIO", new myString() {
			@Override
			public String get() {
				String html = m_unit.get(map.get("TYPE"));
				initData(map);
				vc = new VelocityContext();
				for (Map.Entry<String, Object> en : map.entrySet()) {
					vc.put(en.getKey(), en.getValue());
				}

				return getResultfromContent(html);
			}
		});
		result.put("SELECT", new myString() {
			@Override
			public String get() {
				String html = m_unit.get(map.get("TYPE"));

				initData(map);

				vc = new VelocityContext();
				for (Map.Entry<String, Object> en : map.entrySet()) {
					vc.put(en.getKey(), en.getValue());
				}
				return getResultfromContent(html);
			}
		});
		result.put("MSELECT", new myString() {
			@Override
			public String get() {
				String html = m_unit.get(map.get("TYPE"));

				initData(map);

				vc = new VelocityContext();
				for (Map.Entry<String, Object> en : map.entrySet()) {
					vc.put(en.getKey(), en.getValue());
				}
				return getResultfromContent(html);
			}
		});
		
		if (result.containsKey(map.get("TYPE"))) {
			return result.get(map.get("TYPE")).get();
		} else {
			return ms.get();
		}

	}

	@SuppressWarnings("unchecked")
	private void initData(Map<String, Object> map) {
		Map<String, Object> sourceMap = (Map<String, Object>) req.getAttribute("sourceMap");
		Map<String, Object> reqMap = (Map<String, Object>) req.getAttribute("reqMap");
		if (map.containsKey("DSQL")) {
			String action = "" + map.get("DSQL");
			String sql = sourceMap.get(action) + "";
			VMRequest v = new VMRequest();
			v.setReqMap(reqMap);
			vc = new VelocityContext();
			vc.put("v", v);
			String nSql = getResultfromContent(sql);
			List<Map<String, Object>> list = getList(nSql, reqMap);
			if (map.containsKey("ISNULL")) {
				list.add(0, new HashMap<String, Object>() {
					private static final long serialVersionUID = 1L;
					{
						put("K", map.get("ISNULL"));
						put("V", "请选择");

					}
				});
			}
			map.put("LIST", list);

		}
		if (map.containsKey("SQL")) {
			String action = "" + map.get("SQL");
			String sql = sourceMap.get(action) + "";
			List<Map<String, Object>> list = getList(sql, reqMap);
			if (map.containsKey("ISNULL")) {
				list.add(0, new HashMap<String, Object>() {
					private static final long serialVersionUID = 1L;
					{
						put("K", map.get("ISNULL"));
						put("V", "请选择");

					}
				});
			}
			map.put("LIST", list);
		}

	}

	private List<Map<String, Object>> getList(String sql, Map<String, Object> reqMap) {
		DBConnection dbConn = (DBConnection) req.getAttribute("DBConnection");
		DBCommand cmd = new DBCommand(dbConn);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if (reqMap.containsKey("@minnum") && reqMap.containsKey("@maxnum")) {
			int minnum = Integer.parseInt(reqMap.get("@minnum") + "");
			int maxnum = Integer.parseInt(reqMap.get("@maxnum") + "");

			try {
				list = cmd.executeSelect(sql, reqMap, minnum, maxnum);
			} catch (DAOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				list = cmd.executeSelect(sql, reqMap);
			} catch (DAOException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	interface myString {
		public String get();
	}

	public static void main(String[] args) {
		Map<String, Object> munit = new HashMap<String, Object>();
		munit.put("TYPE", "INPUT");
		munit.put("KEY", "cust_id");
		munit.put("VAL", "cust_id");

		String html = new VMControl().control(munit);
		System.out.println(html);
	}

	private String getResultfromContent(String s) {
		try {
			StringWriter stringwriter;
			Velocity.init();
			stringwriter = new StringWriter();
			Velocity.evaluate(vc, stringwriter, "mystring", s);
			return stringwriter.toString();
		} catch (Exception e) {
			System.out.println("VelTemplate.getVelstr=" + e);
			return null;
		}
	}
}
