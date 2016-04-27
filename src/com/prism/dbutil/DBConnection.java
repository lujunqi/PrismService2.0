package com.prism.dbutil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.prism.exception.DAOException;

public class DBConnection {
	public static void main(String[] args) throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		Connection CONN = DriverManager.getConnection(
				"jdbc:mysql://127.0.01:3306/prism",
				"root",
				"lost");
		CONN.createStatement().executeQuery("SELECT * FROM SM_BEAN");
		System.out.println(CONN.isClosed()+"=============");
	}
	private Connection CONN = null;
	private String JNDI = null;
	private Map<String, String> JDBC = new HashMap<String, String>();

	@SuppressWarnings("finally")
	public Connection getConnection() {
		try {
			if (JNDI != null) {
				Context ctx = new InitialContext();
				Context envContext=(Context)ctx.lookup("java:comp/env");
				DataSource ds = (DataSource) envContext.lookup(JNDI);
				CONN = ds.getConnection();
				
//				InitialContext ctx = new InitialContext(); 
//				Context envContext = (Context) ctx.lookup("java:comp/env"); 
//				DataSource ds = (DataSource) envContext.lookup("jdbc/TestDB");
			} else {
				// System.out.println(JDBC);
				if (!JDBC.isEmpty()) {
					Class.forName((String) JDBC.get("driverClassName"));
					CONN = DriverManager.getConnection(
							(String) JDBC.get("url"),
							(String) JDBC.get("username"),
							(String) JDBC.get("password"));
				}
			}
		} catch (NamingException e) {
			e.printStackTrace();
			throw new DAOException(e);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException(e);
		} finally {
			return CONN;
		}
	}

	public void setConnection(Connection CONN) {
		this.CONN = CONN;
	}

	public String getJNDI() {
		return JNDI;
	}

	public void setJNDI(String jNDI) {
		JNDI = jNDI;
	}

	public Map<String, String> getJDBC() {
		return JDBC;
	}

	public void setJDBC(Map<String, String> JDBC) {
		this.JDBC = JDBC;
	}

	public void closeConnection() {
		try {
			CONN.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
