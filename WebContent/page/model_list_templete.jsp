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
<!doctype html>
<html>
<head>
<title></title>
<%
	String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
long v = new Date().getTime();
%>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="css/global-min.css" />
<link rel="stylesheet" type="text/css" href="css/common.css" />
<link rel="stylesheet" href="plug/layui/css/layui.css" media="all">
<!-- 引入jquery -->
<script type="text/javascript" src="scripts/jquery.js"></script>
<script type="text/javascript" src="scripts/jquery_extend.js?<%=v%>"></script>

<script type="text/javascript" src="prism/prismTemplete.js?<%=v%>"></script>
<script type="text/javascript" src="plug/layui/layui.js"></script>

<script type="text/javascript">
var my_user_acc = "${sessionScope.user_acc}";
var my_user_name = "${sessionScope.user_name}";
var my_user_id = "${sessionScope.user_id}";
layer = top.layer;

	var param = {};
	param["_page"]=0;
	param["_offset"]=15;

	layui.use(['form'], function(){
		layer = top.layer;
		layui.form().render();
	});
	
	var dataUrl = "${DATAURL}";
	$(init);
	function init() {
		param = {};
		param["_page"]=0;
		param["_offset"]=15;
		slt();
		total();
		search();
	}
	function search(){
		$("#search_btn").click(function(){
			var param1 = $("#layui-form-search").serializeObject();
			param["_page"]=0;
			$.extend(param, param1);
			slt();
		});
	}
function slt(){
	$.get(dataUrl, param, function(res, textStatus, jqXHR) {

		var cmd = new prismTemplete();

		${CALLBACK}
		

		cmd.data("d", res);
		cmd.preview($("#list"));
	}, "json");
	
}
function total(){
	layui.use([ 'laypage' ], function() {
		var laypage = layui.laypage;
		
<%if(request.getAttribute("TOTAL")!=null){%>
var total_url = "${TOTAL}";
$("#page_total").hide();
$.get(total_url,param,function(data){
	
	var $total =  data[0]["total"];
	$("#page_total").show();
	$("span",$("#page_total")).html("共"+$total+"条记录");
	laypage({
		cont : 'pages',
		pages : Math.ceil($total/param["_offset"]),
		groups : 5,
		first : false,
		last : false,
		jump : function(obj, first) {
			if (!first) {
				param["_page"]= (obj.curr-1)*param["_offset"];
				slt();
			}
		}
	});	
},"json");

<%}%>

	});
}
</script>

<script type="text/javascript" src="page/model_list_templete.js?<%=v%>"></script>
</head>

<body class="mainBody">
	<div class="crumb mb10">
		<div class="crumb-l">&nbsp;</div>
		<div class="crumb-r">&nbsp;</div>
		<div class="crumb-m clearfix">
			<a class="home"><b>&nbsp;</b>首页</a> <span class="label">${VIEWNAME}</span>
		</div>
	</div>
	<div class="wrapper comWrap">
		<div class="wrap-tit clearfix">
			<h3 class="wrap-tit-l">
				<span class="icon icon-list-alt mr5"></span>${VIEWNAME}</h3>
			
			<p class="wrap-tit-r">

				<%
					if(request.getAttribute("BUTTON")!=null){
																Map<String,Object> button = (Map<String,Object>)request.getAttribute("BUTTON");
																for(Map.Entry<String,Object> en: button.entrySet()){
				%>
				<a class="fr" href="javascript:<%=en.getValue()%>"><i
					class="icon icon-plus mr5"></i><%=en.getKey()%></a> <span
					class="fr mlr10">|</span>
				<%
					}}
				%>
			</p>

			
		</div>
		<div class="wrap-inner">
			<div id="search_div" class="frmList clearfix">
<form class="layui-form" id="layui-form-search" style="padding:10px" name="form" onsubmit="return false;"
action="">	
<%
if(request.getAttribute("SEARCH")!=null){
	@SuppressWarnings("unchecked")
	Map<String,Object> map_val = (Map<String,Object>)request.getAttribute("SEARCH");
	for(Map.Entry<String,Object> en: map_val.entrySet()){
		@SuppressWarnings("unchecked")
		Map<String,Object> tmap = (Map<String,Object>)en.getValue();
		String div = String.format("<div class=\"layui-inline\" style='margin:3px;'>"+
				"	<label>%1$s：</label>"+
				"	<div class=\"layui-input-inline\">%2$s</div></div>",en.getKey(),vm.control(tmap));
	 	out.println(div);
	};
	out.println("<div class=\"layui-input-inline\"><button id=\"search_btn\" class=\"layui-btn\">查询</button></div>");
	
}
%>


</form>

			</div>
		</div>
		<div class="layui-form">
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
				<thead>
					<tr>
						<%
							if(request.getAttribute("COL")!=null){
																															Map<String,Object> map_key = (Map<String,Object>)request.getAttribute("COL");
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
							if(request.getAttribute("COL")!=null){
																															Map<String,Object> map_val = (Map<String,Object>)request.getAttribute("COL");
																															for(Map.Entry<String,Object> en: map_val.entrySet()){
																																out.print("<td data-exp='"+en.getValue()+"'></td>" );
																															}
																														}
						%>

					</tr>
				</tbody>

			</table>

			<div>
				<div class="plr10 ptb5" style="float:left;" id="pages"></div>
				<div class="plr10 ptb5" style="float:left;display:none;" id="page_total">
					 <div class="layui-box layui-laypage layui-laypage-default" ><span class="layui-laypage-curr">的点点</span> </div>
				</div>
				
			</div>
		</div>
		<div class="wrap-btm clearfix">
			<div class="wrap-btm-l">&nbsp;</div>
			<div class="wrap-btm-r">&nbsp;</div>
		</div>
	</div>
	<!--/.wrapper-->

</body>
</html>