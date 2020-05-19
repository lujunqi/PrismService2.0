package com.prism.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class PrismBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {

	@SuppressWarnings("unchecked")
	protected Class getBeanClass(Element element) {
		return Prism.class;
	}

	protected void doParse(Element element, BeanDefinitionBuilder bean) {
		String id = element.getAttribute("id");
		bean.addPropertyValue("id", id);

		String aop = element.getAttribute("aop");
		if (!"".equals(aop)) {
			bean.addPropertyValue("aop", aop);
		}

		String dataSource = element.getAttribute("dataSource");
		if (!"".equals(dataSource)) {
			bean.addPropertyValue("dataSource", dataSource);
		}

		NodeList list = element.getChildNodes();

		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			String nodeName = node.getNodeName();
			if (nodeName.endsWith(":db")) {
				List<Map<String, String>> db = db(node, bean);
				bean.addPropertyValue("dblist", db);
			
			} else if (nodeName.endsWith(":view")) {
				
				Map<String,Object> view = new HashMap<String,Object>();
				NamedNodeMap attributes = node.getAttributes();
				Element el = (Element)node;
				if(el.hasAttribute("include")) {
					
				}
	            for (int j = 0; j < attributes.getLength(); j++) {
	                Node attribute = attributes.item(j);
	                
	                view.put(attribute.getNodeName(), attribute.getNodeValue());
	            }
				
				
				view.put("var", view(node,view));

				bean.addPropertyValue("view", view);
			}

		}

	}
		private Map<String, Object> view(Node pnode,Map<String,Object> view ) {
			Map<String, Object> result = new HashMap<String, Object>();
			NodeList list = ((Element) pnode).getChildNodes();

			for (int i = 0; i < list.getLength(); i++) {
				Node node = list.item(i);
				String nodeName = node.getNodeName();
				if (nodeName.endsWith(":var")) {
					Element ele = (Element) node;
					String text = ele.getTextContent();
					if("json".equals(ele.getAttribute("type"))) {
						Gson gson = new Gson();

						@SuppressWarnings("rawtypes")
//						List<HashMap> obj =JSON.parseArray(text, HashMap.class);
						
						List<Map<String,Object>> obj = gson.fromJson(text, new TypeToken<List<Map<String,Object>>>() {}.getType());

						result.put(ele.getAttribute("name"),obj);
					}else {
						result.put(ele.getAttribute("name"),text);	
					}
					
				}else if (nodeName.endsWith(":text")) {
					Element ele = (Element) node;
					
					view.put("text", ele.getTextContent());
				}
			}
			return result;
		}
	

	private List<Map<String, String>> db(Node pnode, BeanDefinitionBuilder bean) {
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();

		NodeList list = ((Element) pnode).getChildNodes();

		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			String nodeName = node.getNodeName();

			if (nodeName.endsWith(":sql")) {
				Element ele = (Element) node;
				String sql = node.getTextContent();

				Map<String, String> param = new HashMap<String, String>();
				param.put("sql", sql);

				param.put("id", ele.getAttribute("id"));
				param.put("type", ele.getAttribute("type"));
				param.put("depend", ele.getAttribute("depend"));
				param.put("key", ele.getAttribute("key"));

				result.add(param);
			} else if (nodeName.endsWith(":return")) {
				String text = node.getTextContent();

				bean.addPropertyValue("return", text);

			}
		}
		return result;
	}
}
