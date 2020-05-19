package com.prism.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.dingtalk.openapi.demo.utils.aes.DingTalkEncryptException;
import com.alibaba.dingtalk.openapi.demo.utils.aes.DingTalkEncryptor;
import com.alibaba.fastjson.JSONObject;
import com.prism.db.Prism;
import com.prism.ding.Ding;
import com.prism.ding.DingTalk;

import redis.clients.jedis.Jedis;

@WebServlet("/dingtalk")
public class DingTalkAction extends PrismAction {

	private static final long serialVersionUID = -1974119664570518188L;

	public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		Map<String, Object> param = new LinkedHashMap<String, Object>();
		if (reloadXML()) {
			beans = new ClassPathXmlApplicationContext(xmls);

		}
		this.req = req;
		this.res = res;
		service(null, param);
	}

	@Override
	protected Map<String, Object> service(Prism prism, Map<String, Object> param) {
		try {
			req.setCharacterEncoding("UTF-8");
			res.setContentType("text/html;charset=UTF-8");
			String action = req.getParameter("action");

			DingTalk dt = new DingTalk();
			PrintWriter out = res.getWriter();
			if ("roleList".equals(action)) {
				String role = dt.roleList();
				role = role.replaceAll("\"name\"", "\"spread\":true,\"title\"");
				role = role.replaceAll("groupId", "id");
				role = role.replaceAll("roles", "children");

				out.print(role);
			} else if (Ding.DEP_LIST.equals(action)) {

				String info = dt.depList();

				out.print(info);
			} else if ("callback".equals(action)) {
				out.print(callback(req, res));
			} else {
				String code = req.getParameter("code");
				String userId = dt.user(code);

				String info = dt.userInfo(userId);

				out.print(info);
			}
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	@SuppressWarnings({ "unchecked", "resource" })
	private String callback(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		/** url中的签名 **/
		String msgSignature = request.getParameter("signature");
		/** url中的时间戳 **/
		String timeStamp = request.getParameter("timestamp");
		/** url中的随机字符串 **/
		String nonce = request.getParameter("nonce");

		/** post数据包数据中的加密数据 **/
		ServletInputStream sis = request.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(sis));
		String line = null;
		StringBuilder sb = new StringBuilder();
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}
		JSONObject jsonEncrypt = JSONObject.parseObject(sb.toString());
		String encrypt = jsonEncrypt.getString("encrypt");

		/** 对encrypt进行解密 **/
		DingTalkEncryptor dingTalkEncryptor = null;
		String plainText = null;
		try {
			// 对于DingTalkEncryptor的第三个参数，ISV进行配置的时候传对应套件的SUITE_KEY，普通企业传Corpid
			dingTalkEncryptor = new DingTalkEncryptor(Ding.CALL_BACK_TOKEN, Ding.CALL_AES_KEY, Ding.CORPID);
			plainText = dingTalkEncryptor.getDecryptMsg(msgSignature, timeStamp, nonce, encrypt);
		} catch (DingTalkEncryptException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		/** 对从encrypt解密出来的明文进行处理 **/

		JSONObject plainTextJson = JSONObject.parseObject(plainText);
		
		String eventType = plainTextJson.getString("EventType");
		System.out.println("DingTalkAction119:" + eventType);

		DingTalk dt = new DingTalk();
		List<String> users = plainTextJson.getObject("UserId", List.class);
		List<String> deptIds = plainTextJson.getObject("DeptId", List.class);
		Jedis jedis = new Jedis("localhost");
		switch (eventType) {
		case "user_add_org":// 通讯录用户增加 do something

			break;
		case "user_modify_org":// 通讯录用户更改 do something

			for (String userId : users) {
				String u = dt.userInfo(userId);

				JSONObject umap = JSONObject.parseObject(u);

				Map<String, Object> param = new HashMap<String, Object>();
				param.put("user_id", umap.getString("userid"));
				param.put("user_name", umap.getString("name"));
				param.put("user_dep", umap.getString("department").replaceAll("\\[", "").replaceAll("\\]", ""));
				param.put("user_body", u);

				user(param, "lb.users.add");
			}

			break;
		case "user_leave_org":// 通讯录用户离职 do something
			for (String userId : users) {
			 
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("user_id", userId);
				user(param, "lb.users.leave");
			}
			break;
		case "org_admin_add":// 通讯录用户被设为管理员 do something
			break;
		case "org_admin_remove":// 通讯录用户被取消设置管理员 do something
			break;
		case "org_dept_create":// 通讯录企业部门创建 do something

			break;
		case "org_dept_modify":// 通讯录企业部门修改 do something
			for (String deptId : deptIds) {
				String key = Ding.DEP_M_X + deptId;
				jedis.del(key);
			}
			break;
		case "org_dept_remove":// 通讯录企业部门删除 do something
			for (String deptId : deptIds) {
				String key = Ding.DEP_M_X + deptId;
				jedis.del(key);
			}
			break;
		case "org_remove":// 企业被解散 do something
			break;

		case "check_url":// do something
		default: // do something
			break;
		}

		/** 对返回信息进行加密 **/
		long timeStampLong = Long.parseLong(timeStamp);
		Map<String, String> jsonMap = null;
		try {
			jsonMap = dingTalkEncryptor.getEncryptedMap("success", timeStampLong, nonce);
		} catch (DingTalkEncryptException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		JSONObject json = new JSONObject();
		json.putAll(jsonMap);

		return json.toString();
	}

	private void user(Map<String, Object> param, String key) {
		System.out.println(param);
		Prism prism = (Prism) beans.getBean(key);
		prism.setContext(beans);
		Map<String, Object> db = prism.db(param);
		System.out.println(db);

	}

}
