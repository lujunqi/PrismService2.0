<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	isELIgnored="false"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="com.prism.common.VMControl"%>

<%
ApplicationContext context = (ApplicationContext)request.getAttribute("context");
@SuppressWarnings("unchecked")
Map<String,String> from = (Map<String,String>)context.getBean("m_unit");
VMControl vm = new VMControl(request);
%>
<!DOCTYPE html>
<html>
<head>
<%
	String path = request.getContextPath();
	String basePath =  path + "/";
	
%>
<base href="<%=basePath%>">
<link rel="stylesheet" href="<%=basePath%>plug/layui/css/layui.css" media="all">
<script type="text/javascript" src="<%=basePath%>scripts/jquery.js"></script>
<script type="text/javascript" src="<%=basePath%>scripts/jquery_extend.js"></script>
<script type="text/javascript"  src="<%=basePath%>plug/layui/layui.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=basePath%>plug/cityselect/cityselect.js?"></script>
<link href="<%=basePath%>plug/cityselect/cityLayout.css?x" type="text/css"
	rel="stylesheet">
<script src="<%=basePath%>plug/huploadify/jquery.Huploadify.js"
	type="text/javascript"></script>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>plug/huploadify/Huploadify.css">
<script type="text/javascript">
layui.use(['form', 'layedit', 'laydate'], function(){
	init_city_select($(".city_input"));
});
function callBack(req){
	
	layui.use(['form', 'layedit', 'laydate'], function(){
		${CALLBACK}
		layui.form().render();
	});
	
}
</script>
<meta charset="utf-8">
</head>
<body>
	<div style="margin: 10px;">
		<fieldset class="layui-elem-field layui-field-title"
			style="margin-top: 20px;">
			<legend>${VIEWNAME}</legend>
		</fieldset>
		<form class="layui-form" id="layui-form" name="form" onsubmit="return false;" action="">
<%
if(request.getAttribute("COL")!=null){
	@SuppressWarnings("unchecked")
	Map<String,Object> map_val = (Map<String,Object>)request.getAttribute("COL");
	for(Map.Entry<String,Object> en: map_val.entrySet()){
		@SuppressWarnings("unchecked")
		Map<String,Object> tmap = (Map<String,Object>)en.getValue();
		if(tmap.containsKey("AUX")){
			String div = String.format(
			"<div class=\"layui-form-item\">"+
			"<label class=\"layui-form-label\">%1$s</label>"+
			"<div class=\"layui-input-inline\">%2$s</div>"+
			"<div class=\"layui-form-mid layui-word-aux\">%3$s</div>"+
			"</div>"
			,en.getKey(),vm.control(tmap),tmap.get("AUX"));
		 	out.println(div);
		}else{
			String div = String.format("<div class=\"layui-form-item\">"+
					"	<label class=\"layui-form-label\">%1$s</label>"+
					"	<div class=\"layui-input-block\">%2$s</div></div>",en.getKey(),vm.control(tmap));
		 	out.println(div);
		}
		
		
	}
}
%> 
		</form>
	</div>
</body>
</html>