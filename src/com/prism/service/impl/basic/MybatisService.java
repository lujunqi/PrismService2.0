/**
 * MyBatis操作数据库
 */
package com.prism.service.impl.basic;

import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.dom4j.Element;

import com.prism.exception.BMOException;
import com.prism.service.Service;

public class MybatisService implements Service {
	protected Map<String, Object> reqMap = new HashMap<String, Object>();
	private SqlSession sqlSession;
	private HttpServletRequest req;
	private HttpServletResponse res;
	protected VelocityContext vc = new VelocityContext();

	@SuppressWarnings("unchecked")
	public void service() throws ServletException, IOException {
		reqMap = (Map<String, Object>) req.getAttribute("reqMap");
		sqlSession = (SqlSession) req.getAttribute("sqlSession");
	}

	private String getSqlKey(String key, String type) {
		String sqlKey = (String) reqMap.get("_action");// (String)
														// sourceMap.get(key);
		if (sourceMap.get(key) instanceof String) {
			sqlKey = (String) sourceMap.get(key);
		} else if (sourceMap.get(key) instanceof Element) {
			Element el = (Element) sourceMap.get(key);
			sqlKey = el.element(type).attributeValue("id");
		}
		return sqlKey;
	}

	// SELECT
	protected List<Object> selectResult(String key) throws BMOException {

		String sqlKey = getSqlKey(key, "select");
		try {
			SqlSession sqlSession = getSession(key);
			// System.out.println(reqMap);
			if (reqMap.containsKey("@minnum") && reqMap.containsKey("@maxnum")) {
				int minnum = Integer.parseInt(reqMap.get("@minnum") + "");
				int maxnum = Integer.parseInt(reqMap.get("@maxnum") + "");
				List<Object> l = sqlSession.selectList(sqlKey, reqMap, new RowBounds(minnum, maxnum - minnum));
				sqlSession.close();
				return l;
			} else {
				List<Object> l = sqlSession.selectList(sqlKey, reqMap);
				sqlSession.close();
				return l;
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new BMOException(e);
		}
	}

	// UPDATE
	protected int updateResult(String key) throws BMOException {
		try {
			SqlSession sqlSession = getSession(key);
			String sqlKey = getSqlKey(key, "update");
//			String sqlKey = (String) sourceMap.get(key);
			int result = sqlSession.update(sqlKey, reqMap);
			sqlSession.commit();
			sqlSession.close();
			return result;

		} catch (IOException e) {
			e.printStackTrace();
			throw new BMOException(e);
		}
	}

	// INSERT
	protected Map<String,Object> insertResult(String key) throws BMOException {
		try {
			String type = "insert";
			String sqlKey = getSqlKey(key, type);
			SqlSession sqlSession = getSession(key);
			if (sourceMap.get(key) instanceof Element) {
				Element el = (Element) sourceMap.get(key);
				Element child = el.element(type);
				//方式一
				String useGeneratedKeys = child.attributeValue("useGeneratedKeys");
				if(useGeneratedKeys!=null){
					String keyProperty = child.attributeValue("keyProperty");
					reqMap.put(keyProperty, null);
				}
				
				//方式二
				Element selectKey = child.element("selectKey");
				if(selectKey!=null){
					String keyProperty = selectKey.attributeValue("keyProperty");
					reqMap.put(keyProperty, null);
				}
			}
			Map<String,Object> param = new HashMap<String,Object>();
			param.putAll(reqMap);
			int r = sqlSession.insert(sqlKey, param);
			sqlSession.commit();
			sqlSession.close();
			param.put("_result", r);
			return param;
		} catch (IOException e) {
			e.printStackTrace();
			throw new BMOException(e);
			
		}
	}

	// DELETE
	protected int deleteResult(String key) throws BMOException {
		try {
			SqlSession sqlSession = getSession(key);
//			String sqlKey = (String) sourceMap.get(key);
			String sqlKey = getSqlKey(key, "delete");
			int result = sqlSession.delete(sqlKey, reqMap);
			sqlSession.commit();
			sqlSession.close();
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			throw new BMOException(e);
		}
	}

	// PROCEDURE 待定
	protected Map<String, Object> callResult(String key) throws BMOException {
		try {
			String sqlKey = (String) sourceMap.get(key);
			Map<String, Object> map = new HashMap<String, Object>();
			map.putAll(reqMap);
			SqlSession sqlSession = getSession(key);
			sqlSession.selectOne(sqlKey, map);
			sqlSession.commit();
			sqlSession.close();
			return map;
		} catch (IOException e) {
			e.printStackTrace();
			throw new BMOException(e);
		}
	}

	protected String getResultfromContent(String s) throws Exception {
		s = (String) sourceMap.get(s);
		StringWriter stringwriter;
		Velocity.init();
		stringwriter = new StringWriter();
		Velocity.evaluate(vc, stringwriter, "mystring", s);
		return stringwriter.toString();
	}

	public static void main(String[] args) {
		MybatisService ms = new MybatisService();
		try {
			SqlSession sqlSession = ms.getSession("sm_priSpec");
			Object obj = sqlSession.selectList("sm_priSpec");
			System.out.println(obj);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected SqlSession getSession(String key) throws IOException {
		
		Reader reader = null;

		if (sourceMap.get(key) instanceof Element) {
			Element el = (Element) sourceMap.get(key);
			String sql = "";
			@SuppressWarnings("unchecked")
			List<Element> list = el.elements();
			for (Element element : list) {
				sql += element.asXML();
			}
			sql = java.net.URLEncoder.encode(sql,"utf-8");
			String path = getRequest().getContextPath();
			String basePath = getRequest().getScheme() + "://" + getRequest().getServerName() + ":" + getRequest().getServerPort() + path;
			String urlpath = basePath + "/mybatisConfig/SqlMapConfig.jsp?sql=" + sql;
			
			
			
			reader =  Resources.getUrlAsReader(urlpath);
		}else{
			String resource = "SqlMapConfig.xml";
			reader = Resources.getResourceAsReader(resource);

		}

		SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
		SqlSessionFactory factory = builder.build(reader);
		if (sqlSession != null) {
			return sqlSession;
		}
		SqlSession session = factory.openSession(true);
		return session;
	}

	protected Map<String, Object> sourceMap = new HashMap<String, Object>();

	public void setSourceMap(Map<String, Object> sourceMap) {
		this.sourceMap = sourceMap;
	}

	public Map<String, Object> getSourceMap() {
		return sourceMap;
	}

	@Override
	public HttpServletRequest getRequest() {
		return this.req;
	}

	@Override
	public void setRequest(HttpServletRequest req) {
		this.req = req;
	}

	@Override
	public HttpServletResponse getResponse() {
		return this.res;
	}

	@Override
	public void setResponse(HttpServletResponse res) {
		this.res = res;
	}

}
