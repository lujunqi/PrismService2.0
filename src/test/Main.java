package test;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.dingtalk.openapi.demo.utils.aes.PKCS7Padding;
import com.alibaba.dingtalk.openapi.demo.utils.aes.Utils;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiUserGetRequest;
import com.dingtalk.api.response.OapiUserGetResponse;
import com.prism.db.Prism;
import com.prism.ding.DingTalk;
import com.taobao.api.ApiException;

public class Main {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		try {
			String text = "{\r\n" + 
					"    \"EventType\": \"user_add_org\",\r\n" + 
					"    \"TimeStamp\": 43535463645,\r\n" + 
					"    \"UserId\": [\"1637031228850937\" , \"4927116733362571\"],\r\n" + 
					"    \"CorpId\": \"corpid\"\r\n" + 
					"}";
			JSONObject plainTextJson = JSONObject.parseObject(text);		
			
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static String encrypt() throws Exception {
		try {
			String data = "Test String";
			String key = "1234567812345678";
			String iv = "1234567812345678";
			Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
			int blockSize = cipher.getBlockSize();
			byte[] dataBytes = data.getBytes();
			int plaintextLength = dataBytes.length;
			if (plaintextLength % blockSize != 0) {
				plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
			}
			byte[] plaintext = new byte[plaintextLength];
			System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
			SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
			IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());
			cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
			byte[] encrypted = cipher.doFinal(plaintext);
			return new sun.misc.BASE64Encoder().encode(encrypted);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	 private static final Charset CHARSET = Charset.forName("utf-8");
	public static String desEncrypt() throws Exception {
		try {
			String data = "8x2C3CeCmWTSZsaFIyTq1pbqT8/YuvsvwPQoAkPS7+Jd4pOTGUGQBwAUzZc198gDn451l7YRgLiQF4WkdgmZGOOqY3WiYjJojzIVkzrL2z4zCy/ftptmy9zQjatBlbJg=";
			String key = "1234567812345678";
			String iv = "1234567812345678";
			 String encodingAesKey = "xxxxxxxxlvdhntotr3x9qhlbytb18zyz5zxxxxxxxxx";
			byte[] aesKey = Base64.decodeBase64(encodingAesKey + "=");
			byte[] encrypted1 = new sun.misc.BASE64Decoder().decodeBuffer(data);
			Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
			SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
			IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());
			cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
			byte[] original = cipher.doFinal(encrypted1);
			byte[] bytes = PKCS7Padding.removePaddingBytes(original);
			byte[] networkOrder = Arrays.copyOfRange(bytes, 16, 20);
			int plainTextLegth = Utils.bytes2int(networkOrder);
			String plainText = new String(Arrays.copyOfRange(original, 20, 20 + plainTextLegth), CHARSET);
			
			return plainText;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public static void main1(String[] args) {
		try {

			ApplicationContext beans = new ClassPathXmlApplicationContext("classpath:spring.xml");
			Prism prism = (Prism) beans.getBean("lb.users");
			prism.setContext(beans);
			Map<String, Object> m = prism.db(new HashMap<String, Object>());
			List<Map<String, Object>> data = (List<Map<String, Object>>) m.get("data");
			for (Map<String, Object> map : data) {

				user(map.get("user_id") + "", beans);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void user(String usid, ApplicationContext beans) {
		DingTalk dt = new DingTalk();
		String accessToken = dt.token();
		DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/get");
		OapiUserGetRequest req = new OapiUserGetRequest();
		req.setUserid(usid);
		req.setHttpMethod("GET");
		try {
			OapiUserGetResponse rsp = client.execute(req, accessToken);
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("user_id", rsp.getUserid());
			param.put("user_name", rsp.getName());
			if (!"0".equals(rsp.getErrorCode())) {
				System.out.println(rsp.getBody());
			}
			List<Long> dep = rsp.getDepartment();
			String[] tem = new String[dep.size()];
			for (int i = 0; i < dep.size(); i++) {
				tem[i] = dep.get(i) + "";
			}
			String ss = String.join(",", tem);
			param.put("user_dep", ss);
			param.put("user_body", rsp.getBody());

			Prism prism = (Prism) beans.getBean("lb.users.add");
			prism.setContext(beans);
			Map<String, Object> m = prism.db(param);
			System.out.println(m);
		} catch (ApiException e) {
			//
			e.printStackTrace();
		}
	}
}
