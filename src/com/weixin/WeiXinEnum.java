package com.weixin;

public enum WeiXinEnum {
	sToken("qYrbuNklZl9bUc1QiM"), // 这个Token是随机生成，但是必须跟企业号上的相同
	sCorpID("wx970cddf1694f3fc3"), // 这里是你企业号的CorpID
	sEncodingAESKey("uRgSYeTv3FLz3sGAKy2l87rjUAWpdyXwtJuzWhiKzL3"), // 这个EncodingAESKey是随机生成，但是必须跟企业号上的相同
	APPSECRET(
			"oiVl1KmK-G0iwHorQIXjlXqs4Y3klleuzXK6fWvvVW_1fTK3gYu0nq13nNeZifyu");// 管理组的凭证密钥
	private String name;

	private WeiXinEnum(String name) {
		this.name = name;
	}
	public String getName() {
        return name;
    }
}
