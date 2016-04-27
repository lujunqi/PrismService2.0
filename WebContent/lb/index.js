function opt1(data) {
	var html = "";

	html += '<span class="operate"> <a href="javascript:del('
			+ data["user_id"] + ')">[删除]</a> </span>';
	html += '<span class="operate"> <a href="javascript:upt('
			+ data["user_id"] + ')">[修改]</a> </span>';
	html += '<span class="operate"> <a href="javascript:auth(\''
			+ data["user_acc"] + '\')">[赋权]</a> </span>';
	return html;
}
function auth(user_acc){
	var winParam = {};
	winParam["user_acc"] = user_acc;
	var area_t =[ '600px', '530px' ];
	layer["winParam"]=winParam;
	layer.open({
		type : 2,
		area : area_t,
		fix : false, // 不固定
		maxmin : false,
		title : false,
		content : "pa/lb_user_auth.v"
	});
}
//******************************//
function opt2(data){
	var html = '';
	if(data["user_m_acc"]!=null){
		html = '<a href="javascript:del_user_auth(\''+data["user_acc"]+'\');">取消</a>';
	}else{
		html = '<a href="javascript:add_user_auth(\''+data["user_acc"]+'\');">授权</a>';
	}
	return html;
}
function add_user_auth(user_s_acc){
	var req = getWinParam();
	req["user_s_acc"] = user_s_acc;
	$.post("pa/lb_user_auth_add.u",req,function(data){
		init();
	});
}
function del_user_auth(user_s_acc){
	var req = getWinParam();
	var req = getWinParam();
	req["user_s_acc"] = user_s_acc;
	$.post("pa/lb_user_auth_del.u",req,function(data){
		init();
	});
}
