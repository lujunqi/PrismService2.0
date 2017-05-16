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
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

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
			SimpleDateFormat formatter = new SimpleDateFormat ("yyyyMMdd"); 
			String filename = formatter.format(new Date());
			File nfile = new File(file,filename+".sql");
			if (file.isDirectory()) {
				if(nfile.canExecute()){
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
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

			}

		}

	}

}
