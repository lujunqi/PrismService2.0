<%@ page language="java" import="java.util.*,java.net.*"
	pageEncoding="UTF-8"%>
<%
	String BASE = request.getContextPath();
	request.setAttribute("BASE", BASE);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">


<link rel="stylesheet" href="${BASE}/frame/layui/css/layui.css">
<link rel="stylesheet" href="${BASE}/frame/static/css/style.css">
<link rel="icon" href="${BASE}/frame/static/image/code.png">
<script type="text/javascript" src="${BASE}/frame/layui/layui.js"></script>
<script type="text/javascript" src="${BASE}/script/common.js"></script>
<script type="text/javascript" src="${BASE}/script/md5.js"></script>
<script type="text/javascript" src="item.js"></script>
<script type="text/javascript">
	var BASE = "${BASE}";
	var $;
	layui.use([ 'form', "laydate", "upload", "tree", "layedit", "table" ], function() {
		$ = layui.jquery;

		f.init();

	});
</script>
<style type="text/css">
.slt_user {
	cursor: pointer;
}

.unslt_user {
	cursor: pointer;
}
</style>


</head>
<body class="body">

	<fieldset class="layui-elem-field layui-field-title"
		style="margin-top: 20px;">
		<legend id="text1">基本设置</legend>
	</fieldset>
	<div>
		<form class="layui-form layui-form-pane" name="form1"
			lay-filter="frm1" onsubmit="return false;" action="xx">
			<input type="hidden" name="msg_id">



			<div class="layui-form-item">
				<label class="layui-form-label">活动名称</label>
				<div class="layui-input-block">
					<input type="text" name="msg_title" lay-verify="required"
						autocomplete="off" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">活动时间</label>
				<div class="layui-input-block">
					<input type="text" name="be_date" id="be_date"
						lay-verify="required" autocomplete="off" class="layui-input">
				</div>

			</div>
			<div class="layui-form-item layui-form-text">
				<label class="layui-form-label">内容</label>
				<div class="layui-input-block">
					<textarea id="msg_text"  name="msg_text" style="display: none;"></textarea>
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


			<div class="layui-form-item">
				<label class="layui-form-label">参与人员</label>
				<div class="layui-input-block">
					<div class="layui-input-inline" id="user_ids"
						style="min-width: 500px; width: 80%;"></div>
					<div class="layui-input-inline"
						style="width: 70px; float: right; text-align: right;">

						<button id="clr_slt_user" type="button"
							class="layui-btn layui-btn-primary layui-btn-xs">清空</button>
					</div>
				</div>
			</div>
			<div id="sltUsers" style="margin-bottom: 25px;">
				<div id="org_tree" style="width: auto; float: left;"></div>
				<div
					style="width: auto; max-width: 70%; float: left; margin-top: 5px;">
					<div>
						<button id="all_slt_user" type="button"
							class="layui-btn layui-btn-primary layui-btn-xs">全选</button>

					</div>
					<div id="org_user"></div>
				</div>
				<div style="clear: both;"></div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">任务周期</label>
				<div class="layui-input-block">
					<input type="radio" name="cycle_type" value="0" title="单次" checked="">
					<input type="radio" name="cycle_type" value="1" title="按天提交">
					<input type="radio" name="cycle_type" value="2" title="按周提交">
					<input type="radio" name="cycle_type" value="3" title="按月提交">
					<input type="radio" name="cycle_type" value="4" title="按季提交">
					<input type="radio" name="cycle_type" value="5" title="按年提交"
						>
				</div>
			</div>


			<div class="layui-form-item" id="btn65">
				<button class="layui-btn" lay-submit="" id="tijiao"
					lay-filter="demo2">提交</button>
				<button class="layui-btn" id="back">返回</button>
			</div>
		</form>
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
					placeholder="名称必填..."
						class="layui-input" />
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