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

<link rel="stylesheet" href="<%=BASE%>/frame/layui/css/layui.css">
<link rel="stylesheet" href="<%=BASE%>/frame/static/css/style.css">
<link rel="icon" href="<%=BASE%>/frame/static/image/code.png">

<link href="${BASE }/frame/layui/exts/city-picker/city-picker.css" rel="stylesheet" />
<script type="text/javascript">
	var BASE = "${BASE}";
	var OPT = "${param.opt}";
	var BACK = "${param.back}";
	var ID = "${param.id}";
	var GID = "${param.gid}";
	
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
			<input name="cust_id" type="hidden" id="cust_id"> 
			<div class="layui-form-item">
				<label class="layui-form-label">客户名称</label>
				<div class="layui-input-block">
					<input type="text" name="cust_name" data-exp="map.cust_name"
						data-method="TEXT" id="cust_name" p_verify="noNull"
						class="layui-input">

				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">客户电话</label>
				<div class="layui-input-block">
					<input type="text" name="cust_tel" data-exp="map.cust_tel"
						 id="cust_tel" lay-verify-msg="客户电话不能重复" 
						 required lay-verify="ctel"
						class="layui-input">

				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">性别</label>
				<div class="layui-input-block">
					<div data-exp="map.cust_sex" data-method="RADIO" >
						<input type="radio" name="cust_sex" checked="" value="男" title="男">
						<input type="radio" name="cust_sex" value="女" title="女">
					</div>

				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">所属区域</label>
				<div class="layui-input-block">
					<input name="cust_area" id="cust_area" type="text"
						class="city_input layui-input" data-exp="map.cust_area"
						data-method="TEXT" readonly="readonly">


				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">详细地址</label>
				<div class="layui-input-block">
					<input type="text" name="cust_address" data-exp="map.cust_address"
						data-toggle="city-picker" placeholder="请选择" id="cust_address"
						class="layui-input">

				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">场地面积</label>
				<div class="layui-input-inline">
					<input type="text" name="cust_space" data-exp="map.cust_space"
						data-method="TEXT" id="cust_space" class="layui-input">

				</div>
				<div class="layui-form-mid layui-word-aux">平米</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">项目分类</label>
				<div class="layui-input-block">
					<select data-exp="map.cust_class_id" name="cust_class_id"
						id="cust_class_id" data-method="SELECT">
						<option value="7">其他</option>
						<option value="1">办公</option>
						<option value="2">门面</option>
						<option value="3">餐饮</option>
						<option value="4">娱乐</option>
						<option value="5">厂房</option>
						<option value="6">家居</option>
					</select>
					 

				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">项目性质</label>
				<div class="layui-input-block">
					<input type="text" name="cust_class_info"
						data-exp="map.cust_class_info" data-method="TEXT"
						id="cust_class_info" class="layui-input">

				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">信息员</label>
				<div class="layui-input-block">
					<select data-exp="map.gather_id" name="gather_id" id="gather_id"
						data-method="SELECT">
					</select>

				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">备注</label>
				<div class="layui-input-block">
					<textarea name="cust_memo" data-exp="map.cust_memo"
						data-method="TEXT" id="cust_memo" class="layui-textarea"></textarea>

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