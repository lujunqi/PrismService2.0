package test;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class Test170503 {

	public static void main(String[] args) throws Exception {
		List<Map<String, Object>> list = list();
		Element root = new Element("ul");
		root.setAttribute("class", "sideNav");
		Map<Object, Element> ele = new HashMap<Object, Element>();
		for (Map<String, Object> map : list) {
			Element li = new Element("li");
			if ("0".equals(map.get("pid"))) {
				li.setAttribute("class", "major");
				Element h2 = new Element("h2");
				h2.setAttribute("class", "subtit");
				Element a = new Element("a");
				a.setAttribute("target", "main");
				a.setAttribute("href", "javascript:");
				a.setText(map.get("name") + "");
				h2.addContent(a);
				li.addContent(h2);
				root.addContent(li);
			} else {
				Element a = new Element("a");
				a.setAttribute("target", "main");
				a.setAttribute("href", "javascript:");
				a.setText(map.get("name") + "");
				li.addContent(a);
			}
			ele.put(map.get("id"), li);

			if (ele.containsKey(map.get("pid"))) {
				Element p = ele.get(map.get("pid"));
				if (p.getChild("ul") == null) {
					Element ul = new Element("ul");
					ul.setAttribute("class", "sublist");
					p.addContent(ul);
				}
				p.getChild("ul").addContent(li);
			}

		}

		Format format = Format.getCompactFormat();
		format.setEncoding("utf-8");
		format.setIndent(" ");
		XMLOutputter xmlout = new XMLOutputter(format);
		ByteArrayOutputStream byteRsp = new ByteArrayOutputStream();
		xmlout.output(root, byteRsp);
		String str = byteRsp.toString("utf-8");
		System.out.println(str);
	}

	public static List<Map<String, Object>> list() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		list.add(new HashMap<String, Object>() {
			private static final long serialVersionUID = 1L;
			{
				put("id", "1");
				put("name", "A");
				put("pid", "0");
			}
		});

		list.add(new HashMap<String, Object>() {
			private static final long serialVersionUID = 1L;
			{
				put("id", "8");
				put("name", "H");
				put("pid", "0");
			}
		});

		list.add(new HashMap<String, Object>() {
			private static final long serialVersionUID = 1L;
			{
				put("id", "2");
				put("name", "B");
				put("pid", "1");
			}
		});

		list.add(new HashMap<String, Object>() {
			private static final long serialVersionUID = 1L;
			{
				put("id", "3");
				put("name", "C");
				put("pid", "1");
			}
		});

		list.add(new HashMap<String, Object>() {
			private static final long serialVersionUID = 1L;
			{
				put("id", "9");
				put("name", "I");
				put("pid", "8");
			}
		});

		list.add(new HashMap<String, Object>() {
			private static final long serialVersionUID = 1L;
			{
				put("id", "10");
				put("name", "J");
				put("pid", "8");
			}
		});

		list.add(new HashMap<String, Object>() {
			private static final long serialVersionUID = 1L;
			{
				put("id", "11");
				put("name", "K");
				put("pid", "8");
			}
		});

		list.add(new HashMap<String, Object>() {
			private static final long serialVersionUID = 1L;
			{
				put("id", "21");
				put("name", "U");
				put("pid", "8");
			}
		});

		return list;
	}
}
