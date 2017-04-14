package test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test030601 {
	public static int date = 170101;
	public static int dateq = -1;

	public static void main(String[] args) {
		dateq = date;
		String info1 = info("e:/1.txt");
		int i = 0;
		while (true) {
			try {
				i++;

				Thread.sleep(8000);
				System.out.println(date * 10000 + i);
				String url = "http://www.ccir.com.cn/order/search.aspx?typeID=1&keyWords=W" + (date * 10000 + i);
				String info2 = sendGet(url);
				System.out.println(info2);
				// String info2 = info("e:/2.txt");
				if (info2.indexOf("没有符合条件的结果") != -1) {
					if (i > 50) {
						i = 0;
						date = add(date);

					}
					continue;
				}
//				System.out.println(info1);
//				System.out.println(info2);

				Map<String, String> map = new HashMap<String, String>();
				pickup(info1, info2, map);
				// System.out.println(map);
				String info = map.get("ddh") + ",";
				info += map.get("cpmc") + ",";
				info += map.get("gsmc") + ",";
				info += map.get("txdz") + ",";
				info += map.get("lxr") + ",";
				info += map.get("lxdh") + ",";
				info += map.get("sjhm") + "\n";
				save(info);

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	public static int add(int str) {
		SimpleDateFormat format = new SimpleDateFormat("yyMMdd");
		try {
			Date date = format.parse(str + "");
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			calendar.add(calendar.DATE, 1);// 把日期往后增加一天.整数往后推,负数往前移动
			date = calendar.getTime();
			str = Integer.parseInt(format.format(date));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	public static void save(String data) {
		try {
			FileWriter fileWritter = new FileWriter("e:/yiwei_" + dateq + ".txt", true);
			BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
			bufferWritter.write(data);
			bufferWritter.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void pickup(String info1, String info2, Map<String, String> map) throws Exception {
		try {
			int begin = info1.indexOf("{{");

			info1 = info1.substring(begin + 2);
			int end = info1.indexOf("}}");
			String key = info1.substring(0, end);
			String tmp1 = info2.substring(0, begin);
			int begin2 = info1.indexOf("{{");
			if (begin2 == -1) {
				begin2 = info1.length();
			}
			String tmp2 = info1.substring(end + 2, begin2);
			int begin_info2 = info2.indexOf(tmp1);

			int end_info2 = info2.indexOf(tmp2);
			String value = info2.substring(begin_info2 + tmp1.length(), end_info2);
			info2 = info2.substring(end_info2);
			info1 = info1.substring(end + 2);
			if (!"0".equals(key)) {
				map.put(key, value);

			}
			
			if (info1.indexOf("{{") != -1) {
				pickup(info1, info2, map);
			}
			
		} catch (Exception e) {
//			System.out.println("info1 = "+info1);
//			System.out.println("info2 = "+info2);
//			System.out.println(map);
			throw new Exception();
		}
	}

	public static String info(String filepath) {
		String result = "";
		try {
			byte[] strBuffer = null;
			int flen = 0;
			File xmlfile = new File(filepath);
			try {
				InputStream in = new FileInputStream(xmlfile);
				flen = (int) xmlfile.length();
				strBuffer = new byte[flen];
				in.read(strBuffer, 0, flen);
				in.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			result = new String(strBuffer); // 构建String时，可用byte[]类型，

			result = replaceAll(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	private static String replaceAll(String result) {
		result = result.replaceAll("\n", "");
		result = result.replaceAll("\t", "");
		result = result.replaceAll("\r", "");
		result = result.replaceAll(" ", "");
		result = result.replaceAll("	", "");
		
		result = result.replaceAll("&nbsp;", "");
		return result;
	}

	public static String sendGet(String url) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = url;
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
			// for (String key : map.keySet()) {
			// System.out.println(key + "--->" + map.get(key));
			// }
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line + "\n";
			}
			result = replaceAll(result);

		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}
}
