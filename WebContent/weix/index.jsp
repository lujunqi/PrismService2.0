<%@ page language="java" contentType="text/html; charset=UTF-8"
	import=" java.util.*" pageEncoding="UTF-8"%>
<%
	String BASE = request.getContextPath();
	request.setAttribute("BASE", BASE);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
<link rel="stylesheet" href="${BASE}/frame/layui/css/layui.css">

<link rel="icon" href="${BASE}/frame/static/image/code.png">
<link rel="stylesheet" href="css/weui.css">
<script type="text/javascript" src="${BASE}/frame/layui/layui.js"></script>
<script type="text/javascript" src="${BASE}/script/common.js"></script>
<script type="text/javascript">	
var BASE = "${BASE}";

</script>


<script type="text/javascript" src="js/index.js"></script>
</head>
<body>
<script id="wxlist_tmp" type="text/html">
  
  <ul>
  {{#  layui.each(d.data, function(index, item){ }}
    
<div class="weui-media-box weui-media-box_text">
	<h4 class="weui-media-box__title msg"  data-id='{{item.msg_id}}'>{{item.msg_title||''}}</h4>
	<p class="weui-media-box__desc msg"  data-id='{{item.msg_id}}'>{{item.msg_desc||''}}</p>
	<ul class="weui-media-box__info">
	 <li class="weui-media-box__info__meta">{{item.user_name||''}}</li>
	 <li class="weui-media-box__info__meta">{{item.c_date||''}}</li>
	 <li class="weui-media-box__info__meta weui-media-box__info__meta_extra">访问({{item.msg_visit_count||'0'}})   调查({{item.msg_inst_count||'0'}}) </li>
    </ul>
</div>

  {{#  }); }}
  {{#  if(d.data === 0){ }}
    无数据
  {{#  } }} 
  </ul>
</script>
	<div id="weix_header___"
		style="text-align: right; position: fixed; height: 30px;z-index:1000; line-height: 30px; background: #fff; width: 100%; border-bottom: 1px solid #e6e6e6;">
	
	<a id="weix_header_cut_user___" style=" margin: 4px;color: rgba(0,0,0,.5);font-size: 14px;">切换</a>
	</div>
	<div style="height: 32px;"></div>
	
	<div class="weui-panel">
		<div class="weui-panel__hd">我的消息</div>
		<div class="weui-panel__bd" id="wxlist">
		</div>
	</div>
</body>
</html>