<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=utf-8" pageEncoding="UTF-8"
	isELIgnored="false"%>
<%@page import="com.weixin.*"%>
<%
String code = request.getParameter("code");
WeiXinResponse w = new WeiXinResponse(session);
w.getAccessToken("wx627f086f1403f471", "38c69c5e96c50f57bd4c93de86da1d9b", code);
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">

<title>çå¸æµ</title>
<link href="css/style.css" rel="stylesheet" media="all">
<link href="../plug/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<script src="../scripts/jquery.js" type="text/javascript"></script>
<script src="js/masonry.pkgd.min.js" type="text/javascript"></script>
<script src="js/imagesloaded.pkgd.min.js" type="text/javascript"></script>



<script type="text/javascript">
	$(function() {
		mas();
		$(window).on("orientationchange", function(event) {
			if (event.orientation) {
				var $container = $('#con1_1');
				$container.imagesLoaded(function() {
					$container.masonry();
				});
			}
		});
	});
	function mas() {
		var $container = $('#con1_1');

		for (var i = 1; i < 13; i++) {
			var grid = $('<div class="grid"><img src="img/'+i+'.jpg" ></div>');
			$container.append(grid);//.masonry();
		}
		$container.imagesLoaded(function() {
			$container.masonry();
		});
		for (var i = 1; i < 13; i++) {
			var grid = $('<div class="grid"><img src="img/'+i+'.jpg" ></div>');
			$container.append(grid);//.masonry();
		}
		$container.imagesLoaded(function() {
			$container.masonry();
		});
		
	}
</script>
</head>
<body>
	<div class="wrapper">
		<h3>çå¸æµå®ä¾</h3>
		<div id="con1_1"></div>
	</div>
</body>
</html>