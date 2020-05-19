package test;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.prism.db.Prism;

public class Test1106 {
	private static final Feature List = null;
	public static void main(String[] args) {
		String y = "[\r\n" + 
				"{\"field\":\"ht_zu_type\",\"title\":\"租赁类型\",\"type\":\"radio\",\"val\":[{\"k\":\"out\",\"v\":\"租出\"},{\"k\":\"in\",\"v\":\"租入\"}]},\r\n" + 
				"{field:'ht_kh_name',title:'客户名称',type:\"text\",attr:{placeholder:\"请输入客户名称\"}},\r\n" + 
				"{field:'ht_no',title:'合同号',type:\"text\",attr:{placeholder:\"请输入合同号\"}},\r\n" + 
				"{field:'ht_jz_yn',title:'是否结账',type:\"text\",val:[{k:\"y\",v:\"是\"},{k:\"n\",v:\"否\",c:1}]},\r\n" + 
				"{field:'ht_othe',title:'其他说明',type:\"ht_other\",attr:{placeholder:\"请输入其他说明\"}}, \r\n" + 
				"			]";

        List<HashMap> list =JSON.parseArray(y, HashMap.class);

		
		System.out.format("%s",list.get(0).getClass());
		
//		ApplicationContext beans = new ClassPathXmlApplicationContext("classpath:spring.xml");
//		
//		
//		Prism prism = (Prism) beans.getBean("hetong.list");
//		prism.setContext(beans);
//		
//		Map<String, Object> param = new HashMap<String, Object>();
//		param.put("user_acc", "admin");
//		param.put("user_pwd", "12345678");
//		param.put("my_user_co", "sadnim");
		
////		prism.tbl(param);
//		String _return= prism.getReturn();
//		
//		
//		if(!"".equals(_return)) {
//			VelocityContext vc = new VelocityContext();
//			Map<String,Object> result =  new HashMap<String, Object>();
//			vc.put("result",result);
//			vc.put("db", r);
//			
//			vc.put("param",param);
//			System.out.println(_return(_return,vc));
//			System.out.println(result.get("data"));
//			System.out.println(result.get("count"));
//			
//		}else {
//			System.out.println(38);
//		}
		
	}

	private static String _return(String str,VelocityContext vc) {
		Velocity.init();
		StringWriter stringwriter = new StringWriter();
		
//		for (Map.Entry<String, Object> en : param.entrySet()) {
//			vc.put(en.getKey(), en.getValue());
//		}
		Velocity.evaluate(vc, stringwriter, "mystring", str);
		return stringwriter.toString();
	}
	private static List<Map<String, String>> l() {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		for (int i = 0; i < 13; i++) {
			Map<String, String> m = new HashMap<String, String>();
			m.put("id", "k" + i);
			m.put("name", "m" + i);
			list.add(m);

		}
		return list;
	}
}
