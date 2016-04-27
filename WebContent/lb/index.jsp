<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=utf-8" pageEncoding="UTF-8"
	isELIgnored="false"%>
<%@page import="com.weixin.*"%>
<%
	Map<String, String[]> map = request.getParameterMap();
for(Map.Entry<String,String[]> en :map.entrySet()){
	System.out.println(en.getKey()+"=="+en.getValue()[0]+"=="+en.getValue().length);
}

String code = request.getParameter("code");
// 业务
WeiXinResponse w = new WeiXinResponse(session);
String accessToken = w.getCoAccessToken(WeiXinEnum.sCorpID.getName(),
		WeiXinEnum.APPSECRET.getName());
String userId =	w.getCoUserId(accessToken, code, "5");
request.setAttribute("UserId", userId);
%>
<!DOCTYPE HTML>
<html>
<head>
<title>信息采集2</title>
<meta charset="utf-8">
<link rel="stylesheet" type="text/css"
	href="../bootstrap/css/bootstrap.min.css" />
<script src="../bootstrap/js/jquery-1.8.3.min.js"></script>
<script src="../bootstrap/js/bootstrap.min.js"></script>    

</head>
<body>


<div class="container">
  <h1 class="page-header"><span class="glyphicon glyphicon-user"> </span>信息采集</h1>
  <form class="form-horizontal">
    <div class="form-group">
      <div class="col-md-5">
        <input type="text" name="" id="" class="form-control"
						placeholder="用户名/email" />
      </div>
    </div>
    <div class="form-group">
      <div class="col-md-5">
        <input type="password" name="" id="" class="form-control"
						placeholder="密码" />
      </div>
    </div>
    <div class="form-group">
      <div class="col-md-5">
        <button class="btn btn-primary" id="ok">登录</button>
        <button class="btn btn-danger">登录</button>
      </div>
    </div>
  </form>
</div>
</body>
</html>