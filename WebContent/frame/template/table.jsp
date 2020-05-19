
<%@ page language="java" import="java.util.*,java.net.*"
	isELIgnored="false" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="my" uri="http://www.itnba.com" %> 

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">

<link rel="stylesheet" href="${BASE}/frame/layui/css/layui.css">
<link rel="stylesheet" href="${BASE}/frame/static/css/style.css">
<link rel="icon" href="${BASE}/frame/static/image/code.png">

<script type="text/javascript" src="${BASE}/frame/layui/layui.js"></script>
<script type="text/javascript" src="${BASE}/script/common.js"></script>
<script type="text/javascript" src="${BASE}/script/md5.js"></script>
<script type="text/javascript" src="${BASE}/frame/template/table.js"></script>
<style type="text/css">
.layui-laypage-limits select {
	display: none;
}
</style>
<script type="text/javascript">
	var BASE = "${BASE}";
	var header = ${v.header};
	var $;
	layui.use([ 'table', 'laytpl' ], function() {
		
		$ = layui.jquery;
		tbl.cols = ${v.cols};
		tbl.cols.push({
			fixed : 'right',
			align : 'center',
			toolbar : '#barDemo'
		});
		tbl.setHeader(header);
		tbl.where = function(){
						var t = ${v.where};
						return t
					};
		tbl.event = function(){ 
						var t = ${v.event};
						return t
					};
		
		tbl.barDemo();
		
		tbl.set_table();
		

	});
</script>


</head>
<body class="body">
	
	
	
	<input type="hidden" id="BASE" value='${BASE }'>
	<input type="hidden" id="ACTION" value='${ACTION }'>
	<input type="hidden" id="MYURL" value='${MYURL }'>
	<fieldset class="layui-elem-field layui-field-title"
		style="margin-top: 20px;">
		<legend>${v.name }</legend>
	</fieldset>

	<script type="text/html" id="barDemo">

  
</script>
	<div class="demoTable" id="content_header">
	
	
	
	</div>
	<table id="demo" lay-filter="demo" data-url='${v.url}'></table>

</body>
</html>