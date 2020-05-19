<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.prism.weixin.*"%>
<%
	String BASE = request.getContextPath();
	request.setAttribute("BASE", BASE);
	WeiXin.openId(request);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />

<link rel="stylesheet" href="css/weui.css">
<link rel="stylesheet" href="${BASE}/frame/layui/css/layui.css">

<link rel="icon" href="${BASE}/frame/static/image/code.png">
<style type="text/css">
.pick_item {
	height: 35px;
	line-height: 35px;
	border-top: 1px solid #c2c2c2;
}
</style>
<script type="text/javascript" src="${BASE}/frame/layui/layui.js"></script>
<script src="js/layer.js"></script>
<script src="js/login.js"></script>
<!--这里是相对路径-->

<script type="text/javascript">
	var BASE = "${BASE}";
	var URL = "${param.url}";
	var OPENID = "${OPENID}";
	if (URL == "") {
		URL = "${param.state}";
	}
	if (OPENID == "") {
		//window.location.href = URL;
	}

	function isWeiXin() {
		var ua = window.navigator.userAgent.toLowerCase();
		if (ua.match(/MicroMessenger/i) == 'micromessenger') {
			return true;
		} else {
			return false;
		}
	}
</script>
</head>
<body>

	<div id="weix_header___" style="text-align: right;">
		<a id="repwd"
			style="margin: 4px; color: rgba(0, 0, 0, .5); font-size: 14px;">修改密码</a>
		<a id="back"
			style="margin: 4px; color: rgba(0, 0, 0, .5); font-size: 14px;">返回</a>

	</div>
	<div id="frm">
		<div style="padding: 40px; background: #ededed;">
			<h1 class="page__title">登录</h1>
			<p class="page__desc">请输入用户账号登录</p>
		</div>

		<div class="weui-cell">
			<div class="weui-cell__bd">
				<input class="weui-input" id="user_acc" type="text"
					autocomplete="false" placeholder="登录账号">
			</div>
		</div>

		<div class="weui-cell">
			<div class="weui-cell__bd">
				<input class="weui-input" id="user_pwd" autocomplete="false"
					type="password" placeholder="登录密码">
			</div>
		</div>
		<div class="weui-cell">
			<div class="weui-cell__bd">
				<input class="weui-input" id="nickname" type="text"
					autocomplete="false" placeholder="与账号的关系，如：XXX妈妈...">
			</div>
		</div>


		<div class="weui-cell weui-cell_access" id="showPicker">
			<div class="weui-cell__bd" style="color: #40AFFE;">切换用户</div>
			<div class="weui-cell__ft"></div>
		</div>


		<div class="weui-btn-area">
			<a class="weui-btn weui-btn_primary" href="javascript:" id="tijiao">确定</a>
		</div>
	</div>
	<div id="repwd_frm" style="display:none;">
		<div style="padding: 40px; background: #ededed;">
			<h1 class="page__title">修改密码</h1>
			<p class="page__desc">请输入用户账号登录</p>
		</div>

		<div class="weui-cell">
			<div class="weui-cell__bd">
				<input class="weui-input" id="re_user_acc" type="text"
					autocomplete="false" placeholder="登录账号">
			</div>
		</div>

		<div class="weui-cell">
			<div class="weui-cell__bd">
				<input class="weui-input" id="re_user_pwd_old" autocomplete="false"
					type="password" placeholder="输入原密码">
			</div>
		</div>
		<div class="weui-cell">
			<div class="weui-cell__bd">
				<input class="weui-input" id="re_user_pwd_new" autocomplete="false"
					type="password" placeholder="输入新密码">
			</div>
		</div>
		<div class="weui-cell">
			<div class="weui-cell__bd">
				<input class="weui-input" id="re_user_pwd_re" autocomplete="false"
					type="password" placeholder="请再次输入新密码">
					
			</div>
		</div>
		




		<div class="weui-btn-area" style="text-align: center;">
			<a class="weui-btn weui-btn_mini weui-btn_primary" href="javascript:" id="re_tijiao">确定</a>
			<a class="weui-btn weui-btn_mini weui-btn_primary" href="javascript:" id="re_back">返回</a>
		</div>
	</div>






	<div class="page msg_success" id="msg" style="display: none;">
		<div class="weui-msg">
			<div class="weui-msg__icon-area">
				<i class="weui-icon-warn weui-icon_msg"></i>
			</div>
			<div class="weui-msg__text-area">
				<h2 class="weui-msg__title">操作失败</h2>
				<p class="weui-msg__desc">请输入正确的账号</p>
			</div>
			<div class="weui-msg__opr-area">
				<p class="weui-btn-area">
					<a href="javascript:show(0);" class="weui-btn weui-btn_primary">返回</a>
				</p>
			</div>

		</div>
	</div>

	<script type="text/html" id="pick_div">
	 {{#  layui.each(d, function(index, item){ }}
		<div data-id="{{item.user_id}}" class="pick_item">{{item.user_name}}</div>
	 {{#  }); }} 
	{{#  if($.isEmptyObject(d)){ }}
    	无用户
  	{{#  } }} 
	</script>
</body>
</html>