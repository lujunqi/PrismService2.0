package templete;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

import org.apache.catalina.loader.WebappClassLoader;

public class PrismCompiler {
	public Class<?>  compilerRun(File file) {
		compiler(file);
		return run(file);
	}

	@SuppressWarnings({  "resource" })
	private  Class<?> run(File file) {
		try {
			

			
			String fileName = file.getName();
			fileName = fileName.substring(0, fileName.length() - 5);

			URL url1 = new URL(new File(file.getParent()).toURI() + "");
			URL url2 = new URL(
					new File(getClass().getResource("/").getPath()).toURI()
							+ "");
			URL[] urls = new URL[] { url1, url2 };
			URLClassLoader ucl = new URLClassLoader(urls);

//			c = (Class<T>) (ucl.loadClass(fileName));
//			
//			System.out.println("==53");
//			System.out.println("父类:"+c.newInstance().getClass().getSuperclass().getName());
//			System.out.println("子类:"+c.newInstance().getClass().getName());
//			Method method = c.getMethod("run", Map.class);
//			method.invoke(c.newInstance(), new HashMap<String,Object>());	
			
			return ucl.loadClass(fileName);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private void compiler(File file) {
		String filePath = file.getAbsolutePath();
		String fileName = file.getName();
		File pfile = new File(file.getParentFile(), fileName.replaceAll("\\.",
				"-"));
		pfile.mkdir();
		File markfile = new File(pfile, getMd5ByFile(file));

		 
		if (!markfile.exists()) {
			JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
			int result = javaCompiler.run(null, null, null, "-classpath",
					getClass().getResource("/").getPath(),"-encoding","utf-8",filePath);
			System.out.println(result == 0 ? "恭喜编译成功" : "对不起编译失败");

			if (result == 0) {
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
