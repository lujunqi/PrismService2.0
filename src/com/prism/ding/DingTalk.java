package com.prism.ding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiCallBackRegisterCallBackRequest;
import com.dingtalk.api.request.OapiDepartmentGetRequest;
import com.dingtalk.api.request.OapiDepartmentListRequest;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.request.OapiRoleListRequest;
import com.dingtalk.api.request.OapiUserGetDeptMemberRequest;
import com.dingtalk.api.request.OapiUserGetRequest;
import com.dingtalk.api.request.OapiUserGetuserinfoRequest;
import com.dingtalk.api.response.OapiCallBackRegisterCallBackResponse;
import com.dingtalk.api.response.OapiDepartmentGetResponse;
import com.dingtalk.api.response.OapiDepartmentListResponse;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.dingtalk.api.response.OapiRoleListResponse;
import com.dingtalk.api.response.OapiUserGetDeptMemberResponse;
import com.dingtalk.api.response.OapiUserGetResponse;
import com.dingtalk.api.response.OapiUserGetuserinfoResponse;
import com.taobao.api.ApiException;

import redis.clients.jedis.Jedis;

public class DingTalk {
	Jedis jedis = new Jedis("localhost");

	@SuppressWarnings("unchecked")
	public String token() {
		try {
			String key = Ding.DING_TOKEN;
			//jedis.del(key);
			if (jedis.exists(key)) {
				return jedis.get(key);
			}
			DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/gettoken");
			OapiGettokenRequest req = new OapiGettokenRequest();
			req.setHttpMethod("GET");
			req.setAppkey(Ding.AppKey);
			req.setAppsecret(Ding.AppSecret);
			OapiGettokenResponse rsp = client.execute(req);
			Map<String, Object> map = JSON.parseObject(rsp.getBody(), Map.class);
			if (rsp.getErrcode() == 0) {
				add(key, map.get("access_token"), 6000);
			}
			return map.get("access_token") + "";
		} catch (ApiException e) {
			e.printStackTrace();

			return null;
		}
	}

	private void add(String key, Object val, int time) {
		jedis.set(key, val + "", "NX", "EX", time);
	}

	public String user(String code) {
		String result = "";
		try {
			DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/getuserinfo");
			OapiUserGetuserinfoRequest request = new OapiUserGetuserinfoRequest();
			request.setCode(code);
			request.setHttpMethod("GET");
			OapiUserGetuserinfoResponse response = client.execute(request, token());

			String userId = response.getUserid();

			result = userId;

		} catch (ApiException e) {
			e.printStackTrace();

		}
		return result;
	}

	public String userInfo(String userId) {
		String result = "";
		try {
			DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/get");
			OapiUserGetRequest request = new OapiUserGetRequest();
			request.setUserid(userId);
			request.setHttpMethod("GET");
			OapiUserGetResponse response = client.execute(request, token());
			result = response.getBody();
		} catch (ApiException e) {
			e.printStackTrace();

		}
		return result;
	}

	// 获得所有部门
	public String depList() {
		String result = "";
		String key = Ding.DEP_LIST;
		//jedis.del(key);
		
		if (jedis.exists(key)) {
			return jedis.get(key);
		}
		String accessToken = token();
		try {
			DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/department/list");
			OapiDepartmentListRequest request = new OapiDepartmentListRequest();

			request.setHttpMethod("GET");
			OapiDepartmentListResponse response = client.execute(request, accessToken);
			result = response.getBody();
			
			if (response.getErrcode() == 0) {
				add(key, result, 1000);
			}

		} catch (ApiException e) {
			e.printStackTrace();
		}
		return result;
	}

	// 部门详细信息
	public List<String> depM(String dep_id) {
		String key = Ding.DEP_M_X + dep_id;
		 jedis.del(key);
		if (jedis.exists(key)) {
			List<String> listString = Arrays.asList(jedis.get(key).split("\\|"));
			return listString;
		}
		List<String> result = new ArrayList<String>();
		String accessToken = token();

		try {
			DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/department/get");
			OapiDepartmentGetRequest req = new OapiDepartmentGetRequest();
			req.setId(dep_id);
			req.setHttpMethod("GET");
			OapiDepartmentGetResponse rsp = client.execute(req, accessToken);
			System.out.println(rsp.getBody()+"====");
			if (rsp.getErrcode() == 0) {
				result = Arrays.asList(rsp.getDeptManagerUseridList().split("\\|"));
				add(key, rsp.getDeptManagerUseridList(), 1000);
			} else {
				System.out.println(dep_id);
				System.out.println(rsp.getBody());
			}

		} catch (ApiException e) {
			e.printStackTrace();
		}
		return result;
	}

	// 部门员工列表
	public List<String> depUsers(String dep_id) {
		String key = Ding.DEP_X + dep_id;
//		 jedis.del(key);
		if (jedis.exists(key)) {
			List<String> listString = Arrays.asList(jedis.get(key).split(","));
			return listString;
		}
		List<String> result = new ArrayList<String>();
		String accessToken = token();
		DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/getDeptMember");
		OapiUserGetDeptMemberRequest req = new OapiUserGetDeptMemberRequest();
		req.setDeptId(dep_id);
		req.setHttpMethod("GET");
		try {
			OapiUserGetDeptMemberResponse response = client.execute(req, accessToken);
			System.out.println(response.getBody());
			if (response.getErrcode() == 0)
				result = response.getUserIds();
				String ss = String.join(",", result);
				add(key, ss, 1000);
		} catch (ApiException e) {
			e.printStackTrace();
		}
		return result;
	}

	// 权限列表
	public String roleList() {
		String result = "";
		try {
			DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/role/list");
			OapiRoleListRequest req = new OapiRoleListRequest();
			req.setSize(10L);
			req.setOffset(0L);
			OapiRoleListResponse rsp = client.execute(req, token());
			result = rsp.getBody();

		} catch (ApiException e) {
			e.printStackTrace();
		}
		return result;
	}

	public void registerCallback() {
		String accessToken = token();
//		DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/call_back/register_call_back");
		DingTalkClient  client = new DefaultDingTalkClient("https://oapi.dingtalk.com/call_back/update_call_back");

		OapiCallBackRegisterCallBackRequest request = new OapiCallBackRegisterCallBackRequest();
		request.setUrl("http://oa.zhuozhengzs.com:8080/prism/dingtalk?action=callback");
		request.setAesKey(Ding.CALL_AES_KEY);
		System.out.println(accessToken);

		request.setToken(Ding.CALL_BACK_TOKEN);
		request.setCallBackTag(Arrays.asList("user_add_org", "user_modify_org", "user_leave_org","org_dept_modify","org_dept_remove"));
		try {
			OapiCallBackRegisterCallBackResponse response = client.execute(request, accessToken);
			System.out.println(response.getBody());
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws ApiException {
		DingTalk dt = new DingTalk();
		
		System.out.println(dt.token());

	}
}
