<%@ page language="java" contentType="text/html; charset=GB2312"
    pageEncoding="GB2312"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.sql.*" %>
<%@ page import="javax.sql.*" %>
<%@ page import="javax.naming.*" %>

<html>
<head>

<title>ʹ��tomcat�Դ������ݿ����ӳ�</title>
</head>
<body>
 <%
 try{
  Connection conn;
  Statement stmt;
  ResultSet rs;
  //������Դ�л�����ݿ�����
  Context ctx = new InitialContext();
  DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/BookDB");
  conn = ds.getConnection();
  
  //����һ��SQL����
  stmt = conn.createStatement();
  //��ѯ��¼
  rs = stmt.executeQuery("select * from lb_cust_info");
  //�����ѯ���
  out.println("<table border=1 width=400>");
  while (rs.next()){
   String col1 = rs.getString(1);
   String col2 = rs.getString(2);
   String col3 = rs.getString(3);
   String col4 = rs.getString(4);
   
   //ת���ַ�����
   //col1 = new String(col1.getBytes("ISO-8859-1"),"GB2312");
   //col2 = new String(col2.getBytes("ISO-8859-1"),"GB2312");
   //col3 = new String(col3.getBytes("ISO-8859-1"),"GB2312");
   
   //��ӡ��ʾ������
   out.println("<tr><td>"+col1+"</td><td>"+col2+"</td><td>"+col3+"</td><td>"+col4+"</td></tr>");
  }
  out.println("</table>");
  
  //�رս������SQL���������ݿ�����
  rs.close();
  stmt.close();
  conn.close();
 }catch(Exception e){
  out.println(e.getMessage());
  e.printStackTrace();
 }
 %>
</body>
</html>