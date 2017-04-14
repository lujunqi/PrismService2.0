package test;

import java.io.IOException;

import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class Test0306 {
	public static void main(String[] args) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet("http://www.ccir.com.cn/order/search.aspx?typeID=1&keyWords=W1411070002"); // 请求地址
		try {
			httpGet.setConfig(createConfig(5000, false));
			CloseableHttpResponse response = httpClient.execute(httpGet);
			Header h = response.getFirstHeader("Location");
			String location = "www.ccir.com.cn" + h.getValue();
			HttpGet getMethod = new HttpGet(location);
			response = httpClient.execute(getMethod);
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static RequestConfig createConfig(int timeout, boolean redirectsEnabled) {
		return RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout).setConnectionRequestTimeout(timeout).setRedirectsEnabled(redirectsEnabled).build();
	}

	public static void get(String url) {
		HttpGet httpGet = new HttpGet(url);// 创建get请求
		System.out.println(sendHttpGet(httpGet));
	}

	private static String sendHttpGet(HttpGet httpGet) {
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse response = null;
		HttpEntity entity = null;
		String responseContent = null;
		try {
			// 创建默认的httpClient实例.
			httpClient = HttpClients.createDefault();
			// httpGet.setConfig(requestConfig);
			// 执行请求
			response = httpClient.execute(httpGet);

			entity = response.getEntity();
			responseContent = EntityUtils.toString(entity, "UTF-8");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// 关闭连接,释放资源
				if (response != null) {
					response.close();
				}
				if (httpClient != null) {
					httpClient.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return responseContent;
	}
}
