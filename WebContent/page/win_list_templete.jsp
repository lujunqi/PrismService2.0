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
%>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="css/global-min.css" />
<link rel="stylesheet" type="text/css" href="css/common.css" />
<link rel="stylesheet" href="plug/layui/css/layui.css" media="all">
<!-- 引入jquery -->
<script type="text/javascript" src="scripts/jquery.js"></script>
<script type="text/javascript" src="prism/prismTemplete.js?ZZ"></script>
<script type="text/javascript" src="plug/layer/layer.js"></script>
<script type="text/javascript"  src="plug/layui/layui.js" charset="utf-8"></script>
 
<script type="text/javascript">
	if (top.layer != undefined) {
		layer = top.layer;
	}
var $req = {};
function callBack(req){
	$req = req;
	$req["param"]["_page"]=0;
	$req["param"]["_offset"]=15;
	slt();
	
	total();
	
};
function total(){
	layui.use([ 'laypage', 'layer' ], function() {
		var laypage = layui.laypage, layer = layui.layer;
		
<%if(request.getAttribute("TOTAL")!=null){%>
var total_url = "${TOTAL}";
$("#page_total").hide();
$.get(total_url,$req["param"],function(data){
	var $total =  data[0]["total"];
	$("#page_total").show();
	$("span",$("#page_total")).html("共"+$total+"条记录");
	laypage({
		cont : 'pages',
		pages : Math.ceil($total/$req["param"]["_offset"]),
		groups : 5,
		first : false,
		last : false,
		jump : function(obj, first) {
			if (!first) {
				$req["param"]["_page"]= (obj.curr-1)*$req["param"]["_offset"];
				slt();
			}
		}
	});	
},"json");

<%}%>

	});
}
function slt(){
	var req = $req;
	$("#list").hide();
	$.get($req["info"],$req["param"],function(res){
		var cmd = new prismTemplete();
		
${CALLBACK}
		
		if(res.length>0){
			$("#list").show();
			cmd.data("d", res);
			cmd.preview($("#list"));
		}
	},"json");
}

</script>

</head>

<body class="mainBody">

	<div class="wrapper comWrap">

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
				<caption>
				${CAPTION}
				</caption>
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