<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<base href="<%=basePath%>">
<link rel="stylesheet" href="plug/layui/css/layui.css" media="all">
<meta charset="utf-8">
<script type="text/javascript">
function callBack(req){
	
}
</script>
</head>
<body>
	<div style="margin: 10px;">
		<fieldset class="layui-elem-field layui-field-title"
			style="margin-top: 20px;">
			<legend>${VIEWNAME}</legend>
		</fieldset>
		<form class="layui-form" onsubmit="return false;" action="">
			<%
				if (request.getAttribute("COL") != null) {
							Map<String,Object> map_key = (Map<String,Object>)request.getAttribute("COL");
							for(Map.Entry<String,Object> en: map_key.entrySet()){
			%>
			<div class="layui-form-item">
				<label class="layui-form-label"><%=en.getKey()%></label>
				<div class="layui-input-block">
					<div
						style="display: block; padding: 9px 15px;color:#999; font-weight: 400;  "
						data-exp="<%=en.getValue()%>"></div>

				</div>
			</div>
			<%
				}
						}
			%>
		</form>
	</div>
</body>
</html>