package com.prism.source;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.prism.dbutil.DBCommand;
import com.prism.exception.DAOException;

public class SourceMap extends HashMap<String, Object> {
	private static final long serialVersionUID = 2L;

	public void setKey(String action, Object dbConn) {
		String sql = "SELECT * FROM sm_bean WHERE action=${ACTION<STRING>}";
		DBCommand cmd = new DBCommand(dbConn);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ACTION", action);
		try {
			List<Map<String, Object>> list = cmd.executeSelect(sql, map);
			for (Map<String, Object> map2 : list) {
				String key2 = (String) map2.get("KEY");
				Object val2 = map2.get("VAL");
				if ("XML".equals(key2)) {
					try {
						Document doc = DocumentHelper.parseText(val2+"");
						Element root = doc.getRootElement();
						@SuppressWarnings("unchecked")
						List<Element> elements = root.elements();
						for (Element ele : elements) {
							if(ele.attributeValue("value")!=null){
								super.put(ele.attributeValue("name"),ele.attributeValue("value"));
							}else{
								if(ele.attributeValue("node")!=null){
									super.put(ele.attributeValue("name"), ele  );
								}else{
									super.put(ele.attributeValue("name"), ele.getText());
								}
								
							}
							
						}
					} catch (DocumentException e) {
						e.printStackTrace();
					}
					
				} else {
					
					super.put(key2, val2);
				}

			}

			if (!super.containsKey("VIEW")) {// 默认值
				super.put("VIEW", "$v.toJson()");
			}
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}
}
