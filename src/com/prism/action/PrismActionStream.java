package com.prism.action;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.prism.service.Service;
import com.prism.source.SourceMap;

public class PrismActionStream extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String[] xmls = { "config/base/baseConf.xml" };
	private static ApplicationContext context;

	public void init() throws ServletException {
		System.out.println("PrismActionEx正在加载....");
		context = new ClassPathXmlApplicationContext(xmls[0],"config/base/taskConf.xml");
	}

	public void service(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		try {
			context = new ClassPathXmlApplicationContext(xmls);
			req.setCharacterEncoding("UTF-8");
			res.setContentType("text/html;charset=UTF-8");
			String action = getAction(req);
			String exName = getExtendName(req);
			Service vm = (Service) context.getBean(exName);
			if (context.containsBean(action)) {// 优先XML配置
				Service s = (Service) context.getBean(action);
				vm.setSourceMap(s.getSourceMap());
			} else {
				SourceMap smap = new SourceMap();
				smap.putAll(vm.getSourceMap());
				smap.setKey(action, context.getBean("DBConnection"));
				vm.setSourceMap(smap);
			}
			Map<String, Object> reqMap = new HashMap<String, Object>();
			Enumeration<String> en = req.getParameterNames();
			while (en.hasMoreElements()) {
				String name = en.nextElement();
				if (!isNull(req.getParameter(name))) {
					reqMap.put(name, req.getParameter(name));
				}
			}
			
			
			reqMap.put("_action", action);
			req.setAttribute("reqMap", reqMap);
			req.setAttribute("context", context);
			req.setAttribute("DBConnection", context.getBean("DBConnection"));
			vm.setRequest(req);
			vm.setResponse(res);
			vm.service();

		} catch (Exception e) {
			e.printStackTrace();
			req.getRequestDispatcher("/error.jsp").forward(req, res);
		}
	}

	private String getAction(HttpServletRequest req) {
		try {
			String relativeuri = req.getRequestURI().replaceFirst(
					req.getContextPath(), "");
			relativeuri = relativeuri.replaceAll("/pa/", "");
			int exLen = relativeuri.lastIndexOf(".");
			StringBuffer sb = new StringBuffer(relativeuri);
			return sb.substring(0, exLen);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	private String getExtendName(HttpServletRequest req) {
		try {
			String relativeuri = req.getRequestURI().replaceFirst(
					req.getContextPath(), "");
			// relativeuri = relativeuri.replaceAll("/", "");
			int exLen = relativeuri.lastIndexOf(".");
			StringBuffer sb = new StringBuffer(relativeuri);
			return sb.substring(exLen + 1);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	public void destroy() {
		super.destroy();
	}

	private boolean isNull(String param) {
		if (param == null) {
			return true;
		} else if ("".equals(param)) {
			return true;
		} else {
			return false;
		}
	}
}
