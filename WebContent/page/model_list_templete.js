
 	
function del(v_id) {
	layer.confirm('是否删除该记录？', {
		btn : [ '确定', '取消' ]
	}, function() {
		$.post(dataUrl["del"], {
			id : v_id
		}, function(data) {
			if (data["code"] == '1') {
				msg("操作成功");
				init();
			} else {
				msg("操作失败");
			}
		}, "json");
	}, function() {
	});
}
function win(req) {
	var area_t = [ '800px', '630px' ];
	if (req["area"] != null) {
		area_t = req["area"];
	}

	layer.open({
		type : 2,
		area : area_t,
		fix : false, // 不固定
		maxmin : false,
		title : false,
		content : req["win"],
		success : function(layero, index) {
			var body = layer.getChildFrame('body', index);
			try{
				$(layero).find("iframe")[0].contentWindow.callBack(req);
			}catch(e){
				
			}
			
		}
	});
}
function info(req) {
	var area_t = [ '800px', '630px' ];
	if (req["area"] != null) {
		area_t = req["area"];
	}

	layer.open({
		type : 2,
		area : area_t,
		fix : false, // 不固定
		maxmin : false,
		title : false,
		content : req["win"],
		success : function(layero, index) {
			var body = layer.getChildFrame('body', index);
			layer["req"] = req;

			$.get(req["info"], req["param"], function(data) {
				if (data.length > 0) {
					var cmd = new prismTemplete();
					cmd.data("map", data[0]);
					cmd.preview($("form", body));
					$(layero).find("iframe")[0].contentWindow.callBack(req);
				}

			}, "json");
		}
	});
}
function upt(req) {
	var area_t = [ '800px', '630px' ];
	if (req["area"] != null) {
		area_t = req["area"];
	}
	layer.open({
		type : 2,
		area : area_t,
		fix : false, // 不固定
		maxmin : false,
		title : false,
		btn : [ "提交", "关闭" ],
		content : req["win"],
		yes : function(index, layero) {
			var body = layer.getChildFrame('body', index);

			var param = $("#layui-form", $(body)).serializeObject();
			$.extend(req["param"], param);
			
			if(!verify($(body),$(layero).find("iframe")[0].contentWindow.p_verify)){
				return;
			}
			$.post(req["upt"], req["param"], function(data) {
				if (data["code"] == '1' && data["result"] > 0) {
					msg("操作成功");
					layer.close(index);
					init();
				} else {
					msg("操作失败");
					layer.close(index);
				}
			}, "json");
		},
		cancel : function(index) {

		},

		success : function(layero, index) {
			var body = layer.getChildFrame('body', index);
			$.get(req["info"], req["param"], function(data) {
				if (data.length > 0) {
					var cmd = new prismTemplete();
					cmd.data("map", data[0]);
					
					
					cmd.preview($("form", $(body)));
					
					$(layero).find("iframe")[0].contentWindow.callBack(req);
				}
			}, "json");
		}
	})
}

function add(req) {
	var area_t = [ '800px', '630px' ];
	if (req["area"] != null) {
		area_t = req["area"];
	}
	layer.open({
		type : 2,
		area : area_t,
		fix : false, // 不固定
		maxmin : false,
		title : false,
		btn : [ "新增", "关闭" ],
		content : req["win"],
		yes : function(index, layero) {
			var body = layer.getChildFrame('body', index);
			var param = $("#layui-form", $(body)).serializeObject();

			if(!req["param"]){
				req["param"] = {}
			}
			$.extend(req["param"],param);
			if(!verify($(body),$(layero).find("iframe")[0].contentWindow.p_verify)){
				return;
			}
			$.post(req["add"], req["param"], function(data) {
				if (data["code"] == '1' && data["result"] > 0) {
					msg("操作成功");
					layer.close(index);
					init();
				} else {
					msg("操作失败");
					layer.close(index);
				}
			}, "json");
		},
		cancel : function(index) {

		},

		success : function(layero, index) {
			var body = layer.getChildFrame('body', index);
			try{
				$(layero).find("iframe")[0].contentWindow.callBack(req);
			}catch(e){
				console.log(e)
			}
		}
	})
}
function verify(body,$verify){
	var flag = true;
	
	$("[p_verify]",body).each(function(){
		var v = $(this).attr("p_verify");
		$verify[v]($(this));
		if($(this).attr("v_check")!="Y"){
			$(this).addClass("layui-form-danger");
			$(this).focus();
			msg($(this).attr("v_check"));
			flag = false;
		}else{
			$(this).removeClass("layui-form-danger");
		}
	});
	return flag;
}
function msg(info) {
	layer.msg(info, {
		time : 1000,
		icon : 6
	});
}
