
<%@ page language="java" import="java.util.*,java.net.*"
	pageEncoding="UTF-8"%>
<%
	String BASE = request.getContextPath();
	request.setAttribute("BASE", BASE);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<title>表格</title>
<link rel="stylesheet" href="${BASE}/frame/layui/css/layui.css">
<link rel="stylesheet" href="${BASE}/frame/static/css/style.css">
<link rel="icon" href="${BASE}/frame/static/image/code.png">

<script type="text/javascript" src="${BASE}/frame/layui/layui.js"></script>
<script type="text/javascript" src="${BASE}/script/common.js?"></script>
<script type="text/javascript" src="index.js"></script>
<style type="text/css">
.layui-laypage-limits select {
	display: none;
}
</style>
<script type="text/javascript">
	var BASE = "${BASE}";
	var FOLLOW_TYPE = "${param.ftype}";
	var OTHER_ID = "${param.oid}";
	var BACK = "${param.back}";
</script>
</head>
<body class="body">



	<fieldset class="layui-elem-field layui-field-title"
		style="margin-top: 20px;">
		<legend>跟进</legend>
	</fieldset>

	<div class="demoTable">

		<textarea placeholder="请输入内容" id="follow_info" class="layui-textarea"></textarea>
		<div style="margin: 5px;">
			<button class="layui-btn" id="add">新增</button>
			<button class="layui-btn" id="back">返回</button>
		</div>
	</div>
	<table id="demo" style="width: auto;" lay-filter="demo"></table>

</body>
</html>