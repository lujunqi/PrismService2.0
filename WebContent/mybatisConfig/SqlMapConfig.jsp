<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="java.net.URLEncoder"%>
<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
%>
<!DOCTYPE configuration
  PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
  "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
	<environments default="development">
		<environment id="development">
			<transactionManager type="JDBC" />

			<dataSource type="POOLED">
				<property name="driver" value="com.mysql.jdbc.Driver" />
				<property name="url"
					value="jdbc:mysql://127.0.0.1:3306/xiaom?useUnicode=true&amp;characterEncoding=UTF-8" />
				<property name="username" value="root" />
				<property name="password" value="lost" />
				<property name="poolMaximumActiveConnections" value="100" />
				<property name="poolMaximumIdleConnections" value="0" />
			</dataSource>

		</environment>

	</environments>
	<mappers>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	//System.out.println(request.getParameter("sql"));
	String sql = request.getParameter("sql");
	sql = java.net.URLEncoder.encode(sql,"utf-8");
	
%>
		<mapper url="<%=basePath %>mybatisConfig/sm.jsp?sql=<%=sql %>" />

	</mappers>
</configuration>
