<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="java.util.*" pageEncoding="UTF-8"%>
<%
	String BASE = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
<title>404-对不起！您访问的页面不存在</title>

<style type="text/css">
.head404 {
	text-align: center;
	margin-top: 20px;
}

.txtbg404 {
	text-align: center;
	position: relative;
	margin: 10px auto 0 auto;
}

.txtbg404 .txtbox {
	width:60%;
	position:absolute;
	top: 30px;
	left: 60px;
	color: #eee;
	font-size: 13px;
}

.txtbg404 .txtbox p {
	margin: 5px 0;
	line-height: 18px;
}

.txtbg404 .txtbox .paddingbox {
	padding-top: 15px;
}

.txtbg404 .txtbox p a {
	color: #eee;
	text-decoration: none;
}

.txtbg404 .txtbox p a:hover {
	color: #FC9D1D;
	text-decoration: underline;
}
</style>

</head>



<body bgcolor="#494949">

	<div class="head404">
		<img alt="" src="<%=BASE%>/images/head404.png" style="width: 100%">
	</div>

	<div class="txtbg404">
		<img alt="" src="<%=BASE%>/images/txtbg404.png" style="width: 99%">
		<div class="txtbox">

			<p>对不起，您请求的页面不存在、或已被删除、或暂时不可用</p>

			



		</div>

	</div>

</body>

</html>
</html>
