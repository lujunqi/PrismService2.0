package com.prism.db;

import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import com.mysql.jdbc.Statement;
import com.prism.db.Param.P;


public class DB {
	private VelocityContext vc = new VelocityContext();
	
	private Param p = new Param();
	private Connection conn;
//	private String forName, url, user, passWord;
	private BasicDataSource ds ;
	public DB(BasicDataSource ds) {
		this.ds = ds;
//		forName = "com.mysql.jdbc.Driver";
//		url = "jdbc:mysql://127.0.0.1:3306/majun?useUnicode=true&amp;characterEncoding=UTF-8";
//		user = "xiaom";
//		passWord = "xiaom";

		initVelocity();
	}

	private void initVelocity() {
		vc = new VelocityContext();
		p.list.clear();
		vc.put("p", p);
		vc.put("fn", p);
		vc.put("VARCHAR", Types.VARCHAR);
		vc.put("str", Types.VARCHAR);
		vc.put("string", Types.INTEGER);
		vc.put("int", Types.INTEGER);
		vc.put("DATE", Types.DATE);
	}

	public void setAutoCommit(boolean flag) {
		try {
			this.conn.setAutoCommit(flag);
		} catch (SQLException e) {

		}
	}

	public void commit() {
		try {
			
			if (conn!=null && !conn.getAutoCommit()) {

				this.conn.commit();
			}
				
		} catch (SQLException e) {

		}
	}

	public void closeConn() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private String sql(String sql, Map<String, Object> param) {
		Velocity.init();
		StringWriter stringwriter = new StringWriter();
		initVelocity();
		for (Map.Entry<String, Object> en : param.entrySet()) {
			vc.put(en.getKey(), en.getValue());
		}
		Velocity.evaluate(vc, stringwriter, "mystring", sql);
		return stringwriter.toString();

	}
	public int delete (String sql,Map<String,Object> param) throws SQLException {
		
		return executeUpdate(sql,param,false)[0];
	}
	public Object insert (String sql,Map<String,Object> param,boolean updateReturn) throws SQLException {
		if(updateReturn) {
			int[] data = executeUpdate(sql,param,updateReturn);
	        Integer[] integers1 = Arrays.stream(data).boxed().toArray(Integer[]::new);
	        List<Integer> list2 = Arrays.asList(integers1);
			return list2;	
		}else {
			return executeUpdate(sql,param,false)[0];
		}
		
	}
	public int update (String sql,Map<String,Object> param) throws SQLException {
		return executeUpdate(sql,param,false)[0];
	}
	
	private int[] executeUpdate(String sql, Map<String, Object> param,boolean updateReturn) throws SQLException {
		String nSql = sql(sql, param);
		
		if (conn == null || conn.isClosed()) {
			setConn();
		}
		
		PreparedStatement stmt = conn.prepareStatement(nSql);
		if (updateReturn) {
			stmt = conn.prepareStatement(nSql, Statement.RETURN_GENERATED_KEYS);
		}

		setStmt(stmt);

		if (updateReturn) {
			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			
			
			List<Integer> l = new ArrayList<Integer>();
			while (rs.next()) {
				l.add(rs.getInt(1));
			}
	        int[] arr = l.stream().mapToInt(Integer::valueOf).toArray();

			return arr;
		} else {
			int[] num = {stmt.executeUpdate()};
			return num;
		}
		
		
	}
	public Map<String, Object> selectOne(String sql, Map<String, Object> param) throws SQLException {
		Map<String, Object> map = new HashMap<String, Object>();
		String nSql = sql(sql, param);
		if (conn == null || conn.isClosed()) {
			setConn();
		}
		PreparedStatement stmt = conn.prepareStatement(nSql);
		setStmt(stmt);

		ResultSet rs = stmt.executeQuery();
		ResultSetMetaData rsmd = rs.getMetaData();
		int count = rsmd.getColumnCount();
		String[] name = new String[count];
		for (int i = 0; i < count; i++) {
			name[i] = rsmd.getColumnName(i + 1);
		}
		while (rs.next()) {
			for (int i = 0; i < name.length; i++) {
				map.put(name[i], rs.getObject(i + 1));
			}
			break;
		}
		
		rs.close();
		stmt.close();

		return map;
	}
	public Object selectObj(String sql, Map<String, Object> param) throws SQLException {
		String nSql = sql(sql, param);
		if (conn == null || conn.isClosed()) {
			setConn();
		}
		PreparedStatement stmt = conn.prepareStatement(nSql);
		setStmt(stmt);

		ResultSet rs = stmt.executeQuery();
		if (rs.next()) {
			return rs.getObject(1);
		
		}
		
		rs.close();
		stmt.close();

		return null;
	}
	
	public List<Map<String, Object>> select(String sql, Map<String, Object> param) throws SQLException {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String nSql = sql(sql, param);
		if (conn == null || conn.isClosed()) {
			setConn();
		}
		PreparedStatement stmt = conn.prepareStatement(nSql);
		setStmt(stmt);

		ResultSet rs = stmt.executeQuery();
		ResultSetMetaData rsmd = rs.getMetaData();
		int count = rsmd.getColumnCount();
		String[] name = new String[count];
		for (int i = 0; i < count; i++) {
			name[i] = rsmd.getColumnName(i + 1);
		}
		while (rs.next()) {
			Map<String, Object> map = new HashMap<String, Object>();
			for (int i = 0; i < name.length; i++) {
				map.put(name[i], rs.getObject(i + 1));
			}
			list.add(map);
		}
		
		rs.close();
		stmt.close();

		return list;
	}

	private void setStmt(PreparedStatement stmt) throws SQLException {
		for (int i = 0; i < p.list.size(); i++) {
			P _p = p.list.get(i);

			if (_p.containsKey("type")) {
				stmt.setObject(i + 1, _p.get("val"), _p.getType());
			} else {
				stmt.setObject(i + 1, _p.get("val"));
			}
		}
	}

	public void setConn() {
		try {
			
			conn = ds.getConnection();
		} catch ( SQLException e) {
			e.printStackTrace();
		}

	}



}
