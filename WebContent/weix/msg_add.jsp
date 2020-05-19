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
<title>新增</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />

<link rel="stylesheet" href="css/weui.css">
<link rel="stylesheet" href="${BASE}/frame/layui/css/layui.css">

<link rel="icon" href="${BASE}/frame/static/image/code.png">
<script type="text/javascript" src="${BASE}/script/common.js"></script>
<script type="text/javascript" src="${BASE}/frame/layui/layui.js"></script>
<script src="js/layer.js"></script>
<script type="text/javascript" src="js/msg_add.js"></script>
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
		<a href="index.jsp"
			style="margin: 4px; color: rgba(0, 0, 0, .5); font-size: 14px;">返回</a>
		<a id="weix_header_cut_user___"
			style="margin: 4px; color: rgba(0, 0, 0, .5); font-size: 14px;">切换</a>

	</div>
	<div style="height: 32px;"></div>

	<div class="main">
		<fieldset class="layui-elem-field layui-field-title"
			style="margin-top: 20px;">
			<legend id="text1">新增信息</legend>
		</fieldset>
		<form class="layui-form layui-form-pane" name="form1"
			lay-filter="frm1" onsubmit="return false;" action="xx">
			<div class="layui-form-item">
				<label class="layui-form-label">活动名称</label>
				<div class="layui-input-block">
					<input type="text" name="msg_title" lay-verify="required"
						autocomplete="off" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item layui-form-text">
				<label class="layui-form-label">内容</label>
				<div class="layui-input-block">
					<textarea id="msg_text" name="msg_text" style="display: none;"></textarea>
				</div>
			</div>

			<div class="layui-form-item layui-form-text">
				<label class="layui-form-label">问卷调查
					<button id="ans_add" style="float: right;" type="button"
						class="layui-btn layui-btn-primary layui-btn-xs">新增</button>
				</label>
				<div class="layui-input-block">
					<table id="ans_tbl" lay-filter="ans_tbl">
					</table>
				</div>
			</div>

			<div class="layui-form-item layui-form-text">
				<label class="layui-form-label">参与人员
					<button id="add_slt_user" type="button"
						class="layui-btn layui-btn-primary layui-btn-xs">新增</button>
					<button id="clr_slt_user" type="button"
						class="layui-btn layui-btn-primary layui-btn-xs">清空</button>
				</label>
				<div class="layui-input-block">
					<div class="layui-input-inline" id="user_ids"
						style="width: 100%; padding-left: 5px;"></div>
					<div class="layui-input-inline"
						style="width: 70px; float: right; text-align: right;"></div>
				</div>
			</div>
			<div class="layui-form-item" style="text-align: center;">
				<button class="layui-btn" lay-submit="" id="tijiao"
					lay-filter="demo2">提交</button>
				<a class="layui-btn" href="/">返回</a>
			</div>
		</form>
	</div>

	<div id="sltUsers" style="margin-bottom: 25px; display: none;">
		<div style="width: 99%; margin-top: 5px;">

			<div id="user_group"></div>
			<div style="text-align: right; display: none;" id="all_slt_div">
				<button id="all_slt_user" type="button" style=""
					class="layui-btn layui-btn-primary layui-btn-xs">全选</button>
			</div>
			<div id="my_group"></div>
		</div>



	</div>


	<div id="ans_win" style="display: none;">


		<ul class="layui-form layui-form-pane" style="margin: 15px;">
			<li class="layui-form-item"><label class="layui-form-label">输入类型</label>
				<div class="layui-input-block">
					<select name="ans_type" lay-filter="ans_type">
						<option value="text">单行文本</option>
						<option value="textarea">多行文本</option>
						<option value="checkbox">多选框</option>
						<option value="radio">单选框</option>
					</select>
				</div></li>
			<li class="layui-form-item "><label class="layui-form-label">名称</label>
				<div class="layui-input-block">
					<input name="cname" lay-verify="required" autofocus="true"
						placeholder="名称必填..." class="layui-input" />
				</div></li>
			<li class="layui-form-item "><label class="layui-form-label">验证类型</label>
				<div class="layui-input-block">
					<input type="checkbox" name="verify" value="required"
						lay-skin="primary" title="必填项"> <input type="checkbox"
						name="verify" value="phone" lay-skin="primary" title="手机号">
					<input type="checkbox" name="verify" value="email"
						lay-skin="primary" title="邮箱"> <input type="checkbox"
						name="verify" value="number" lay-skin="primary" title="数字">
				</div></li>
			<li class="layui-form-item" id="def_item" style="display: none;"><label
				class="layui-form-label">默认选项</label>
				<div class="layui-input-block">
					<input placeholder="多项选择用“空格”分隔" name="def" autofocus="true"
						class="layui-input" />
				</div></li>
			<li class="layui-form-item" style="text-align: center;">
				<button type="button" lay-submit lay-filter="ans-code-yes"
					class="layui-btn">确定</button>
				<button style="margin-left: 20px;" type="button"
					class="layui-btn layui-btn-primary">取消</button>
			</li>
		</ul>

	</div>
	<script type="text/html" id="ans_bar">
  <a class="layui-btn layui-btn-xs" lay-event="del">删除</a>
	</script>
</body>
</html>