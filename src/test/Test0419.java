package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

import org.apache.catalina.loader.WebappClassLoader;

import templete.DynamicObj;

public class Test0419 {

	public static void main(String[] args) throws Exception {
		String fileName = "e:/dclass/TT2.java";
		Test0419 t0419 = new Test0419();
		File file = new File(fileName);
		// 编译
		t0419.compiler(file);
		// 执行
		DynamicObj dynamic = t0419.run(file,DynamicObj.class);
		Map<String,Object> param = new HashMap<String,Object>();
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		for(int i=0;i<10;i++){
			Map<String,Object> m = new HashMap<String,Object>();
			m.put("c1", "v"+i);
			list.add(m);
		}
		param.put("this",list);
		dynamic.run(list);
	}

	@SuppressWarnings("unchecked")
	private <T> T run(File file, Class<T> c) throws MalformedURLException,
			ClassNotFoundException, InstantiationException,
			IllegalAccessException {
		String fileName = file.getName();
		fileName = fileName.substring(0, fileName.length() - 5);
		URL[] urls = new URL[] { new URL(new File(file.getParent()).toURI()
				+ "") };
		
//		@SuppressWarnings("resource")
		URLClassLoader ucl = new URLClassLoader(urls);
		c = (Class<T>) ucl.loadClass(fileName);
		System.out.println(ucl.findResource(fileName));
		return c.newInstance();
	}

	private void compiler(File file) {
		String filePath = file.getAbsolutePath();
		String fileName = file.getName();
		File pfile = new File(file.getParentFile(), fileName.replaceAll("\\.",
				"-"));
		pfile.mkdir();
		File markfile = new File(pfile, getMd5ByFile(file));
		if (!markfile.exists()) {
			JavaCompiler compiler2 = ToolProvider.getSystemJavaCompiler();
			int results = compiler2.run(null, null, null, filePath);
			if (results == 0) {
				try {
					File[] f = pfile.listFiles();
					for (int i = 0; i < f.length; i++) {
						f[i].delete();
					}
					markfile.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	private String getMd5ByFile(File file) {
		String value = null;
		FileInputStream in = null;
		try {
			in = new FileInputStream(file);
			MappedByteBuffer byteBuffer = in.getChannel().map(
					FileChannel.MapMode.READ_ONLY, 0, file.length());
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(byteBuffer);
			BigInteger bi = new BigInteger(1, md5.digest());
			value = bi.toString(16);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return value;
	}
}
