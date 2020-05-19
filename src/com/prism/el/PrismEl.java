package com.prism.el;

import com.alibaba.fastjson.JSON;

public class PrismEl {
	public static String obj2Json(Object obj) {
		return JSON.toJSONString(obj);
	}
}
