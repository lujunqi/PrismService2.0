package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import com.prism.dbutil.DBCommand;
import com.prism.dbutil.DBConnection;
import com.prism.exception.DAOException;

public class Test0425 {

	public static void main(String[] args) throws Exception {
		Map<String, Long> lasts = new HashMap<String, Long>();
		while (true) {
			File f1 = new File("e:/test/liubin");
			File[] files = f1.listFiles();
			for (File f2 : files) {
				String name = f2.getName().split("\\.")[0];
				String key = f2.getName().split("\\.")[1];
				
				long last = 0;
				if (lasts.containsKey(name)) {
					last = lasts.get(name);
				}
				if (last != f2.lastModified()) {
					lasts.put(name, f2.lastModified());
					InputStreamReader read = new InputStreamReader(new FileInputStream(f2), "utf-8");// 考虑到编码格式
					BufferedReader bufferedReader = new BufferedReader(read);
					String val = "";
					String lineTxt = null;
					while ((lineTxt = bufferedReader.readLine()) != null) {
						val += lineTxt + "\n";
					}
					db(val, name,key);
					System.out.println(name+"="+key+"="+last);
					read.close();
				} else {
					Thread.sleep(500);
				}
			}
		}
	}

	private static void db(String val, String name,String key) throws DAOException {
		DBConnection dconn = new DBConnection();
		dconn.setJDBC(new HashMap<String, String>() {
			private static final long serialVersionUID = 1L;
			{
				put("driverClassName", "com.mysql.jdbc.Driver");
				put("url", "jdbc:mysql://127.0.01:3306/prism");
				put("username", "root");
				put("password", "lost");
			}
		});

		DBCommand cmd = new DBCommand(dconn);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("val", val);
		map.put("action", name);
		map.put("key", key.toUpperCase());

		String sql = "REPLACE into sm_bean (action,`KEY`,val)values(${action<STRING>},${key<STRING>},${val<STRING>})";
		cmd.executeUpdate(sql, map);
		dconn.closeConnection();
	}
}
