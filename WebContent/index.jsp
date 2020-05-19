<%@page import="com.prism.ding.Ding"%>
<%@ page language="java" import="java.util.*,java.net.*"
	pageEncoding="UTF-8"%>
<%
	String BASE = request.getContextPath();
	request.setAttribute("projectName", "Prism");
	request.setAttribute("CORPID", Ding.CORPID);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<title>${projectName}</title>
<link rel="stylesheet" href="frame/layui/css/layui.css">
<link rel="stylesheet" href="frame/static/css/style.css">
<script type="text/javascript" src="script/md5.js"></script>
<link rel="icon" href="frame/static/image/code.png">
<script type="text/javascript"
	src="http://g.alicdn.com/dingding/dingtalk-pc-api/2.3.1/index.js"></script>

<script type="text/javascript">
	
<%="var BASE=\"" + BASE + "\";"%>
	
</script>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">

</head>
<body>

	<!-- layout admin -->
	<div class="layui-layout layui-layout-admin">
		<!-- 添加skin-1类可手动修改主题为纯白，添加skin-2类可手动修改主题为蓝白 -->
		<!-- header -->
		<div class="layui-header my-header">
			<a href="index.jsp"> <!--<img class="my-header-logo" src="" alt="logo">-->
				<div class="my-header-logo">${projectName}</div>
			</a>
			<div class="my-header-btn">
				<button class="layui-btn layui-btn-small btn-nav">
					<i class="layui-icon">&#xe65f;</i>
				</button>
			</div>

			<!-- 顶部左侧添加选项卡监听 -->
			<ul class="layui-nav" lay-filter="side-top-left" id="side_top_left">

			</ul>

			<!-- 顶部右侧添加选项卡监听 -->
			<ul class="layui-nav my-header-user-nav" lay-filter="side-top-right">

			
				<li class="layui-nav-item"><a class="name" href="javascript:;"><img
						src="./frame/static/image/code.png" id="my_user_icon" alt="logo"> <span
						id="my_user_name">Admin</span> </a>
				</li>
			</ul>

		</div>
		<!-- side -->
		<div class="layui-side my-side">
			<div class="layui-side-scroll">
				<!-- 左侧主菜单添加选项卡监听 -->
				<ul class="layui-nav layui-nav-tree" lay-filter="side-main"
					id="side_main">



				</ul>

			</div>
		</div>
		<!-- body -->
		<div class="layui-body my-body">
			<div class="layui-tab layui-tab-card my-tab" lay-filter="card"
				lay-allowClose="true">
				<ul class="layui-tab-title">
					<li class="layui-this" lay-id="1"><span><i
							class="layui-icon">&#xe638;</i>欢迎页</span></li>
				</ul>
				<div class="layui-tab-content">
					<div class="layui-tab-item layui-show">
						<iframe id="iframe" src="page/welcome.html" frameborder="0"></iframe>
					</div>
				</div>
			</div>
		</div>
		<!-- footer -->
		<div class="layui-footer my-footer"></div>
	</div>



	<!-- 右键菜单 -->
	<div class="my-dblclick-box none">
		<table class="layui-tab dblclick-tab">
			<tr class="card-refresh">
				<td><i class="layui-icon">&#x1002;</i>刷新当前标签</td>
			</tr>
			<tr class="card-close">
				<td><i class="layui-icon">&#x1006;</i>关闭当前标签</td>
			</tr>
			<tr class="card-close-all">
				<td><i class="layui-icon">&#x1006;</i>关闭所有标签</td>
			</tr>
		</table>
	</div>

	<script type="text/javascript" src="frame/layui/layui.js"></script>
	<script type="text/javascript" src="script/common.js?x"></script>
	<script type="text/javascript" src="./frame/static/js/vip_comm.js?1x"></script>
	<script type="text/javascript">
		MS_PARAM = {};
		var $;
		layui.use([ 'layer', 'vip_nav' ], function() {
			// 操作对象
			layer = layui.layer, vipNav = layui.vip_nav, $ = layui.jquery;
			init();
		});
		
		function init() {
			DingTalkPC.runtime.permission.requestAuthCode({
				corpId : "${CORPID}",
				//corpId : "ding7e93978de9880fe135c2f4657eb6378f",
				onSuccess : function(result) {
					
					var code = result.code;
					
					$.post("./dingtalk",{"code":code},function(res){
						$("#my_user_name").text(res.name);
						if(my.avatar!=""){
							$("#my_user_icon").attr("src",my.avatar);	
						}
						
						vipNav.main('./json/1.json?', 'side-main',false, res.roles);
						cou.set("my_user",res);
					},"json");
				},
				onFail : function(err) {
					cosole.log(err)
				}
			});
			
			
			var my = {"errcode":0,"unionid":"MokIAxLUdrzLmBda7LiSWQAiEiE","openId":"MokIAxLUdrzLmBda7LiSWQAiEiE","roles":[{"id":280010323,"name":"经理","groupName":"岗位","type":0},{"id":280010308,"name":"负责人","groupName":"默认","type":103},{"id":280010309,"name":"主管","groupName":"默认","type":104},{"id":280010306,"name":"主管理员","groupName":"默认","type":101}],"remark":"","userid":"1637031228850937","isLeaderInDepts":"{58319756:true}","isBoss":true,"isSenior":false,"tel":"","department":[58319756],"workPlace":"","email":"","orderInDepts":"{58319756:180016955155526360}","mobile":"13875858628","errmsg":"ok","active":true,"avatar":"https://static-legacy.dingtalk.com/media/lADPBbCc1ZG0U17NAd7NAn4_638_478.jpg","isAdmin":true,"tags":{},"isHide":false,"jobnumber":"","name":"柳斌（总经理）","stateCode":"86","position":"总经理"}

			cou.set("my_user",my);
			$("#my_user_name").text(my.name);
			if(my.avatar!=""){
				$("#my_user_icon").attr("src",my.avatar);	
			}
			
  			vipNav.main('./json/1.json?x', 'side-main',false, my.roles);
  			
		}
		
	</script>

	
	
	
</body>
</html>