package test;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class Test0419 {

	public static void main(String[] args) throws Exception {
		while (true) {
			HttpClient httpclient = new DefaultHttpClient();

			// 新建Http post请求
			HttpPost httppost = new HttpPost("http://www.xgpx.net/tp/cz1.asp?cz_s=62&cz.x=18&cz.y=9&cz=%B2%E9%D5%D2&from=timeline&isappinstalled=0");

			// 处理请求，得到响应
			HttpResponse response = httpclient.execute(httppost);

			// String set_cookie =
			// response.getFirstHeader("Set-Cookie").getValue();

			// 打印Cookie值
			// System.out.println(set_cookie.substring(0,set_cookie.indexOf(";")));

			// 打印返回的结果
			HttpEntity entity = response.getEntity();

			StringBuilder result = new StringBuilder();
			if (entity != null) {
				InputStream instream = entity.getContent();
				BufferedReader br = new BufferedReader(new InputStreamReader(instream));
				String temp = "";
				while ((temp = br.readLine()) != null) {
					String str = new String(temp.getBytes(), "GBK");
					result.append(str + "\n");
				}
			}
			httppost = new HttpPost("http://www.xgpx.net/tp/View.asp?id=62");

			// 处理请求，得到响应
			response = httpclient.execute(httppost);

			// String set_cookie =
			// response.getFirstHeader("Set-Cookie").getValue();

			// 打印Cookie值
			// System.out.println(set_cookie.substring(0,set_cookie.indexOf(";")));

			// 打印返回的结果
			entity = response.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				BufferedReader br = new BufferedReader(new InputStreamReader(instream));
				String temp = "";
				while ((temp = br.readLine()) != null) {
					String str = new String(temp.getBytes(), "GBK");
					System.out.println(str);
				}
			}
//			writeHTMLtoFile(entity, "d:\\xx.htm");
			System.out.println("=====");
		}
	}

	public static void writeHTMLtoFile(HttpEntity entity, String pathName) throws Exception {

		byte[] bytes = new byte[(int) entity.getContentLength()];

		FileOutputStream fos = new FileOutputStream(pathName);

		bytes = EntityUtils.toByteArray(entity);

		fos.write(bytes);

		fos.flush();

		fos.close();
	}

}
