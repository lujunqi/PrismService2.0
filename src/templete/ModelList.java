package templete;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.stereotype.Component;

import com.prism.exception.BMOException;

@Component("ModelList")
public class ModelList implements Templete {
	public static void main(String[] args) {
		ModelList m = new ModelList();
		String tm = "#set($m={});\n" + "$m.put('k','\"xx');\n" + "$req.put('BUTTON',$m);";
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			m.vc.put("req", map);
			m.getResultfromContent(tm);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private VelocityContext vc = new VelocityContext();

	public void service(Map<String, Object> sourceMap, HttpServletRequest req, HttpServletResponse res) throws BMOException {
		for (Map.Entry<String, Object> en : sourceMap.entrySet()) {
			vc = new VelocityContext();
			String key = en.getKey();
			Object val = en.getValue();
			if ("MAPPING".equals(key)) {
				try {
//					System.out.println(val);
//					System.out.println(req);
					
					vc.put("Req", req);
					getResultfromContent(val + "");
				} catch (Exception e) {
					System.out.println(val);
					e.printStackTrace();
				}
			}
//			 System.out.println(req.getMethod()+"============="+req.getQueryString());
			// 验证签名
			if ("SIGN".equals(key) && !"v".endsWith(getExtendName(req))) {
				String sign = "xx";
				Map<String, Integer> mark = new HashMap<String, Integer>();
				if ("POST".equals(req.getMethod())) {
					for (String fd : req.getHeader("FormData").split("&")) {
						if (fd.indexOf("%5B%5D") == -1) {
							sign += "&" + fd + "=" + req.getParameter(fd);
						} else {
							String markkey = fd.substring(0, fd.indexOf("%5B%5D"));
							if (mark.containsKey(markkey)) {
								mark.put(markkey, mark.get(markkey) + 1);
							} else {
								mark.put(markkey, 0);
							}
							sign += "&" + fd + "=" + req.getParameterMap().get(markkey + "[]")[mark.get(markkey)];
						}
					}
					
				} else if ("GET".equals(req.getMethod())) {
					
					sign += "&"+req.getRequestURI().replaceAll(req.getContextPath(), "")+"?"+req.getQueryString();
				}
				Map<String, String> cookieMap = new HashMap<String, String>();
				for (Cookie cookie : req.getCookies()) {
					String name = cookie.getName();
					String value = cookie.getValue();
					cookieMap.put(name, value);
				}
				StringBuffer buffer = new StringBuffer();
				try {
					MessageDigest digest = MessageDigest.getInstance("md5");
					byte[] result = digest.digest(sign.getBytes());
					for (byte b : result) {
						// 与运算
						int number = b & 0xff;// 加盐
						String str = Integer.toHexString(number);
						if (str.length() == 1) {
							buffer.append("0");
						}
						buffer.append(str);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				if (!buffer.toString().equals(cookieMap.get("SIGN"))) {
					res.setStatus(401);
					throw new BMOException("用户未授权访问");
				}

			}

			req.setAttribute(key, val);

		}
	}

	private String getExtendName(HttpServletRequest req) {
		try {
			String relativeuri = req.getRequestURI().replaceFirst(req.getContextPath(), "");
			// relativeuri = relativeuri.replaceAll("/", "");
			int exLen = relativeuri.lastIndexOf(".");
			StringBuffer sb = new StringBuffer(relativeuri);
			return sb.substring(exLen + 1);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	private String getResultfromContent(String s) throws Exception {
		StringWriter stringwriter;
		Velocity.init();
		stringwriter = new StringWriter();
		Velocity.evaluate(vc, stringwriter, "mystring", s);
		return stringwriter.toString();
	}

}
