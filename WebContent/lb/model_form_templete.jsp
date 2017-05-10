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
<link rel="stylesheet" href="plug/layui/css/layui.css" media="all">
<script type="text/javascript" src="scripts/jquery.js"></script>
<script type="text/javascript" src="scripts/jquery_extend.js"></script>
<script type="text/javascript" src="prism/prismTemplete.js?ZZ"></script>
<script type="text/javascript" src="plug/layui/layui.js" charset="utf-8"></script>
<script type="text/javascript" src="plug/cityselect/cityselect.js?"></script>
<link href="plug/cityselect/cityLayout.css?x" type="text/css"
	rel="stylesheet">
<script src="plug/huploadify/jquery.Huploadify.js"
	type="text/javascript"></script>
<link rel="stylesheet" type="text/css"
	href="plug/huploadify/Huploadify.css">

<script type="text/javascript">
	var my_user_acc = "${sessionScope.user_acc}";
	var my_user_name = "${sessionScope.user_name}";
	var my_user_id = "${sessionScope.user_id}";

	layui.use([ 'form', 'layedit', 'laydate'  ], function() {
		init_city_select($(".city_input"));
		$("#listdiv").hide();

		

	});
	//上传文件
	function upload($obj,$img,$input){
		$obj.Huploadify({
			auto:true,
			fileTypeExts:'*.jpg;*.png;*.gif',
			multi:false,
			fileSizeLimit:9999,
			showUploadedPercent:false,//是否实时显示上传的百分比，如20%
			showUploadedSize:true,
			removeTimeout:9999999,
			uploader:'<%=basePath%>upload',
			onUploadSuccess:function(file,res){
				var data = $.parseJSON(res);
				if(data.result=="Y"){
					$img.attr("src","<%=basePath%>upload/" + data.fileName);
					$input.val("upload/"+data.fileName);
					$(".delfilebtn").trigger("click");
				}
			}
		});
	}

	function callBack(req) {
		if(req["add"]){
			$("#listdiv").hide();
			$("[data-upload-key]").show();
		}else{
			$("[data-upload-key]").hide();
			$("#listdiv").show();
		}
		
		layui.use([ 'form', 'layedit', 'laydate' ], function() {
			${CALLBACK};
			slt(req);
			layui.form().render();
		});
	}
	function slt($req) {
		$("#list").hide();
		$.get($req["follow"], $req["param"], function(res) {
			var cmd = new prismTemplete();
			if (res.length > 0) {
				$("#list").show();
				cmd.data("d", res);
				cmd.preview($("#list"));
			}
		}, "json");
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
		<form class="layui-form" id="layui-form" name="form" onsubmit="return false;"
			action="">
			<%
				if(request.getAttribute("COL")!=null){
				@SuppressWarnings("unchecked")
				Map<String,Object> map_val = (Map<String,Object>)request.getAttribute("COL");
				for(Map.Entry<String,Object> en: map_val.entrySet()){
					String div = String.format("<div class=\"layui-form-item\">"+
					"	<label class=\"layui-form-label\">%1$s</label>"+
					"	<div class=\"layui-input-block\">%2$s</div></div>",en.getKey(),vm.control((Map<String,Object>)en.getValue()));
				 	out.println(div);
				
				}
			}
			%>
		</form>

		

		<div class="layui-form" id="listdiv">
			<table class="layui-table">
				<colgroup>
					<%
						if(request.getAttribute("colgroup")!=null){
									List<?> colgroup = (List<?>)request.getAttribute("colgroup");
									for(int i=0;i<colgroup.size();i++){
										out.print("<col width=\""+colgroup.get(i)+"\">");
									}
								}
					%>

					<col>
				</colgroup>
				<caption>
					<b>客户回访</b>
					<hr />
					${CAPTION}
				</caption>
				<thead>
					<tr>
						<%
							if(request.getAttribute("FOLLOW")!=null){
							Map<String,Object> map_key = (Map<String,Object>)request.getAttribute("FOLLOW");
							for(Map.Entry<String,Object> en: map_key.entrySet()){
								out.print("<th>"+en.getKey()+"</th>" );
							}
						}
						%>
					</tr>
				</thead>
				<tbody id="list" class="comTabList" data-exp="d" data-method="grid"
					data-map="map">
					<tr class="">
						<%
							if(request.getAttribute("FOLLOW")!=null){
							Map<String,Object> map_val = (Map<String,Object>)request.getAttribute("FOLLOW");
							for(Map.Entry<String,Object> en: map_val.entrySet()){
								out.print("<td data-exp='"+en.getValue()+"'></td>" );
							}
						}
						%>

					</tr>
				</tbody>
			</table>

			<div>
				<div class="plr10 ptb5" id="pages"></div>
			</div>
		</div>
	</div>
</body>
</html>