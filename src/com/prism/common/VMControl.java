package com.prism.common;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;

/*
 * 控件生成器
 */
public class VMControl {
	private Map<String, String> m_unit = new HashMap<String, String>();
	private ApplicationContext context;
	public VMControl(){};
	@SuppressWarnings("unchecked")
	public VMControl(HttpServletRequest req) {

		context = (ApplicationContext) req.getAttribute("context");
		m_unit = (Map<String, String>) context.getBean("m_unit");
	}

	public  String control(Map<String,String> map) {
		Map<String, myString> result = new HashMap<String, myString>();
		result.put("TEXT", new myString() {
			String key = "TEXT";
			@Override
			public String get() {
				String html = m_unit.get(key);
				html = "<input type=\"text\" name=\"%1$s\" data-exp='%2$s' data-method=\""+key+"\" id=\"%1$s\" class=\"layui-input\">";
				return String.format(html,map.get("KEY"),nNull(map.get("VAL")));
			}
		});
		if(result.containsKey(map.get("TYPE"))){
			return result.get(map.get("TYPE")).get();
		}else{
			return "";
		}
		
	}

	interface myString {
		public String get();
	}
	public static void main(String[] args) {
		Map<String, String> munit = new HashMap<String, String>();
		munit.put("TYPE", "INPUT");
		munit.put("KEY", "cust_id");
		munit.put("VAL", "cust_id");
		
		String html = new VMControl().control(munit);
		System.out.println(html);
	}
	private String nNull(Object o){
		if(o==null){
			return "";
		}else{
			return o+"";
		}
	}
}
