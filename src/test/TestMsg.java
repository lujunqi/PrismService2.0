package test;

import com.prism.weixin.WeiXin;

public class TestMsg {
	public static void main(String[] args) {
		//WeiXin.APPID
		WeiXin.setAccessToken();
		//{"access_token":"31_kMoZbROaiCnzy6icu3LmMusceH2wOJUgVaJ8Qu7N4Wo7Fa6F2OXIiEmRKAWhA1f4DuNxRfLeWABq_ZuYajA5SnyCrrULxEMp3eWX0DpKrCg3xTw8CNRaVCHX10We6CkenN1HGLm4OwNwsmWpWWHhAHAMMX","expires_in":7200}
		WeiXin.sendTemplate();
		



	}
}
