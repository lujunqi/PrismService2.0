package com.prism.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.DigestUtils;

import com.alibaba.fastjson.JSON;
import com.prism.db.Prism;

public abstract class PrismAction extends HttpServlet {

	private static final long serialVersionUID = 1L;
	protected static final String xmls = "spring.xml";
	protected static ClassPathXmlApplicationContext beans;
	private static String XMLMD5 = "";

	public void init() throws ServletException {

		if (reloadXML()) {
			beans = new ClassPathXmlApplicationContext(xmls);

		}

	}

	protected abstract Map<String, Object> service(Prism prism, Map<String, Object> param);

	protected HttpServletRequest req;
	protected HttpServletResponse res;

	public boolean reloadXML() {
		boolean result = true;

		String path = this.getServletContext().getRealPath("/WEB-INF/classes");
		File file = new File(path, xmls);
		StringBuffer sb = new StringBuffer();
		sb.append(String.format("%s_%s_", file.lastModified(), file.length()));

		File f2 = new File(getServletContext().getRealPath("/WEB-INF/classes/config"));
		File[] fs = f2.listFiles();
		for (int i = 0; i < fs.length; i++) {
			sb.append(String.format("%s_%s_", fs[i].lastModified(), fs[i].length()));
		}
		String md5 = DigestUtils.md5DigestAsHex(sb.toString().getBytes());

		if (md5.equals(XMLMD5)) {
			result = false;
		} else {

			XMLMD5 = md5;
		}

		return result;
	}



	public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// beans = new ClassPathXmlApplicationContext(xmls);
		this.req = req;
		this.res = res;
		if (reloadXML()) {
			beans = new ClassPathXmlApplicationContext(xmls);

		}

		Map<String, Object> result = new HashMap<String, Object>();

		try {
			req.setCharacterEncoding("UTF-8");
			res.setContentType("text/html;charset=UTF-8");
			String action = this.getAction(req);
			String BASE = req.getContextPath();
			req.setAttribute("BASE", BASE);
			req.setAttribute("ACTION", action);
			StringBuffer myurl = req.getRequestURL();
			req.setAttribute("MYURL", myurl.toString());
			
			
			if (beans.containsBean(action)) {
				
				Prism prism = (Prism) beans.getBean(action);
				prism.setContext(beans);
				Map<String, Object> param = getParam(req);
				//begin 操作日记、
				log(param,action);
				//end 操作日记、
				result = service(prism, param);
			
			} else {
				result.put("msg", "URL访问地址不存在");
				result.put("code", "1");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("msg", e.getMessage());
			result.put("code", "1");
		}
		String responseType = req.getHeader("Accept");
		if (responseType.indexOf("application/json") != -1) {
			String json = JSON.toJSONString(result);
			res.getWriter().print(json);
		} else {
			
			if (!res.isCommitted()) {
				req.getRequestDispatcher("/404.jsp").forward(req, res);
			}

		}

	}
	private void log(Map<String, Object> param1,String action) {
		if(!param1.containsKey("my_user_id")) {
			return;
		}
		Prism prism = (Prism) beans.getBean("lb.log");
		prism.setContext(beans);
		String body = JSON.toJSONString(param1);
		Map<String, Object> param = new HashMap<String,Object>();
		param.putAll(param1);
		param.put("log_action", action);
		param.put("log_body", body);
		prism.db(param);
		
	}
	protected Map<String, Object> getParam(HttpServletRequest req) throws UnsupportedEncodingException, IOException {
		Map<String, Object> param = new LinkedHashMap<String, Object>();

		String requestType = req.getHeader("Request");

		if (requestType == null || requestType.indexOf("json") == -1) {

			java.util.Enumeration<String> en = req.getParameterNames();
			while (en.hasMoreElements()) {
				String name = (String) en.nextElement();
				String val = req.getParameter(name);
				param.put(name, val);
			}
			/** post数据包数据中的加密数据 **/
			ServletInputStream sis = req.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(sis));
			String line = null;
			StringBuilder sb = new StringBuilder();
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			param.put("$body", sb.toString());
		} else {

			BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream(), "utf-8"));
			String line = null;
			StringBuilder str = new StringBuilder();
			while ((line = br.readLine()) != null) {
				str.append(line);
			}
			param = JSON.parseObject(str.toString());
		}

		if (param.containsKey("page")) {
			int page = Integer.parseInt(param.getOrDefault("page", 0) + "");
			int limit = Integer.parseInt(param.getOrDefault("limit", 0) + "");
			param.put("_p", (page - 1) * limit);
		}

		return param;
	}

	private String getAction(HttpServletRequest req) {
		try {
			String relativeuri = req.getRequestURI().replaceFirst(req.getContextPath(), "");
			return relativeuri.substring(relativeuri.lastIndexOf("/") + 1);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	public boolean nvl(Object obj) {
		if (obj == null) {
			return true;
		}
		if ("".equals(obj)) {
			return true;
		}
		return false;
	}

}
