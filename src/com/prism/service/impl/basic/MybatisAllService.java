package com.prism.service.impl.basic;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;

import org.dom4j.Element;

import com.prism.common.JsonUtil;
import com.prism.common.VMResponse;

public class MybatisAllService extends MybatisService {
	public void service() throws ServletException, IOException {
		super.service();
		PrintWriter out = getResponse().getWriter();
		try {
			String key = "SQL";
			Map<String,Object> obj = new HashMap<String,Object>();
			if (sourceMap.get(key) instanceof Element) {
				Element el = (Element) sourceMap.get(key);
				Element el1 = (Element) (el.elements().get(0));
				if ("select".equals(el1.getName())) {
					obj.put("session", true);
					obj.put("data", selectResult(key));
				}else if("insert".equals(el1.getName())) {
					obj.put("session", true);
					obj.put("data", this.insertResult(key));

				}
				
			}
			String action = (String) reqMap.get("_action");
			reqMap.put(action, obj);
			reqMap.put("this", obj);
			getRequest().setAttribute("this", obj);
			getRequest().setAttribute(action, obj);
			vc.put(action, obj);
			vc.put("this", obj);

			// 视图模板
			if (sourceMap.containsKey("VIEW")) {
				VMResponse vm = new VMResponse();
				vm.setReqMap(reqMap);
				vc.put("v", vm);
				String content = getResultfromContent("VIEW");
				out.print(content);
			}

			// FORWARD 页面跳转
			if (sourceMap.containsKey("FORWARD")) {

				getRequest().setAttribute("TEMPLATE", sourceMap.get("TEMPLATE"));
				getRequest().getRequestDispatcher((String) sourceMap.get("FORWARD")).forward(getRequest(), getResponse());
			}
			// REDIRECT 页面重定向
			if (sourceMap.containsKey("REDIRECT")) {
				getResponse().sendRedirect((String) sourceMap.get("REDIRECT"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("code", -1);
			m.put("session", false);
			m.put("info", e.getMessage());
			JsonUtil ju = new JsonUtil();
			out.println(ju.toJson(m));
			e.printStackTrace();
		}
	}
}
