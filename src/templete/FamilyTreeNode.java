package templete;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.prism.common.JsonUtil;

@Component("FamilyTreeNode")
public class FamilyTreeNode implements Templete {

	public void service(Map<String, Object> sourceMap, HttpServletRequest req) {
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> list = (List<Map<String, Object>>) req.getAttribute("this");
		makeNodes(list, req);

		sourceMap.remove("VIEW");

	}

	private void makeNodes(List<Map<String, Object>> list, HttpServletRequest req) {
		Map<String, MyNode> nodes = new HashMap<String, MyNode>();
		MyNode root = new MyNode();
		Map<String, Map<String, Object>> mjson = new HashMap<String, Map<String, Object>>();
		for (Map<String, Object> map : list) {
			String ft_id = "id" + map.get("ft_id");
			String sup_ft_id = "id" + map.get("sup_ft_id");
			String ft_name = "<span data-sup_id='" + sup_ft_id + "' data-id='" + ft_id + "'>" + iNull(map.get("ft_name")) + "</span><a>" + iNull(map.get("ft_nick")) + "</a>";
			MyNode node = new MyNode();
			node.id = ft_id;
			node.pid = sup_ft_id;

			node.name = ft_name;

			if ("id0".equals(sup_ft_id)) {// 根节点
				root.add(node);
			}
			nodes.put(ft_id, node);
			if (nodes.containsKey(sup_ft_id)) {
				MyNode pNode = nodes.get(sup_ft_id);
				pNode.add(node);
			}
			mjson.put(ft_id, map);
		}
		JsonUtil json = new JsonUtil();
		String strJson = json.toJson(mjson);
		req.setAttribute("view", outNode(root));
		req.setAttribute("script", strJson);
	}

	private String iNull(Object obj) {
		if (obj == null) {
			return "";
		} else {
			return obj.toString();
		}
	}

	String nodeInfo = "";

	private String outNode(MyNode node) {
		String result = "";

		if (node.pid != null) {
			result += "<li>";
			result += node.name;
		}

		if (!node.child.isEmpty()) {
			result += "<ul>";
			for (MyNode n : node.child) {
				result += outNode(n);
			}
			result += "</ul>";
		}
		if (node.pid != null) {
			result += "</li>";
		}
		return result;
	}

	class MyNode {
		String id;
		String pid;
		String name;
		List<MyNode> child = new ArrayList<MyNode>();

		public void add(MyNode node) {
			this.child.add(node);
		}
	}
}
