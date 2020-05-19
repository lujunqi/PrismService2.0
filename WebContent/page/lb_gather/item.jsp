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

<link href="${BASE }/frame/layui/exts/city-picker/city-picker.css" rel="stylesheet" />
<script type="text/javascript">
	var BASE = "${BASE}";
	var OPT = "${param.opt}";
	var ID = "${param.id}";
</script>

<script type="text/javascript" src="${BASE}/script/common.js?"></script>
<script type="text/javascript" src="${BASE}/frame/layui/layui.js"></script>
<script src="${BASE }/frame/layui/exts/city-picker/city-picker.data.js"></script>
<script src="${BASE }/frame/layui/exts/city-picker/city-picker.js"></script>
<script type="text/javascript" src="item.js"></script>
<style type="text/css">
.layui-form-pane .layui-form-label {
	width: 150px;
}

.layui-form-pane .layui-input-block {
	margin-left: 150px;
}
</style>
</head>
<body class="body">

	<fieldset class="layui-elem-field layui-field-title"
		style="margin-top: 20px;">
		<legend id="text1">基本设置</legend>
	</fieldset>
	<div>
		<form class="layui-form" id="layui-form" name="form" lay-filter="layuiform"
			onsubmit="return false;" action="">
			<input name="gather_id" type="hidden" id="gather_id"> 
			<div class="layui-form-item">
				<label class="layui-form-label">姓名</label>
				<div class="layui-input-block">
					<input type="text" name="gather_name" data-exp='map.gather_name'
						data-method="TEXT" id="gather_name" p_verify="noNull"
						class="layui-input">

				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">单位</label>
				<div class="layui-input-block">
					<input type="text" name="gather_co" data-exp='map.gather_co'
						data-method="TEXT" id="gather_co" class="layui-input">

				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">手机</label>
				<div class="layui-input-block">
					<input type="text" name="gather_tel" 
						id="gather_tel" 
						lay-verify-msg="客户电话不能重复" 
						 required lay-verify="ctel"
						class="layui-input">

				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">所属区域</label>
				<div class="layui-input-block">
					<input name="gather_area" id="gather_area" type="text"
						class="city_input layui-input" data-exp='map.gather_area'
						data-method="TEXT" readonly="readonly">


				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">详细地址</label>
				<div class="layui-input-block">
					<input type="text" name="gather_address"
						data-exp='map.gather_address' data-method="TEXT"
						id="gather_address" class="layui-input">

				</div>
			</div>

			<div class="layui-form-item">
				<label class="layui-form-label">微信</label>
				<div class="layui-input-block">
					<input type="text" name="gather_weixin"
						data-exp='map.gather_weixin' data-method="TEXT" id="gather_weixin"
						class="layui-input">

				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">微信关注</label>
				<div class="layui-input-block">
					
						<input type="radio" name="gather_weixin_jion" checked value="N"
							title="未关注"> <input type="radio"
							name="gather_weixin_jion" value="Y" title="已关注">
					

				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">性质</label>
				<div class="layui-input-block">
					<div data-exp='map.gather_type' data-method="RADIO"
						p_verify="noNull" MSG="填写性质信息" >
						<input type="radio" name="gather_type" value="物业" title="物业">
						<input type="radio" name="gather_type" value="中介" title="中介">
						<input type="radio" name="gather_type" value="售楼" title="售楼">
						<input type="radio" name="gather_type" value="招商" title="招商">
						<input type="radio" name="gather_type" checked value="其他"
							title="其他">
					</div>

				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">等级</label>
				<div class="layui-input-block">
					<div data-exp='map.gather_level' data-method="RADIO"
						p_verify="noNull" MSG="填写等级信息" >
						<input type="radio" name="gather_level" checked value="星星"
							title="星星"> <input type="radio" name="gather_level"
							value="月亮" title="月亮"> <input type="radio"
							name="gather_level" value="太阳" title="太阳">
					</div>

				</div>
			</div>

			<div class="layui-form-item" id="btn65">
				<button class="layui-btn" lay-submit="" id="tijiao"
					lay-filter="demo2">提交</button>
				<button class="layui-btn" id="back">返回</button>
			</div>
		</form>	
	</div>
	
	 
</body>
</html>