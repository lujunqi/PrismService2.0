<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	isELIgnored="false"%>

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
<script type="text/javascript" src="plug/layer/layer.js"></script>
<script type="text/javascript" src="plug/layui/layui.js" charset="utf-8"></script>

<script type="text/javascript">
	if (top.layer != undefined) {
		layer = top.layer;
	}
	var param = {};
	param["_page"]=0;
	param["_offset"]=20;
	
	var dataUrl = "${DATAURL}";
	$(init);
	function init() {
		param["_page"]=0;
		slt();
		total();
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
	layui.use([ 'laypage', 'layer' ], function() {
		var laypage = layui.laypage, layer = layui.layer;
		
<%if(request.getAttribute("TOTAL")!=null){%>
var total_url = "${TOTAL}";
$.get(total_url,param,function(data){
	var $total =  data[0]["total"];
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
			<div id="search_div" class="frmList clearfix"></div>
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
				<div class="plr10 ptb5" id="pages"></div>
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