package com.prism.weixin;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class WeiXin {
	public static String APPID = "wx627f086f1403f471";
	public static String SECRET = "90a35a62424acc3fa7458c193589efa7";
	public static String TOKEN = "132456sdsfdfgfdghhf";

	// 根据code获得openid
	public static void openId(HttpServletRequest req) {
		String code = req.getParameter("code");

		if (req.getSession().getAttribute("SessOPENID") == null) {
			String url = String.format(
					"https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code",
					APPID, SECRET, code);
			req.getSession().setAttribute("SessOPENID", HttpClient.doGetMap(url));
		}

		@SuppressWarnings("unchecked")
		Map<String, Object> m = (Map<String, Object>) req.getSession().getAttribute("SessOPENID");

		req.setAttribute("OPENID", m.get("openid"));
	}

	// 获取access_token
	public static void setAccessToken() {

		String url = format("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s",
				WeiXin.APPID, WeiXin.SECRET);
		Map<String, Object> map = HttpClient.doGetMap(url);
		
		System.out.println(map);
		
	}
	public static void sendTemplate() {
		String ACCESS_TOKEN = "28_oFcCrVBFd786ojydKbusE_YbfuTpxWTUZ_ubl_blzVb8_RJUX4cnUaaotqJgHhf4yfMLbk2aq6_3atiLpMHvokiR0r3lqXKf85HtUzgRqLC4WAj2-ft61eNACC3CQp2FXref7rkro6rXKRAlQNJfAIACZV";
		String url = format("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=%s",ACCESS_TOKEN);
		String param = "{\r\n" + 
				"    \"touser\":\"oxVxpuBjblcUAGZ2iYOAGBoFmcXQ\",\r\n" + 
				"    \"template_id\":\"eSEAf_3Em6QXvQej353tmko6TWS1hSs1oHibXqPmjbc\",\r\n" + 
				"\"url\":\"http://192.168.1.106/prism/weix/msg.jsp?id=3\",\r\n" + 
				"    \"topcolor\":\"#FF0000\",\r\n" + 
				"    \"data\":{\r\n" + 
				"            \"User\": {\r\n" + 
				"                \"value\":\"黄先生\",\r\n" + 
				"                \"color\":\"#173177\"\r\n" + 
				"            },\r\n" + 
				"            \"Date\":{\r\n" + 
				"                \"value\":\"06月07日 19时24分\",\r\n" + 
				"                \"color\":\"#173177\"\r\n" + 
				"            },\r\n" + 
				"            \"CardNumber\": {\r\n" + 
				"                \"value\":\"0426\",\r\n" + 
				"                \"color\":\"#173177\"\r\n" + 
				"            },\r\n" + 
				"            \"Type\":{\r\n" + 
				"                \"value\":\"消费\",\r\n" + 
				"                \"color\":\"#173177\"\r\n" + 
				"            },\r\n" + 
				"            \"Money\":{\r\n" + 
				"                \"value\":\"人民币260.00元\",\r\n" + 
				"                \"color\":\"#173177\"\r\n" + 
				"            },\r\n" + 
				"            \"DeadTime\":{\r\n" + 
				"                \"value\":\"06月07日19时24分\",\r\n" + 
				"                \"color\":\"#173177\"\r\n" + 
				"            },\r\n" + 
				"            \"Left\":{\r\n" + 
				"                \"value\":\"6504.09\",\r\n" + 
				"                \"color\":\"#173177\"\r\n" + 
				"            }\r\n" + 
				"    }\r\n" + 
				"}\r\n" + 
				"";
		Map<String,Object> m = HttpClient.doPostMap(url, param);
		System.out.println(m);

		

	}
	private static String format(String format, Object... args) {

		return String.format(format, args);

	}
}
