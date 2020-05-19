package com.prism.cut;

import java.util.Map;

public class TableCut extends Cut {

	@Override
	public void begin(Map<String, Object> map) {
		//

	}

	@Override
	public void end(Map<String, Object> map) {
//		System.out.println(map.get("cols"));
//		@SuppressWarnings("unchecked")
//		List<Map<String, Object>> cols = (List<Map<String, Object>>) map.get("cols");
//		List<String> b = new ArrayList<String>();
//		for (Map<String, Object> m2 : cols) {
//			List<String> c = new ArrayList<String>();
//			for (Map.Entry<String, Object> en : m2.entrySet()) {
//				String key = en.getKey();
//				Object val = en.getValue();
//				String var = "";
//				if("templet".equals(key)) {
//					var=String.format("\"%s\":function(res){%s}",key,val);
//				}else {
//					var=String.format("\"%s\":\"%s\"",key,val); 
//				}
//				c.add(var);
//			}
//			
//			b.add("{"+StringUtils.join( c.toArray(), ",")+"}");
//		}
//		if(map.containsKey("event")) {
//			b.add("{fixed : 'right',align : 'center',toolbar : '#barDemo'}"); 
//		}
//		String x ="["+ StringUtils.join(b.toArray(), ",")+"]";
//		if(!map.containsKey("where")) {
//			map.put("where", "{}");
//		}
//		map.put("iCols", x);
		
	}

}
