<%@ page language="java" import="java.util.*,java.net.*"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<script type="text/javascript">
var BASE = "${BASE}";
</script>
<style type="text/css">
.layui-form-pane .layui-form-label {
	width: 150px;
}

.layui-form-pane .layui-input-block {
	margin-left: 150px;
}
</style>
</head>
<script type="text/javascript" src="${BASE}/frame/template/frm.js"></script>
<body class="body">
	<input type="hidden" id="BASE" value='${BASE }'>
	<input type="hidden" id="ACTION" value='${ACTION }'>
	<input type="hidden" id="MYURL" value='${MYURL }'>
	
	<fieldset class="layui-elem-field layui-field-title"
		style="margin-top: 20px;">
		<legend id="text1">${v.name }</legend>
	</fieldset>
	<div>
		<form class="layui-form layui-form-pane" name="form1" 
			lay-filter="frm1" id="frm1" onsubmit="return false;" action="${v.data_url }">
			
			<c:forEach items="${v.cols}" var="item" varStatus="id">
				<c:if test="${item.type=='hidden'}">
					<input type="${item.type}" name="${item.name}">
				</c:if>
				<c:if test="${item.type!='hidden'}">
					<div
						class="layui-form-item ${item.type=='textarea'?'layui-form-text':'' }">
						<label class="layui-form-label">${item.title}</label>
						<div class="layui-input-block ">
						<c:choose>
							<c:when test="${item.type=='text'}">
								<input type="${item.type}" name="${item.name}"
									lay-verify="${item.attr.verify}" autocomplete="off"
									placeholder="${item.attr.placeholder}" class="layui-input">
							</c:when>
							<c:when test="${item.type=='radio'}">
								<c:forEach items="${item.val}" var="it2" varStatus="id2">
									<input type="radio" name="${item.name}" value="${it2.k }"
										title="${it2.v }" ${it2.c}>
								</c:forEach>
							</c:when>
							<c:when test="${item.type=='textarea'}">
								<textarea placeholder="${item.attr.placeholder}" lay-verify="${item.attr.verify}"
									name="${item.name}" class="layui-textarea"></textarea>
							</c:when>
							<c:when test="${item.type=='mdate'}">
								<input type="text" data-type='mdate' class="layui-input" lay-verify="${item.attr.verify}"
									data-field="${item.field }" name="${item.name}"
									readonly="readonly" id="${item.name}" placeholder=" ~ ">
							</c:when>
							<c:when test="${item.type=='tree'}">
								<input type="text" data-type='tree' class="layui-input ctree" lay-verify="${item.attr.verify}"
									data-field="${item.field }" name="${item.name}"
									readonly="readonly" id="${item.name}" data-tree="${item.tree }">
									<div id="t_${item.name}"></div>
							</c:when>
							
							<c:when test="${item.type=='select'}">
								<select name="${item.name}" lay-filter="${item.name}" lay-verify="${item.attr.verify}">
									<c:forEach items="${item.val}" var="it2" varStatus="id2">
										<option value="${it2.k }" ${it2.c }>${it2.v }</option>
									</c:forEach>
								</select>
							</c:when>
							
							</c:choose>
						</div>
					</div>
				</c:if>
			</c:forEach>
<div id="tree"></div>


			<div class="layui-form-item" id="btn65">
				<button class="layui-btn" lay-submit="" id="tijiao"
					lay-filter="demo2">提交</button>
				<button class="layui-btn" id="back">返回</button>
			</div>
		</form>
	</div>
</body>
</html>