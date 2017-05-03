<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	isELIgnored="false"%>
<!doctype html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title></title>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<base href="<%=basePath%>" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="css/global-min.css" />
<link rel="stylesheet" type="text/css" href="css/common.css" />
<!-- 引入jquery -->
<script type="text/javascript" src="scripts/jquery.js"></script>
<script type="text/javascript" src="prism/jquery.prism.2.0.js"></script>
<script type="text/javascript" src="plug/layer/layer.js"></script>
<script type="text/javascript">
var dataUrl = ${DATAURL};
var param={};

<%
String mapping = request.getAttribute("MAPPING")+"";
if(mapping.startsWith("/*")){
	out.println(mapping);
}
%>
$(init)
function init(){
	var req = getWinParam();

	param["param"] = req;
	param["content"]=dataUrl["list"];
	param["end"]=function(prism,data){
		var index = parent.layer.getFrameIndex(window.name); 
		parent.layer.iframeAuto(index);
	};
	$("#list").prism(param);
}
function getWinParam(){
	return parent.layer["winParam"];
}
</script>

</head>

<body class="mainBody">

<div class="wrapper comWrap">
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
    
  </div>
  
</div>
<!--/.wrapper-->

</body>
</html>