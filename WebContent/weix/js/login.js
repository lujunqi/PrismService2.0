var $;

layui.use([ 'jquery', 'laytpl' ], function() {
	$ = layui.jquery;
	
	
	show(0);
	$("#showPicker").click(function() {
		var users = getItem("users");
		
		var laytpl = layui.laytpl;
		laytpl($("#pick_div").html()).render(users, function(string) {
			layer.open({
				id : "l",
				content : string,
				btn : [ '关闭' ],
				skin : 'footer',
				success : function(idx) {
					$(".pick_item", idx).click(function() {
						var id = $(this).attr("data-id");
						
						setItem("user",users[id]);
						window.location.href=URL;
						layer.closeAll();
					});
				},
				yes : function(index) {
					layer.closeAll();
				}
			});
		});

	});
	$("#back").click(function() {
		window.location.href=URL;
	});
	$("#tijiao").click(tijiao);
	$("#repwd").click(function(){
		$("#frm").hide(300);
		$("#repwd_frm").show(300);		
	});
	$("#re_back").click(function(){
		$("#frm").show(300);
		$("#repwd_frm").hide(300);		
	});
	$("#re_tijiao").click(re_tijiao);
});
function re_tijiao(){
	var param = {};
	param.user_acc = $("#re_user_acc").val();
	param.user_pwd = $("#re_user_pwd_old").val();
	param.user_pwd_new = $("#re_user_pwd_new").val();
	user_pwd_re = $("#re_user_pwd_re").val();
	if(param.user_acc=="" ){
		msg("请输入账号");
		return;
	}
	if(param.user_pwd=="" ){
		msg("请输入原密码");
		return;
	}
	if(param.user_pwd_new=="" ){
		msg("请输入新密码");
		return;
	}
	if(param.user_pwd_new!=user_pwd_re ){
		msg("输入密码不一致");
		return;
	}
	layer.open({type: 2});
	$.post("../data/weix.repwd", param, function(res) {
		layer.closeAll();
		if(res.data==0){
			msg("输入的账号密码错误，密码修改失败");
		}else{
			
			$("#re_back").trigger("click");
		}
	},"json");
	
}
function tijiao() {
	var param = {};
	param.user_acc = $("#user_acc").val();
	param.user_pwd = $("#user_pwd").val();
	param.nickname = $("#nickname").val();
	
	if(param.user_acc=="" ){
		msg("请输入账号");
		return;
	}
	if(param.user_pwd=="" ){
		msg("请输入密码");
		return;
	}
	param.openid = OPENID;
	layer.open({type: 2});
	$.post("../data/weix.login", param, function(res) {
		layer.closeAll();
		if (res.data.vcode !=null) {
			var my = res.data;
			setItem("user",my);
			var users = getItem("users");
			my["nickname"]=param.nickname;
			my["openid"]=param.openid;
			
			users[my.user_id] = my;
			setItem("users",users);
			//window.location.href=URL;
		} else {
			msg("登录的账号/密码错误!");
		}
	}, "json");
}
function getItem(key) {
	var obj = localStorage.getItem(key);
	if (obj == "") {
		return {};
	} else if (obj == null) {
		return {};
	} else {
		return JSON.parse(obj);
	}
}
function setItem(key,obj) {
	localStorage.setItem(key, JSON.stringify(obj));
}

function msg(info){
	layer.open({
	    type: 1,
	    content: '<div style="text-align:center;height: 80px;line-height: 80px">'+info+'</div>',
	    anim: 'up',
	    style: 'position:fixed; bottom:0; left:0; width: 100%; height: 80px; padding:10px 0; border:none;'
	  });
}
function show(v) {
	if (v == 1) {
		$("#frm").hide();
		$("#msg").show();
	} else {
		$("#frm").show();
		$("#msg").hide();
	}
}