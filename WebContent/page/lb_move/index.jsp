
<%@ page language="java" import="java.util.*,java.net.*"
	pageEncoding="UTF-8"%>
<%
	String BASE = request.getContextPath();
	request.setAttribute("BASE", BASE);
	Map<String,String> m = new HashMap<String,String>();
	m.put("move", "移交");
	m.put("liangf", "量房");
	request.setAttribute("TNAME", m.get(request.getParameter("ftype")));
	
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
	var FTYPE = "${param.ftype}";
	var OTHER_ID = "${param.oid}";
	var BACK = "${param.back}";
</script>
</head>
<body class="body">



	<fieldset class="layui-elem-field layui-field-title"
		style="margin-top: 20px;">
		<legend>${TNAME }</legend>
	</fieldset>

	<div class="demoTable">

		<div></div>
		<div style="margin: 5px;">
			<button class="layui-btn" id="add">${TNAME }</button>
			<button class="layui-btn" id="back">返回</button>
		</div>
	</div>

	<script type="text/html" id="barDemo">
<a class="layui-btn  layui-btn-xs" lay-role="c_move" lay-event="move">${TNAME }</a>
</script>
	<table id="demo" style="width: auto;" lay-filter="demo"></table>

</body>
</html>