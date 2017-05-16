<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	isELIgnored="false"%>

<!DOCTYPE HTML>
<html>
<head>
<title>后台管理系统</title>
<meta charset="utf-8">
<link rel="stylesheet" href="css/global-min.css">
<link rel="stylesheet" href="css/common.css">
<script type="text/javascript" src="scripts/jquery.js"></script>
<script type="text/javascript" src="scripts/common.js"></script>
<script type="text/javascript" src="scripts/jquery_extend.js"></script>
<script type="text/javascript" src="plug/layer/layer.js"></script>
<script type="text/javascript" src="plug/layui/layui.js"></script>

<script type="text/javascript">
	$(function() {

	$( "#my_user_name").click(function() {
			if ($("span",this).text() == "") {
				login();
			} else {
				logout();
			}
		});
	
	<%if (session.getAttribute("user_acc") != null) {
		out.println("$('#my_user_acc').val('"
				+ session.getAttribute("user_acc") + "');");
		out.println("$('#my_user_id').val('"
				+ session.getAttribute("user_id") + "');");
		out.println("$('span','#my_user_name').html('"
				+ session.getAttribute("user_name") + "')");
	} else {
		out.println("setTimeout(login,100);");
	}%>
	
	});
	
	//退出
	function logout() {
		layer.closeAll();
		layer.confirm('是否要退出？', {
			btn : [ '确定', '取消' ]
		}, function() {
			$.get("pa/lb_user_logout.s", {}, function(data) {
				if (data["status"] == "Y") {
					top.document.main.location.reload();
					layer.closeAll();
					$("span", "#my_user_name").html("");
					initMenu();
					login();
				}
			}, "JSON");
		}, function() {

		});
	}
	var isLogin = false;
	function login() {
		if (isLogin) {
			return;
		}
		var area_t = [ '400px', '230px' ];
		isLogin = true;
		layer.open({
			type : 2,
			id : "login_layer",
			area : area_t,
			fix : false, // 不固定
			maxmin : false,
			title : false,
			btn : [ "提交", "关闭" ],
			content : "pa/lb_user_login.v",
			yes : function(index, layero) {
				var body = layer.getChildFrame('body', index);

				var param = $("form", $(body)).serializeObject();
				if (param["user_acc"] == "" || param["user_pwd"] == "") {

					layer.alert("请输入账号和密码");
					return;
				}

				$.post("pa/lb_user_login.s", param, function(data, textStatus, jqXHR) {

					if (data.length > 0) {
						$("span", "#my_user_name").html(data[0]["user_name"]);
						
						isLogin = false;
						top.document.main.location.reload();
						initMenu();
						layer.closeAll();
					} else {
						layer.alert("请输入正确的账号和密码");
					}

				}, "json");

			},
			cancel : function(index, layero) {
				isLogin = false;
			}
		});

	}
</script>
</head>

<body class="fluid">
	<input id="my_user_id" type="hidden" />
	<input id="my_user_acc" type="hidden" />

	<div class="container">
		<div id="header">
			<div class="inner">
				<h1 id="site-name">后台管理系统</h1>
				<div id="site-logo" class="fl">
					<a href="#"><img class="pngfix" src="images/logo.png" alt=""
						width="579" height="80" /></a>
				</div>

				<ul id="site-nav" class="clearfix">
					<li class="user" id="my_user_name"><span></span>欢迎您</li>

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