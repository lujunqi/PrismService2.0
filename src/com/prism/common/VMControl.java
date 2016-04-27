package com.prism.common;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.context.ApplicationContext;

import com.prism.service.Service;

/*
 * 控件生成器
 */
public class VMControl {
	private Map<String, String> m_unit = new HashMap<String, String>();
	private HttpServletRequest req;
	private HttpServletResponse res;
	private ApplicationContext context;

	@SuppressWarnings("unchecked")
	public VMControl(HttpServletRequest req, HttpServletResponse res) {
		this.req = req;
		this.res = res;
		context = (ApplicationContext) req.getAttribute("context");
		m_unit = (Map<String, String>) context.getBean("m_unit");
		vc = new VelocityContext();
	}

	private VelocityContext vc = new VelocityContext();
	private String title = "";

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getContext(String name) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Service vm = (Service) context.getBean(name);
		vm.setRequest(req);
		vm.setResponse(res);
		try {
			vm.service();
			result = (List<Map<String, Object>>) req.getAttribute("this");
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	public String win(String title,String name,String content){
		String unit = m_unit.get("EL:WIN");
		this.title = title;
		return String.format(unit, name, setNull(content));
	}
	
	public String text(String title, String name) {
		return text(title, name, "");
	}

	public String text(String title, String name, String value) {
		String unit = m_unit.get("EL:TEXT");
		this.title = title;
		return String.format(unit, name, setNull(value));
	}

	public String hidden(String name, String value) {
		String unit = m_unit.get("EL:HIDDEN");
		return String.format(unit, name, setNull(value));
	}

	public String checkbox(String title, String name,
			List<Map<String, String>> list) {
		return checkbox(title, name, list, null);
	}

	public String checkbox(String title, String name,
			List<Map<String, String>> list, String[] c) {
		String unit = m_unit.get("EL:CHECKBOX");
		this.title = title;
		String html = "";
		Set<String> s = new HashSet<String>();
		if (c != null) {
			for(String x:c){
				s.add(x);
			}
		}
		for (Map<String, String> tmps : list) {
			String value = tmps.get("V");
			String des = tmps.get("D");
			String checked = null;
			if (tmps.containsKey("C")) {
				checked = "checked=\"checked\"";
			}
			if(s.contains(value)){
				checked = "checked=\"checked\"";
			}
			html += String.format(unit, name, value, des, setNull(checked));
		}
		return html;
	}

	public String radio(String title, String name,
			List<Map<String, String>> list) {
		return radio(title, name, list, null);
	}

	public String radio(String title, String name,
			List<Map<String, String>> list, String c) {
		String unit = m_unit.get("EL:RADIO");
		this.title = title;
		String html = "";
		for (Map<String, String> tmps : list) {
			String value = tmps.get("V");
			String des = tmps.get("D");
			String checked = null;
			if (tmps.containsKey("C")) {
				checked = "checked=\"checked\"";
			}
			if (value.equals(c)) {
				checked = "checked=\"checked\"";
			}
			html += String.format(unit, name, value, des, setNull(checked));
		}
		return html;
	}

	public String select(String title, String name,
			List<Map<String, String>> list) {
		return select(title, name, list, null);
	}

	public String select(String title, String name,
			List<Map<String, String>> list, String c) {
		String unit = m_unit.get("EL:SELECT");
		this.title = title;
		String html = "";

		for (Map<String, String> tmps : list) {
			String value = tmps.get("V");
			String des = tmps.get("D");
			String checked = null;
			if (tmps.containsKey("C")) {
				checked = "checked=\"checked\"";
			}
			if (value.equals(c)) {
				checked = "checked=\"checked\"";
			}
			html += String.format("<option value=\"%1$s\" %3$s>%2$s</option>",
					value, des, setNull(checked));
		}
		return String.format(unit, name, html);
	}

	public String date(String title, String name, String val1, String val2) {
		String unit = m_unit.get("EL:DATE");
		this.title = title;
		return String.format(unit, name, setNull(val1), setNull(val2));
	}

	public String date(String title, String name) {
		return date(title, name, null, null);
	}

	public String getHtml(String unit) {
		vc.put("el", this);
		try {
			unit = getResultfromContent(unit, vc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String html = "";
		if ("".equals(title)) {
			html = unit;
//			System.out.println(html + "============");
		} else {
			html = String.format("<li><label>%1$s:</label>%2$s</li>", title,
					unit);
		}
		return html;
	}

	private String getResultfromContent(String s, VelocityContext vc)
			throws Exception {
		StringWriter stringwriter;
		Velocity.init();
		stringwriter = new StringWriter();
		Velocity.evaluate(vc, stringwriter, "mystring", s);
		return stringwriter.toString();
	}

	private String setNull(Object v) {
		if (v == null) {
			return "";
		} else {
			return v + "";
		}
	}

}
