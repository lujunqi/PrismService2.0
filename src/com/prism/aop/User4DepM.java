package com.prism.aop;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.prism.ding.DingTalk;
// 部门主管
@Component("User4DepM")
public class User4DepM implements AOP{
	public Map<String, Object> run(String id,Map<String, Object> param) {
		
		String[] dep = ("" + param.get("dep")).split(",");
		
		DingTalk dt = new DingTalk();
		Set<String> userId = new HashSet<String>();
		
		for (int i = 0; i < dep.length; i++) {
			List<String> s = dt.depUsers(dep[i]);
			System.out.println(s);	
			userId.addAll(s);
		}
		userId.remove("");


		String[] s1 = userId.toArray(new String[userId.size()]);
		String ss = String.join(",", s1);
		Map<String, Object> tmap = new HashMap<String, Object>();
		tmap.put("user_ids", ss);
		return tmap;
	
	}
}
