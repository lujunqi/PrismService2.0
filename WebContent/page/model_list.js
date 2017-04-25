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
			}else{
				msg("操作失败");
			}
		}, "json");
	}, function() {
	});
}
function info(v_id,v_area) {
	var area_t =[ '600px', '530px' ];
	if(v_area!=null){
		area_t = v_area;
	}
	layer.open({
		type : 2,
		area : area_t,
		fix : false, // 不固定
		maxmin : false,
		title : false,
		content :  dataUrl["win"],
		success : function(layero, index) {
			var body = layer.getChildFrame('body', index);
			var param = {id : v_id}
			$.post(dataUrl["info"], param, function(data) {
				if(data.length>0){
					var cmd = new prismTemplete();
					cmd.data("map",data[0]);
					cmd.preview($("form",body));
				}
				
			}, "json");
		}
	});
}
function upt(v_id,v_area) {
	var area_t =[ '600px', '530px' ];
	if(v_area!=null){
		area_t = v_area;
	}
	layer.open({
		type : 2,
		area : area_t,
		fix : false, // 不固定
		maxmin : false,
		title : false,
		btn : [ "提交", "关闭", "重置" ],
		content : dataUrl["win"],
		yes : function(index, layero) {
			var body = layer.getChildFrame('body', index);
			var param = $.formField("#baseFrm", $(body));
			param["id"] = v_id;
			$.post(dataUrl["upt"], param, function(data) {
				if (data["code"] == '1' && data["result"] > 0) {
					msg("操作成功");
					layer.close(index);
					init();
				}else{
					msg("操作失败");
					layer.close(index);
				}
			}, "json");
		},
		cancel : function(index) {

		},
		btn3 : function(index, layero) {
			var body = layer.getChildFrame('body', index);
			$.post(dataUrl["info"], {
				id : v_id
			}, function(data) {
				$("[name]", $(body)).each(function() {
					var name = $(this).attr("name");
					if (data[0][name] != undefined) {
						$(this).val(data[0][name]);
					}
				});
			}, "json");
		},
		success : function(layero, index) {
			var body = layer.getChildFrame('body', index);
			$.post(dataUrl["info"], {
				id : v_id
			}, function(data) {
				$("[name]", $(body)).each(function() {
					var name = $(this).attr("name");
					if (data[0][name] != undefined) {
						$(this).val(data[0][name]);
					}
				});
			}, "json");
		}
	})
}

function add(v_area) {
	var area_t =[ '600px', '530px' ];
	if(v_area!=null){
		area_t = v_area;
	}
	layer.open({
		type : 2,
		area : area_t,
		fix : false, // 不固定
		maxmin : false,
		title : false,
		btn : [ "新增", "关闭", "重置" ],
		content : dataUrl["win"],
		yes : function(index, layero) {
			var body = layer.getChildFrame('body', index);
			var param = $.formField("#baseFrm", $(body));
			$.post(dataUrl["add"], param, function(data) {
				if (data["code"] == '1' && data["result"] > 0) {
					msg("操作成功");
					layer.close(index);
					init();
				}else{
					msg("操作失败");
					layer.close(index);
				}
			}, "json");
		},
		cancel : function(index) {

		},
		btn3 : function(index, layero) {
			var form = layer.getChildFrame('form', index);
			form[0].reset();
		},
		success : function(layero, index) {
			var body = layer.getChildFrame('body', index);
		}
	})
}
function msg(info){
	layer.msg(info, {
		time : 1000,
		icon : 6
	});
}