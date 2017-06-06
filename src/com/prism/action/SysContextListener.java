/*
 * 数据备份 每天一次
 */
package com.prism.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.eclipse.jdt.internal.compiler.batch.Main;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

public class SysContextListener implements ServletContextListener {
	private Timer timer = null;
	String hostIP = "127.0.0.1";
	String userName = "root";
	String password = "lost";
	String databaseName = "prism";
	String dirpath = "e:/soft";
	String exec = " D:\\tool\\mysql-5.6.24-win32\\bin\\mysqldump ";

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		timer.cancel();
		event.getServletContext().log("定时器销毁");

	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		timer = new Timer(true);
		timer.schedule(new MyTask(event.getServletContext()), 10, 10000);

	}

	class MyTask extends TimerTask {
		ServletContext context;

		public MyTask(ServletContext context) {
			this.context = context;
		}

		PrintWriter printWriter = null;
		BufferedReader bufferedReader = null;

		@Override
		public void run() {
			File file = new File(dirpath);
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
			String filename = formatter.format(new Date());
			File nfile = new File(file, filename + ".sql");
			File zfile = new File(file, filename + ".sql.zip");
			
			if (file.isDirectory()) {
				if (zfile.canExecute()) {
					return;
				}

				try {
					printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(nfile), "utf8"));
					Process process = Runtime.getRuntime().exec(exec + "-h" + hostIP + " -u" + userName + " -p" + password + " --set-charset=UTF8 " + databaseName);
					InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream(), "utf8");
					bufferedReader = new BufferedReader(inputStreamReader);
					String line;
					while ((line = bufferedReader.readLine()) != null) {
						printWriter.println(line);
					}
					printWriter.flush();
					if (process.waitFor() == 0) {// 0 表示线程正常终止。
						
						System.out.println("备份完成");
					}
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					try {
						if (bufferedReader != null) {
							bufferedReader.close();
						}
						if (printWriter != null) {
							printWriter.close();
						}
						//备份压缩
						zip(nfile.getAbsolutePath(),nfile.getAbsolutePath()+".zip",false,"123");
						nfile.delete();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

			}

		}

	}

	private String zip(String src, String dest, boolean isCreateDir, String passwd) {
		File srcFile = new File(src);
		// dest = buildDestinationZipFilePath(srcFile, dest);
		ZipParameters parameters = new ZipParameters();
		parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE); // 压缩方式
		parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL); // 压缩级别
		if (passwd != null) {
			parameters.setEncryptFiles(true);
			parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD); // 加密方式
			parameters.setPassword(passwd.toCharArray());
		}
		try {
			ZipFile zipFile = new ZipFile(dest);
			if (srcFile.isDirectory()) {
				// 如果不创建目录的话,将直接把给定目录下的文件压缩到压缩文件,即没有目录结构
				if (!isCreateDir) {
					File[] subFiles = srcFile.listFiles();
					ArrayList<File> temp = new ArrayList<File>();
					Collections.addAll(temp, subFiles);
					zipFile.addFiles(temp, parameters);
					return dest;
				}
				zipFile.addFolder(srcFile, parameters);
			} else {
				zipFile.addFile(srcFile, parameters);
			}
			return dest;
		} catch (ZipException e) {
			e.printStackTrace();
		}
		return null;
	}

}
