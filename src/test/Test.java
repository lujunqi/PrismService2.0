package test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import sun.misc.BASE64Encoder;

public class Test {

	public static void main(String[] args) {
		int i = 0;
		while (true) {
			i++;
			if (i % 3 == 2) {
				if (i % 5 == 4) {
					if (i % 7 == 6) {
						if (i % 9 == 8) {
							if (i % 11 == 0) {
								break;
							}
						}
					}
				}

			}
		}
		System.out.println(i);
	}

	// 图片转化成base64字符串
	public static String GetImageStr() {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
		String imgFile = "e://0003.jpg";// 待处理的图片
		InputStream in = null;
		byte[] data = null;
		// 读取图片字节数组
		try {
			in = new FileInputStream(imgFile);
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 对字节数组Base64编码
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(data);// 返回Base64编码过的字节数组字符串
	}
}
