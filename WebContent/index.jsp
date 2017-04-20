<%@ page language="java" import="java.util.*,java.net.*"
	pageEncoding="UTF-8"%>

<!DOCTYPE HTML>
<html>
<head>
<title>后台管理系统</title>
<meta charset="utf-8">
<link rel="stylesheet" href="css/global-min.css">
<link rel="stylesheet" href="css/common.css">
<script type="text/javascript" src="scripts/jquery.js"></script>
<script type="text/javascript" src="scripts/common.js"></script>
<script type="text/javascript" src="plug/layer/layer.js"></script>
<script type="text/javascript">
<%
session.setAttribute("user_acc","ljq");
if(session.getAttribute("user_acc")==null){
}
%>
</script>
</head>

<body class="fluid">

	<div class="container">
		<div id="header">
			<div class="inner">
				<h1 id="site-name">后台管理系统</h1>
				<div id="site-logo" class="fl">
					<a href="#"><img class="pngfix" src="images/logo.png" alt=""
						width="579" height="80" /></a>
				</div>
				<div id="logout">
					<!--a class="pngfix" href="#">退出登录</a-->
				</div>
				<ul id="site-nav" class="clearfix">
					<li class="user"><span></span>欢迎您</li>

					<li class="timer"><span id="date"></span></li>
				</ul>
			</div>
		</div>
		<!--/#header-->

		<div class="section clearfix">

			<div class="content clearfix">
				<iframe class="mainFrame" id="main" name="main" src="welcome.html"
					frameborder="0" scrolling="yes" hidefocus></iframe>
			</div>
			<!--/.content-->

			<div class="sidebar" id="sidebar">
				<ul class="sideNav">
					<li class="major">
						<h2 class="subtit">
							<a class="" href="#" target="main"><span class="">业务管理</span></a>
						</h2>
						<ul class="sublist">
							<li><a target="main" href="pa/lb_cust_infos.v">资料录入</a></li>
						</ul>
					</li>

					<li class="major">
						<h2 class="subtit">
							<a class="" href="#"><span class="">系统管理</span></a>
						</h2>
						<ul class="sublist">
							<li><a target="main" href="pa/lb_user.v">用户管理</a></li>
						</ul>
						
					</li>
					
				</ul>
			</div>
			<!--/.sidebar -->

			<div class="addBar">
				<!--/.addBar -->
			</div>
		</div>
		<!--/.section-->

	</div>
	<!--/.container -->

	<div id="footer">
		<div class="container">
			<div id="copyright">
				<p class="tc">Copyright &copy; 2000-2013</p>
			</div>
		</div>
	</div>
	<!--/#footer-->

</body>
</html>