package test;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 * TODO(用一句话描述该文件的作用)
 * 
 * @title: HttpClientDemo.java
 * @author zhangjinshan-ghq
 * @date 2014-6-11 14:59:04
 */

public class HttpClientDemo {

	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 * @throws Exception
	 *             the exception
	 */
	public static void main(String[] args) throws Exception {
		getResoucesByLoginCookies();
	}

	/**
	 * 
	 * @throws Exception
	 */
	private static void getResoucesByLoginCookies() throws Exception {
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet("http://www.xgpx.net/tp/View.asp?id=62");
		httpGet.setHeader("Host", "www.xgpx.net");
		httpGet.setHeader("Referer", "http://www.xgpx.net/tp/cz1.asp?cz_s=62&cz.x=18&cz.y=9&cz=%B2%E9%D5%D2&from=timeline&isappinstalled=0");
		httpGet.setHeader("Cookie", "td_cookie=18446744070115517588; ASPSESSIONIDQQQTAQDA=NPDLMEJCGEBDEFIPHLMNEGEC");
		
	

		HttpResponse httpresponse = httpClient.execute(httpGet);
		HttpEntity entity = httpresponse.getEntity();
		String body = EntityUtils.toString(entity);
		System.out.println(new String(body.getBytes(),"UTF-8"));
		
	}

}