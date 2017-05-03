package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.prism.dbutil.DBCommand;
import com.prism.dbutil.DBConnection;
import com.prism.exception.DAOException;

public class Test0420 {
	public static void main(String[] args) throws Exception{
		String sql = "SELECT u.*,us.user_m_acc FROM (select * from lb_user where user_acc != ${user_acc<STRING>}) u LEFT JOIN"+ 
		"(select user_m_acc,user_s_acc from lb_user_acc_sm where user_m_acc = ${user_acc<STRING>}) us "+
		"on u.user_acc = us.user_s_acc";
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("user_acc", "lb");

		
		db(sql,map);

		
	}
	private static void db(String sql,Map<String, Object> map) throws DAOException {
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

		System.out.println(cmd.executeSelect(sql, map));;
		dconn.closeConnection();
	}
	public static void main1(String[] args) throws Exception {
		//long last = 0;
		Map<String,Long> lasts = new HashMap<String,Long>();
		@SuppressWarnings("serial")
		List<String> names = new ArrayList<String>() {
			{
				add("lb_cust_info_upt");
				add("lb_cust_info_add");
				
				add("lb_cust_info");
				add("lb_cust_infos");
			}
		};
		int i = 0;
		while (true) {
			String name = names.get(i);
			i++;
			if(i>=names.size()){
				i=0;
			}
			File file = new File("e:/test/" + name + ".txt");
			long last = 0;
			if(lasts.containsKey(name)){
				last = lasts.get(name);
			}
			if (last != file.lastModified()) {
				lasts.put(name,file.lastModified());
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), "utf-8");// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String val = "";
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					val += lineTxt + "\n";
				}

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
				String sql = "update sm_bean set val=${val<STRING>} where action='" + name + "'";
				cmd.executeUpdate(sql, map);
				dconn.closeConnection();
				read.close();
				System.out.println(name+"="+last);
			} else {
				Thread.sleep(500);
			}
		}

	}
}