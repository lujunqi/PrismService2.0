package templete;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.velocity.VelocityContext;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.springframework.stereotype.Component;

@Component("ManagerMenu")
public class ManagerMenu implements Templete {
	@Override
	public void service(Map<String, Object> sourceMap, HttpServletRequest req,HttpServletResponse res) {

		@SuppressWarnings("unchecked")
		List<Map<String, Object>> list = (List<Map<String, Object>>) req.getAttribute("this");
		HttpSession session = req.getSession();
		Element root = new Element("ul");
		root.setAttribute("class", "sideNav");
		Map<Object, Element> ele = new HashMap<Object, Element>();
		for (Map<String, Object> map : list) {
			Element li = new Element("li");
			if ("0".equals(map.get("pid") + "")) {
				li.setAttribute("class", "major");
				Element h2 = new Element("h2");
				h2.setAttribute("class", "subtit");
				Element a = new Element("a");
				a.setAttribute("target", "main");
				a.setAttribute("href", "javascript:");
				a.setText(map.get("name") + "");
				h2.addContent(a);
				li.addContent(h2);

				int user_opt = -10;
				if (session.getAttribute("user_opt") != null) {
					user_opt = Integer.parseInt("" + session.getAttribute("user_opt"));
					
				}
				int menu_opt = Integer.parseInt("" + map.get("op"));
				int yu = menu_opt % user_opt;
				li.setAttribute("yu", yu + "");
				
				if (yu == 0) {
					root.addContent(li);
				}

			} else {
				Element a = new Element("a");
				a.setAttribute("target", "main");
				if(map.get("url")!=null){
					a.setAttribute("href", map.get("url") + "");
				}
				
				a.setText(map.get("name") + "");
				int user_opt = -10;
				if (session.getAttribute("user_opt") != null) {
					user_opt = Integer.parseInt("" + session.getAttribute("user_opt"));	
				}
				int menu_opt = Integer.parseInt("" + map.get("op"));
				int yu = menu_opt % user_opt;
				li.setAttribute("yu", yu + "");
				li.addContent(a);

			}
			ele.put(map.get("id") + "", li);

			if (ele.containsKey(map.get("pid") + "")) {
				Element p = ele.get(map.get("pid") + "");
				if (p.getChild("ul") == null) {
					Element ul = new Element("ul");
					ul.setAttribute("class", "sublist");
					p.addContent(ul);
				}
				if ("0".equals(li.getAttributeValue("yu")))
					p.getChild("ul").addContent(li);
			}

		}

		Format format = Format.getCompactFormat();
		format.setEncoding("utf-8");
		format.setIndent(" ");
		XMLOutputter xmlout = new XMLOutputter(format);
		ByteArrayOutputStream byteRsp = new ByteArrayOutputStream();
		try {
			xmlout.output(root, byteRsp);
			String menu = byteRsp.toString("utf-8");
			sourceMap.put("lb_menu", menu);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
