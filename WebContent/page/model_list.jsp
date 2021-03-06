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
<!-- 引入jquery -->
<script type="text/javascript" src="scripts/jquery.js"></script>
<script type="text/javascript" src="prism/jquery.prism.2.0.js"></script>
<script type="text/javascript" src="plug/layer/layer.js"></script>
<script type="text/javascript">
if( top.layer != undefined){
	layer = top.layer;
}
var param={};
var req={};
param["data"] = req;
var dataUrl = ${DATAURL};
$(init);
function init(){
	param["content"]=dataUrl["list"];
	$("#list").prism(param);
}
<%
String mapping = request.getAttribute("MAPPING")+"";
if(mapping.startsWith("/*")){
	out.println(mapping);
}
%>
</script>
<script type="text/javascript" src="page/model_list.js"></script>
<%
if(request.getAttribute("SCRIPT")!=null){
	out.println("<script type=\"text/javascript\" src=\""+request.getAttribute("SCRIPT")+"\"></script>");
}
%>
</head>

<body class="mainBody">
<div class="crumb mb10">
  <div class="crumb-l">&nbsp;</div>
  <div class="crumb-r">&nbsp;</div>
  <div class="crumb-m clearfix"> <a class="home"><b>&nbsp;</b>首页</a> <span class="label">${VIEWNAME}</span> </div>
</div>
<div class="wrapper comWrap">
  <div class="wrap-tit clearfix">
    <h3 class="wrap-tit-l"><span class="icon icon-list-alt mr5"></span>${VIEWNAME}</h3>
    <p class="wrap-tit-r"> 
      <!--
        <a class="fr" href="javascript:exportEmployeeInfo()"><i class="icon icon-share-alt mr5"></i>导出数据</a>
        <span class="fr mlr10">|</span>  
        -->
<%
if(request.getAttribute("BUTTON")!=null){
Map<String,Object> button = (Map<String,Object>)request.getAttribute("BUTTON");
for(Map.Entry<String,Object> en: button.entrySet()){%>
<a class="fr" href="javascript:<%=en.getValue() %>" ><i class="icon icon-plus mr5"></i><%=en.getKey() %></a> <span class="fr mlr10">|</span> 
<%}} %>
       </p>
  </div>
  <div class="wrap-inner">
    <div id="search_div" class="frmList clearfix">

    </div>
  </div>
  <div class="wrap-inner" style="">
    <table id="" class="comTabList" width="100%" border="0" cellspacing="0" cellpadding="0">
      <thead>
        <tr>
<%
if(request.getAttribute("COL")!=null){
	Map<String,Object> map_key = (Map<String,Object>)request.getAttribute("COL");
	for(Map.Entry<String,Object> en: map_key.entrySet()){
		out.print("<th>"+en.getKey()+"</th>" );
	}
}%>
        </tr>
      </thead>
      <tbody id="list" class="comTabList" prism="dataGrid">
        <tr class="">
<%
if(request.getAttribute("COL")!=null){
	Map<String,Object> map_val = (Map<String,Object>)request.getAttribute("COL");
	for(Map.Entry<String,Object> en: map_val.entrySet()){
		out.print("<td>"+en.getValue()+"</td>" );
	}
}%>

        </tr>
      </tbody>
    </table>
    <div>
      <div class="plr10 ptb5">
        <div id="pages" style="display:none;" class="pages tr clearfix"> <span class="batch fl mr10" style="display:none;"> </span> <span class="info fl"> <em>共有<b id="total_pages"></b>条数据，当前第<b id="curr_pages">1</b>页</em> </span> <span id="pagesList" class="list"> </span> </div>
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