package test;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

public class Test {
	private static String JAVA_SOURCE_FILE = "DynamicObject.java";
	private static String JAVA_CLASS_FILE = "DynamicObject.class";
	private static String JAVA_CLASS_NAME = "DynamicObject";

	public static void main(String[] args) throws Exception,
			InterruptedException {
		String fileName = "e:\\" + JAVA_SOURCE_FILE;
		// 创建java文件
		// String tr = "\r\n";
		// String source = "public class "
		// + JAVA_CLASS_NAME + "{ " + tr
		// + "    public static void main(String[] args) {" + tr
		// + "        System.out.println(\"Hello World!\");" + tr
		// + "    } " + tr + "}";
		// System.out.println(System.getProperty("user.dir")+"==========");

		// FileWriter fw = new FileWriter(fileName); // 字符输出流
		// PrintWriter pw = new PrintWriter(fw); // 将字节输出流转为PrintWriter
		// pw.write(source);
		// pw.close();
		// 编译java文件
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		System.out.println(compiler);
		StandardJavaFileManager fileManager = compiler.getStandardFileManager(
				null, null, null);
		Iterable<? extends JavaFileObject> sourcefiles = fileManager
				.getJavaFileObjects(fileName);
		// 指定编译文件存放位置,如果不指定的话，编译的文件会和java源文件在一个文件夹中
		// 这样的话加载类的时候会报java.lang.ClassNotFoundException
		Iterable<String> options = Arrays.asList("-d",
				System.getProperty("user.dir")
						+ "\\WebContent\\WEB-INF\\classes");
		System.out.println(options);
		compiler.getTask(null, fileManager, null, options, null, sourcefiles)
				.call();
		fileManager.close();

		JavaCompiler compiler2 = ToolProvider.getSystemJavaCompiler();
		int results = compiler2.run(null, null, null, fileName);
		if (results == 0) {
			Runtime run = Runtime.getRuntime();
	        Process process = run.exec("java -cp ./temp temp/com/Hello");
	        InputStream in = process.getInputStream();
	        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
	        String info  = "";
	        while ((info = reader.readLine()) != null) {
	            System.out.println(info);
	                
	        }
		}
	}
	
	
}
