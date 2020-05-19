var $;
layui.use([ 'form', 'laytpl' ], function() {
	$ = layui.jquery;
	wx.init();

});
var wx = {};

wx.myread = function() {
	cou.post("/data/weix.msg.visit.add", {
		msg_id : ID
	}, function(res) {
		if (res.data > 0) {
			cou.msg("操作成功",function(){
				cou.href("./")
			});
		}
	});

}


wx.init = function() {
	var url = "/data/weix.msg.one";
	var param = {
		id : ID
	};
	var laytpl = layui.laytpl;

	cou.post(url, param, function(res) {
		if (res.data.length > 0) {
			var d = res.data[0];
			d.ans = cou.toJson(d.msg_ans);
			d.isAns = cou.nvl(d.ans);
			d.files = cou.toJson(d.msg_files);
			d.isFiles = cou.nvl(d.files);
			layer.fn = function(ver) {
				if (ver.length > 0) {
					return ' lay-verify="' + ver.join("|") + '" ';
				} else {
					return "";
				}
			}
			layer.vr = function(ver) {
				if (ver.length > 0) {
					var m = {};
					m["required"] = "必填项";
					m["phone"] = "手机号";
					m["email"] = "邮箱";
					m["number"] = "数字";
					var s = [];
					for (i in ver) {
						s.push(m[ver[i]]);
					}
					return ' 要求:[' + s.join("|") + ']';
				} else {
					return "";
				}
			}

			layer.def = function(def) {
				if (!cou.nvl(def)) {
					return def.split(" ");
				} else {
					return [];
				}
			}

			laytpl($("#msg_one_tmp").html()).render(d, function(string) {
				$("#msg_one").html(string);
				layui.form.render();

				layui.form.on('submit(demo2)', function(data) {
					var param = data.field;

					for ( var k in param) {
						if ($("input[type='checkbox'][name='" + k + "']:checked").length > 0) {
							v = [];
							$("input[type='checkbox'][name='" + k + "']:checked").each(function() {
								v.push($(this).val());
							});
							param[k] = v;
						}
						d.ans[k].val = param[k];
					}

					var url = "/data/weix.msg.inst.add";
					var p = {};
					p.msg_id = ID;
					p.msg_result = cou.toString(d.ans);
					layer.load();
					cou.post(url, p, function(res) {

						if (res.data > 0) {
							layer.closeAll("loading");
							cou.msg("数据提交成功", function() {
								cou.href("./");
							});
						} else {
							cou.msg("数据提交失败");
						}
					});

				});

			});
			
			//初始化实例数据
			if(!d.isAns){
				wx.inst_history();
			}
		}

		
	});
}
wx.inst_history = function(){
	$("#inst_history").show();
	var laytpl = layui.laytpl;
	layer.msg_result = function(result){
		var j = cou.toJson(result)
		var html = "";
		for(var i=0;i<j.length;i++){
			var item = j[i];
			html+="<span><b>"+item["cname"]+"：</b>"+item["val"]+";</span>";
		}
		return html;
	};
	
	cou.post("/data/weix.msg.inst.list",{msg_id:ID},function(res){
		laytpl($("#msg_inst_tmp").html()).render(res, function(string) {
			$("#msg_inst").html(string);
			
			$("a","#msg_inst").click(function(){
				var id = $(this).attr("data-id");
				cou.post("/data/weix.msg.inst.del",{msg_inst_id:id},function(res){
					cou.msg("数据删除成功", function() {
						wx.inst_history();
					});
				});
			});
		});
	});
}
