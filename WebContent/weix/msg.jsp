<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="java.util.*" pageEncoding="UTF-8"%>
<%
	String BASE = request.getContextPath();
	request.setAttribute("BASE", BASE);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>我的问卷</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />

<link rel="stylesheet" href="css/weui.css">
<link rel="stylesheet" href="${BASE}/frame/layui/css/layui.css">

<link rel="icon" href="${BASE}/frame/static/image/code.png">
<script type="text/javascript" src="${BASE}/script/common.js"></script>
<script type="text/javascript" src="${BASE}/frame/layui/layui.js"></script>
<script src="js/layer.js"></script>
<script type="text/javascript" src="js/msg.js"></script>
<script type="text/javascript">
	var BASE = "${BASE}";
	var ID = "${param.id}";
</script>
<style type="text/css">
.title__right {
	text-align: right;
	margin-right: 10px;
}

.msg__text {
	font-size: 13px;
}
</style>
</head>
<body>
	<div id="weix_header___"
		style="text-align: right; position: fixed; height: 30px; z-index: 1000; line-height: 30px; background: #fff; width: 100%; border-bottom: 1px solid #e6e6e6;">
		<a id="add_msg" href="msg_add.jsp"
			style="margin: 4px; color: rgba(0, 0, 0, .5); font-size: 14px;">新增</a>
		<a href="index.jsp"
			style="margin: 4px; color: rgba(0, 0, 0, .5); font-size: 14px;">返回</a>
		<a id="weix_header_cut_user___"
			style="margin: 4px; color: rgba(0, 0, 0, .5); font-size: 14px;">切换</a>

	</div>
	<div style="height: 32px;"></div>
	<div class="page" style="display: none;" id="msg_inst_tmp">
		{{#  layui.each(d.data, function(index, item){ }}
		
		<a class="weui-cell weui-cell_access" data-id="{{item.msg_inst_id}}" href="javascript:;">
			<div class="weui-cell__bd">
				<p><b>创建时间：</b>{{item.c_date}}</p>
				<p>{{layer.msg_result(item.msg_result)}}</p>
				<p>{{item.c_date}}</p>
			</div>
			<div class="weui-cell__ft">删除</div>
		</a>
		{{#  }); }}
		{{#  if(d.data.length === 0){ }}
  		<div style="text-align: center;">无数据</div>
		{{#  } }} 
	</div>
	<div class="page" style="display: none;" id="msg_one_tmp">
		<div class="page__hd">
			<h1 class="page__title">{{d.msg_title}}</h1>
			<p class="page__desc title__right">{{d.c_date}}</p>
		</div>
		<div class="page__bd">
			<article class="weui-article">
				<section>
					<div>{{d.msg_text}}</div>
				</section>
				{{# if (!d.isFiles) { }}
				<section>
					<fieldset class="layui-elem-field layui-field-title"
						style="margin-top: 50px;">
						<legend>资料下载</legend>
					</fieldset>


					<div class="weui-cells" id="msg_files">
						<a class="weui-cell weui-cell_access" href="javascript:alert(1);">
							<div class="weui-cell__bd">cell standard</div>
							<div class="weui-cell__ft"></div>
						</a> <a class="weui-cell weui-cell_access" href="javascript:;">
							<div class="weui-cell__bd">xxxc</div>
							<div class="weui-cell__ft"></div>
						</a>
					</div>
				</section>

				{{#}}} {{# if (!d.isAns) { }}
				<section>
					<fieldset class="layui-elem-field layui-field-title"
						style="margin-top: 50px;">
						<legend>问卷调查</legend>
					</fieldset>

					<form class="layui-form layui-form-pane" onsubmit="return false"
						action="">

						{{# layui.each(d.ans, function(index, ans){ }} {{# if
						(ans.ans_type== "text") { }}
						<div class="layui-form-item layui-form-text">
							<label class="layui-form-label ">{{ans.cname}}</label>
							<div class="layui-input-block">
								<input type="text" name="{{index}}" autocomplete="off"
									placeholder="请输入{{ans.cname}}{{layer.vr(ans.ver)}}..."
									{{layer.fn(ans.ver)}}
									class="layui-input">
							</div>
						</div>
						{{#}}} {{# if (ans.ans_type== "radio") { }}
						<div class="layui-form-item layui-form-text">
							<label class="layui-form-label ">{{ans.cname}}</label>
							<div class="layui-input-block">
								{{# layui.each(layer.def(ans.def), function(index4, def){ }} <input
									type="radio" name="{{index}}" value="{{def}}" title="{{def}}">
								{{# }); }}

							</div>
						</div>
						{{#}}} {{# if (ans.ans_type== "checkbox") { }}
						<div class="layui-form-item layui-form-text">
							<label class="layui-form-label ">{{ans.cname}}</label>
							<div class="layui-input-block">
								{{# layui.each(layer.def(ans.def), function(index4, def){ }} <input
									type="checkbox" name="{{index}}" value="{{def}}"
									title="{{def}}" lay-skin="primary"> {{# }); }}

							</div>
						</div>
						{{#}}} {{# if (ans.ans_type== "textarea") { }}
						<div class="layui-form-item layui-form-text">
							<label class="layui-form-label ">{{ans.cname}}</label>
							<div class="layui-input-block">
								<textarea placeholder="请输入{{ans.cname}}..."
									{{layer.fn(ans.ver)}} name="{{index}}" class="layui-textarea"></textarea>
							</div>
						</div>
						{{#}}} {{# }); }}

						<div class="layui-form-item">
							<button class="weui-btn weui-btn_primary" lay-submit=""
								lay-filter="demo2">已阅读提交</button>
						</div>
					</form>
				</section>
				{{#}}} {{# if (d.isAns) { }}
				<button class="weui-btn weui-btn_primary" onclick="wx.myread()"
					id="my_read">已阅读</button>
				{{#}}}
			</article>
		</div>

	</div>
	<div class="main">
		<div class="page" id="msg_one"></div>
	</div>
	<section id="inst_history" style="display: none;">
		<fieldset class="layui-elem-field layui-field-title"
			style="margin-top: 50px;">
			<legend>历史记录</legend>
		</fieldset>
		<div class="weui-cells" id="msg_inst"></div>
	</section>


</body>
</html>