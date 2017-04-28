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
	var area_t = [ '600px', '530px' ];
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
	var area_t = [ '600px', '530px' ];
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
	var area_t = [ '600px', '530px' ];
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

			var param = $("form", $(body)).serializeObject();
			$.extend(req["param"], param);
			layer["req"] = req;

			return ;
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
					cmd.TEXT = function(obj, val) {
						obj.val(val);
					};
					cmd.RADIO = function(obj, val) {
						obj.val(val);
					};
					
					cmd.preview($("form", $(body)));
				}
			}, "json");
		}
	})
}

function add(req) {
	var area_t = [ '600px', '530px' ];
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
			var param = $("form", $(body)).serializeObject();
			if(!req["param"]){
				req["param"] = {}
			}
			$.extend(req["param"],param);
			console.log(req["param"])
			console.log(param)
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
function required(){
	
}
function msg(info) {
	layer.msg(info, {
		time : 1000,
		icon : 6
	});
}
$.fn.serializeObject = function() {
	var obj = {};
	var count = 0;
	$.each(this.serializeArray(), function(i, o) {
		var n = o.name, v = o.value;
		count++;
		obj[n] = obj[n] === undefined ? v : $.isArray(obj[n]) ? obj[n].concat(v) : [ obj[n], v ];
	});
	// obj.nameCounts = count + "";//表单name个数
	// return JSON.stringify(obj);
	return obj;
};