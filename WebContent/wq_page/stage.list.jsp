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

<title>物料超市</title>
<link href="../plug/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<script src="../scripts/jquery.js" type="text/javascript"></script>
<script src="js/masonry.pkgd.min.js" type="text/javascript"></script>
<script src="js/imagesloaded.pkgd.min.js" type="text/javascript"></script>
<script src="../prism/prismTemplete.js" type="text/javascript"></script>
<script type="text/javascript" src="stage.list.js" ></script>
<script type="text/javascript">
var user_id = ${user_id };
</script>
</head>
<body>

	<div class="wrapper">
		<h3>物料超市</h3>
		<div id="con1_1" data-exp="data" data-method="list" data-map="map">
			
			<div class="grid">

				<div class="context">
					<img data-exp="map" data-method="attr"
						data-attr='{"src":"attr.stage_url"}'>
					<div data-exp="map.stage_name"></div>
					<div data-exp="map.stage_desc"></div>
	
					<p class="stage_price" data-exp="map.stage_price"></p>
				</div>
				<div class="footer"  data-exp="map" data-method="attr"
						data-attr='{"data-id":"attr.stage_id"}'>
				 <a class="favorites" data-exp="map" data-method="funcFav">收藏</a><a class="order" data-method="jData" data-exp="map">订购</a>
				</div>
			</div>

		</div>
	</div>
</body>
</html>