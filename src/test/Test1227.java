package test;

import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.prism.weixin.HttpClient;

public class Test1227 {
//	private final static String APPID = "wx627f086f1403f471";
//	private final static String SECTRET = "28cd10c236ea43265127ebe0a193ecf0";
	
	//嘎嘣脆
	private final static String APPID = "wx91ece5f3cd87008a";
	private final static String SECTRET = "82805ffa2ffbe2f9a36055b8d34b579d";
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		String url = String.format(
				"https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s", APPID,
				SECTRET);
		//
		String access_token = HttpClient.doGet(url);
		System.out.println(access_token);

		Map<String, Object> atm = (Map<String, Object>) JSON.parse(access_token);

//		String menu_url = String.format("https://api.weixin.qq.com/cgi-bin/user/get?access_token=%s&next_openid=",
//				atm.get("access_token"));
//		String s = HttpClient.doGet(menu_url);
//		System.out.println(s);

		JSONObject jsonObject1 = new JSONObject();
		jsonObject1.put("msgtype", "text");

		JSONObject data = new JSONObject();
		data.put("content", "context");

		jsonObject1.put("text", data);
		jsonObject1.put("touser", "1omPqlxP2EFlIXy4zXkZwTHhP8TAk");
		
		
		
//		String result = HttpRequestUtil.sendTextMessage(jsonObject1.toJSONString(),"https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token="+access_token)
		String m = HttpClient.doPost("https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token="+atm.get("access_token"), jsonObject1.toJSONString());
		System.out.println(m);
		
		// {"total":2,"count":2,"data":{"openid":["oxVxpuCjn6iXyOh3tPZTxaIsb4mI","oxVxpuBjblcUAGZ2iYOAGBoFmcXQ"]},"next_openid":"oxVxpuBjblcUAGZ2iYOAGBoFmcXQ"}

		// https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID

		// @SuppressWarnings("unchecked")
		// Map<String, Object> atm = (Map<String, Object>) JSON.parse(access_token);
		// String menu_url =
		// String.format("https://api.weixin.qq.com/cgi-bin/menu/create?access_token=%s",
		// atm.get("access_token"));
		// String menu = "{\"button\":[{ \r\n" +
		// "\"type\":\"view\",\r\n" +
		// "\"name\":\"消息3\",\r\n" +
		// "\"url\":\"http://192.168.1.106:8080/prism/weix/\"\r\n" +
		// "}]}\r\n" +
		// "";
		//
		// String m = HttpClient.doPost(menu_url, menu);
		// System.out.println(m);
	}

	

}
