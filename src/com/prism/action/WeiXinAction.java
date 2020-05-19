package com.prism.action;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.IOUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.prism.weixin.HttpClient;
import com.prism.weixin.SignUtil;

import redis.clients.jedis.Jedis;
import test.Cal24;

@WebServlet("/wx")
public class WeiXinAction extends HttpServlet {

	private static final long serialVersionUID = -4881888484358183165L;
	private HttpServletRequest req;

	public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		this.req = req;

		res.setContentType("text/html;charset=UTF-8");
		String signature = req.getParameter("signature");
		String timestamp = req.getParameter("timestamp");
		String nonce = req.getParameter("nonce");
		String echostr = req.getParameter("echostr");
		PrintWriter out = res.getWriter();
		if (SignUtil.checkSignature(signature, timestamp, nonce)) {

			if (echostr != null) {
				out.print(echostr);
			} else {
				String body = IOUtils.toString(req.getInputStream(), "UTF-8");

				String openid = req.getParameter("openid");
				String time = new Date().getTime() + "";

				req.setAttribute("openid", openid);

				String msg = xml(body);
				String xml = "<xml>" + "  <ToUserName>" + openid + "</ToUserName>"
						+ "  <FromUserName>gh_8b126b185bc0</FromUserName>" + "  <CreateTime>" + time + "</CreateTime>"
						+ "  <MsgType><![CDATA[text]]></MsgType>" + "  <Content><![CDATA[" + msg + "]]></Content>"
						+ "</xml>";
				out.print(xml);
			}
			//

		}

		out.close();

	}

	private String xml(String body) {
		try {
			StringBuilder sXML = new StringBuilder();
			sXML.append(body);
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			InputStream is = new ByteArrayInputStream(sXML.toString().getBytes("utf-8"));
			org.w3c.dom.Document doc = dbf.newDocumentBuilder().parse(is);
			String s = doc.getElementsByTagName("Content").item(0).getTextContent();

			String n = s.replaceAll(" ", "");
			String openid = req.getAttribute("openid") + "";
			String[] n2 = n.split("\\=");
			@SuppressWarnings("resource")
			Jedis jedis = new Jedis("localhost");
			if (n2.length == 2) {
				// ANS.put(openid, n2[1]);
				jedis.set(openid, n2[1]);
				// ANS = Integer.parseInt(n2[1]);
				n = n2[0];
			} else if (!jedis.exists(openid)) {

				jedis.set(openid, "24");
				// ANS = 24;
			}

			char[] c = n.toCharArray();
			String[] st = new String[c.length];
			for (int i = 0; i < st.length; i++) {
				st[i] = c[i] + "";
			}
			if (n.indexOf(".") != -1) {
				st = n.split("\\.");
			}

			Cal24 ca = new Cal24();
			String[] ans = jedis.get(openid).split("\\.");
			ca.cal(0, st, new String[st.length * 2 - 1]);
			StringBuffer sb = new StringBuffer();

			ca.cal2(st, ans);
			Map<String, Set<String>> result = ca.getResult();

			for (Map.Entry<String, Set<String>> en : result.entrySet()) {
				Set<String> set = en.getValue();
				sb.append(" 【" + en.getKey() + "】共 " + set.size() + " 条\n");
				Iterator<String> it = set.iterator();
				int step = 1;
				while (it.hasNext()) {
					String str = (String) it.next();
					sb.append(str + "\n");
					if (step > 10) {
						step = 0;
						if (it.hasNext()) {
							sb.append("  ……\n");
						}
						break;
					}
					step++;

				}

			}
			if (sb.length() != 0) {
				return sb.toString();
			} else {
				return "没答案";
			}

		} catch (Exception e) {
			e.printStackTrace();
			return "错误";
		}

	}

	private final static String APPID = "wx627f086f1403f471";
	private final static String SECTRET = "ed9ba0636ae683b10fb31efaa10ee9f9";

	@SuppressWarnings({ "resource", "unchecked" })
	private String getToken() {
		Jedis jedis = new Jedis("localhost");
		String token = jedis.get("token");
		if (token == null) {
			String url = String.format(
					"https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s", APPID,
					SECTRET);
			String access_token = HttpClient.doGet(url);
			Map<String, Object> atm = (Map<String, Object>) JSON.parse(access_token);
			token = atm.get("access_token") + "";
			jedis.setbit("token", 3000, token);
		}
		return token;
	}

	public void sendTMsg(String openid, String context) {
		JSONObject jsonObject1 = new JSONObject();
		jsonObject1.put("msgtype", "text");
		JSONObject data = new JSONObject();
		data.put("content", context);
		jsonObject1.put("text", data);
		jsonObject1.put("touser", openid);
		data.put("content", "context");
		HttpClient.doPost("https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + getToken(),
				jsonObject1.toJSONString());

	}
}
