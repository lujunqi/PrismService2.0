<%@page import="com.prism.ding.Ding"%>
<%@ page language="java" import="java.util.*,java.net.*"
	pageEncoding="UTF-8"%>
<%
	String BASE = request.getContextPath();
	request.setAttribute("projectName", "Prism");
	request.setAttribute("BASE", BASE);
	request.setAttribute("CORPID", Ding.CORPID);
	request.setAttribute("v", new Date().getTime());
	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<title>${projectName}</title>
<link rel="stylesheet" href="${BASE}/frame/layui/css/layui.css">
<link rel="stylesheet" href="${BASE}/frame/static/css/style.css">
<script type="text/javascript" src="${BASE}/script/md5.js"></script>
<link rel="icon" href="${BASE}/frame/static/image/code.png">
<script
	src="https://g.alicdn.com/dingding/dingtalk-jsapi/2.10.3/dingtalk.open.js"></script>

<script type="text/javascript" src="${BASE}/frame/layui/layui.js"></script>
<script type="text/javascript">
	var CORPID = "${CORPID}";
	var BASE = "${BASE}";
</script>
<script type="text/javascript" src="${BASE}/script/common.js?x"></script>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<script type="text/javascript" src="index.js?${v }">
	
</script>
</head>
<body>
	<div
		style="background: #393D49; width: 100%; height: 60px; line-height: 60px;">


		<button class="layui-btn layui-btn-small btn-nav" id="menu"
			style="margin-left: 10px">
			<i class="layui-icon">&#xe65f;</i>
		</button>


		<ul class="layui-nav my-header-user-nav">
			<li class="layui-nav-item"><a class="name" href="javascript:;">
					<span id="my_user_name"></span>
			</a></li>
		</ul>
	</div>
 	
	<ul class="layui-nav layui-nav-tree layui-inline" 
		style="margin-right: 10px;" id="menu_div">
		
	</ul>
	<iframe id="ifrm"  style="width:100vw;height:85vh;" name="ifrm"></iframe>

</body>
</html>