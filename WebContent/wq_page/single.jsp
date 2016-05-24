<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=utf-8" pageEncoding="UTF-8"
	isELIgnored="false"%>
<%@page import="com.weixin.*"%>
<%
	/* 	String code = request.getParameter("code");
	 WeiXinResponse w = new WeiXinResponse(session);
	 w.getAccessToken("wx04dabb281487ea75",
	 "wx04dabb281487ea75:18138868310walwjk", code); */
	request.setAttribute("user_id", 1);
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
<link href="../plug/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<script src="../scripts/jquery.js" type="text/javascript"></script>
<script src="../prism/prismTemplete.js" type="text/javascript"></script>

</head>
<body>
	<div class="wrapper">
		<div class="banner"></div>
	</div>
</body>
</html>