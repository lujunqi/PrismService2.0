package com.weixin;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.prism.common.JsonUtil;

public class WeiXinResponse {
	public static void main(String[] args) {
		JsonUtil ju = new JsonUtil();
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("touser", "ljq");
		m.put("msgtype", "text");
		m.put("agentid", "0");
		Map<String, Object> text = new HashMap<String, Object>();
		text.put("content", "你好~");
		m.put("text", text);
		m.put("safe", "0");
		String content = ju.toJson(m);
		System.out.println(content);
		WeiXinResponse w = new WeiXinResponse();
		String t = w
				.getCoAccessToken("wx970cddf1694f3fc3",
						"oiVl1KmK-G0iwHorQIXjlXqs4Y3klleuzXK6fWvvVW_1fTK3gYu0nq13nNeZifyu");
		w.coResText(t, content);

	}

	private HttpSession session;

	public WeiXinResponse() {

	}

	public WeiXinResponse(HttpSession session) {
		this.session = session;
	}

	// 获取企业当前用户ID
	public String getCoUserId(String accessToken, String code, String agentid) {
		String userId = "";
		try {
			Object UserId = session.getAttribute("UserId");
			if (UserId != null) {
				return UserId + "";
			}
			String url = String
					.format("https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?access_token=%1$s&code=%2$s&agentid=%3$s",
							accessToken, code, agentid);
			JsonUtil ju = new JsonUtil();
			@SuppressWarnings("unchecked")
			Map<String, Object> jmap = (Map<String, Object>) ju
					.toObject(HttpWeb.getGetResponse(url));
			userId = jmap.get("UserId") + "";
			
			session.setAttribute("UserId", jmap.get("UserId"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userId;
	}
	//获取 AccessToken
	public String getAccessToken(String appid, String secret,String code) {
		Object accessToken = session.getAttribute("AccessToken");
		if (accessToken != null) {
			return accessToken + "";
		}
		 
		String url = String
				.format("https://api.weixin.qq.com/sns/oauth2/access_token?appid=%1$s&secret=%2$s&code=%3$s&grant_type=authorization_code",
						appid, secret,code);
		JsonUtil ju = new JsonUtil();
		@SuppressWarnings("unchecked")
		Map<String, Object> jmap = (Map<String, Object>) ju.toObject(HttpWeb
				.getGetResponse(url));
		System.out.println(jmap);
		session.setAttribute("AccessToken", jmap.get("access_token"));
		return jmap.get("access_token") + "";

	}

	// 获取企业AccessToken
	public String getCoAccessToken(String corpid, String corpsecret) {
		Object coAccessToken = session.getAttribute("CoAccessToken");
		if (coAccessToken != null) {
			return coAccessToken + "";
		}
		String url = String
				.format("https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=%1$s&corpsecret=%2$s",
						corpid, corpsecret);
		JsonUtil ju = new JsonUtil();
		@SuppressWarnings("unchecked")
		Map<String, Object> jmap = (Map<String, Object>) ju.toObject(HttpWeb
				.getGetResponse(url));
		session.setAttribute("CoAccessToken", jmap.get("access_token"));
		return jmap.get("access_token") + "";

	}

	public void coResText(String accesstoken, String content) {
		String url = String
				.format("https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=%1$s",
						accesstoken);
		// String url =
		// "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=jMd2AbPnWdLRAp8D34Gr9JY1JFQX938gVLVtXzfwlv-s_7IjZdr616moiJoR1aJH";
		JsonUtil ju = new JsonUtil();
		@SuppressWarnings("unchecked")
		Map<String, Object> j = (Map<String, Object>) ju.toObject(HttpWeb
				.getGetResponse(url, content));
		System.out.println(j);
	}

	// 返回文本内容
	public String resText(Map<String, String> reqMap, String conent)
			throws Exception {
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("xml");// 添加文档根
		Element ToUserName = root.addElement("ToUserName");
		ToUserName.setText(reqMap.get("FromUserName"));

		Element FromUserName = root.addElement("FromUserName");
		FromUserName.setText(reqMap.get("ToUserName"));

		Element CreateTime = root.addElement("CreateTime");
		CreateTime.setText(reqMap.get("CreateTime"));

		Element MsgType = root.addElement("MsgType");
		MsgType.setText("text");

		Element Content = root.addElement("Content");
		Content.setText(conent);

		return document.asXML();
	}

	// 返回图文内容
	public String resNews(Map<String, String> reqMap, String conent)
			throws Exception {
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("xml");// 添加文档根
		Element ToUserName = root.addElement("ToUserName");
		ToUserName.setText(reqMap.get("FromUserName"));

		Element FromUserName = root.addElement("FromUserName");
		FromUserName.setText(reqMap.get("ToUserName"));

		Element CreateTime = root.addElement("CreateTime");
		CreateTime.setText(reqMap.get("CreateTime"));

		Element MsgType = root.addElement("MsgType");
		MsgType.setText("news");

		Element ArticleCount = root.addElement("ArticleCount");
		ArticleCount.setText("1");

		Element item = root.addElement("item");

		Element Title = item.addElement("Title");
		Title.setText("Title1");
		Element Description = item.addElement("Description");
		Description.setText("Description1");
		Element PicUrl = item.addElement("PicUrl");
		PicUrl.setText("http://mmbiz.qpic.cn/mmbiz/mXh9UNKvAuRWBpf5MaBxm4chwAHY6SRfgCMx1VX4ibzJRHFjdVr2S9iazVgoL9wpO9tm3JibHs49rs7dbj3yIyuNg/0");

		Element Url = item.addElement("Url");
		Url.setText("http://mmbiz.qpic.cn/mmbiz/mXh9UNKvAuRWBpf5MaBxm4chwAHY6SRfgCMx1VX4ibzJRHFjdVr2S9iazVgoL9wpO9tm3JibHs49rs7dbj3yIyuNg/0");

		return document.asXML();
	}

}
