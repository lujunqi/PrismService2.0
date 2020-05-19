var f = {};
f.ans = [];
f.idxEdit = 0;
f.init = function() {
	f.initFrm();
	f.initData();
	f.initEdit();
	$("#back").click(function() {
		var uuid = cou.get1("UUID");
		if (!cou.nvl(cou.get1(uuid))) {
			var MS_PARAM = cou.get1(uuid);

			if (MS_PARAM.HREF) {
				window.location.href = MS_PARAM.HREF;
			}
			//	
		}
	});
}
f.initEdit = function() {

	var layedit = layui.layedit;
	layedit.set({
		uploadImage : {
			url : BASE + '/up/file',
			type : 'post'
		}
	});

	tool = [ 'strong' // 加粗
	, 'italic' // 斜体
	, 'underline' // 下划线
	, 'del' // 删除线

	, '|' // 分割线

	, 'left' // 左对齐
	, 'center' // 居中对齐
	, 'right' // 右对齐
	, 'link' // 超链接
	, 'unlink' // 清除链接
	, 'face' // 表情
	, 'image' // 插入图片
	, 'help' // 帮助
	]

	// var index = layedit.build('demo',{tool:tool}); //建立编辑器
	f.idxEdit = layedit.build('msg_text'); // 建立编辑器
	
	$("#ans_add").click(function() {

		layer.open({
			type : 1,
			area : [ "550px", "330px" ],
			shade : .05,
			shadeClose : !0,
			moveType : 1,
			title : "添加调查问卷",
			skin : "layui-layer-msg",
			content : $("#ans_win").html(),
			success : function(l, n) {
				var form = layui.form;
				form.render();

				form.on("select(ans_type)", function(data) {
					var m = {
						"checkbox" : 1,
						"radio" : 1
					};
					if (m[data.value] == 1) {
						$("#def_item", l).show();
					} else {
						$("#def_item", l).hide();
					}
				});

				form.on('checkbox(verify)', function(data) {

					$("input[name='verify']").prop("checked", false);
					$(this).prop("checked", true);
					form.render('checkbox');
				});
				
				l.find(".layui-btn-primary").on("click", function() {
					layer.closeAll();
				});
				layui.form.on("submit(ans-code-yes)", function(e) {

					var v = [];
					$("input:checkbox[name='verify']:checked").each(function() {
						v.push($(this).val());
					});
					var item = e.field;

					item.ver = v;
					item.idx = f.ans.length;
					
					f.ans.push(item);
					f.initTbl();

					layer.closeAll();
				});
			}
		});
	});
};
f.initTbl = function() {
	var table = layui.table;
	
	table.render({
		elem : '#ans_tbl',
		cols : [ [ // 标题栏
		{
			title : '输入类型',
			width : 100,
			templet : function(data) {
				var m = {};
				m.text = "单行文本";
				m.textarea = "多行文本";
				m.checkbox = "多选框";
				m.radio = "单选框";
				return m[data.ans_type];
			}
		}, {
			field : 'cname',
			width : 150,
			title : '名称'
		}, {
			field : 'ver',
			title : '效验规则',
			templet : function(data) {
				var ver = data.ver;
				if (ver != null) {
					var m = {};
					m["required"] = "必填项";
					m["phone"] = "手机号";
					m["email"] = "邮箱";
					m["number"] = "数字";

					var ver2 = [];
					for (i in ver) {
						ver2.push(m[ver[i]]);

					}
					return ver2;
				} else {
					return "";
				}
			}
		}, {
			field : 'def',
			title : '默认选项（用于选择或复选）'
		}, {
			fixed : 'right',
			width : 85,
			align : 'center',
			toolbar : '#ans_bar'
		} ] ],
		data : f.ans
	});
	table.on('tool(ans_tbl)', function(obj) {
		var item = obj.data;
		if (obj.event === 'del') {
			layer.confirm("是否删除", {
				btn : [ '确定', '取消' ],
				title : "提示"
			}, function() {
				
				for(i in f.ans){
					var i2= f.ans[i];
					
					if(i2.idx == item.idx){
						f.ans.splice(i, 1); 

						break;
					}
				}
				
				f.initTbl();
				layer.closeAll();
			});

		}
	});
}
f.initData = function() {

	var laydate = layui.laydate;

	laydate.render({
		elem : '#be_date',
		range : true
	});

	f.initTree();
	$("#clr_slt_user").click(function() {
		$(".slt_user").trigger("click");
	});
};
f.initTree = function() {
	$("#all_slt_user").hide();
	var url = "/data/user.org";
	cou.post(url, {}, function(res) {
		var m = {};
		for (i in res.data) {
			var item = res.data[i];
			var tr = item.org_tr;
			var id = item.org_id;
			var title = item.org_name;
			var arr = tr.split("-");

			var sid = arr.slice(0, arr.length - 1).join("-");

			m[tr] = {
				id : id,
				title : title,
				tr : tr,
				spread : true
			};

			if (m[sid]) {
				if (m[sid].children == null) {
					m[sid].children = [];
				}
				m[sid].children.push(m[tr])
			}

		}

		var tree = layui.tree
		var tname = '#org_tree';

		tree.render({
			elem : tname,
			id : 'orgTree',
			data : [ m['1'] ],
			showCheckbox : true,
			oncheck : function(obj) {
				var checkedData = tree.getChecked('orgTree'); // 获取选中节点的数据
				var data = obj.data;
				var a = f.getOrgId(checkedData);
				var orgs = a.join(",");
				cou.post("/data/msg.user2org", {
					"orgs" : orgs
				}, function(res) {
					var html = "";
					for (i in res.data) {
						var item = res.data[i];
						if ($(".slt_user[data-id='" + item.user_id + "']", "#user_ids").length == 0
								|| $(".slt_user[data-id='" + item.user_id + "']:hidden", "#user_ids").length == 1) {
							html += '<span class="layui-badge layui-bg-green unslt_user" data-id="' + item.user_id + '"  style="margin:3px;padding: 2px;">' + item.user_name
									+ '</span>';
						} else {
							html += '<span class="layui-badge layui-bg-green unslt_user" data-id="' + item.user_id + '"  style="margin:3px;padding: 2px;display:none;">'
									+ item.user_name + '</span>';
						}

					}

					$("#org_user").html(html);
					if (html != "") {
						$("#all_slt_user").show();
					}
					$("#all_slt_user").click(function() {
						$(".unslt_user").trigger("click");
					});
					$(".unslt_user").click(function() {

						var id = $(this).attr("data-id");
						var nm = $(this).html();
						$(this).hide(400);
						var c = $('<a class="layui-badge layui-bg-green slt_user" data-id="' + id + '"  style="margin:3px;padding: 2px;">' + nm + '</a>');
						$(".slt_user[data-id='" + id + "']", "#user_ids").show();
						if ($(".slt_user[data-id='" + id + "']", "#user_ids").length == 0) {
							$("#user_ids").append(c);
						}
						f.slt_user();

					});
				});
			}

		});
	});
};
f.getOrgId = function(dt) {
	var a1 = [];
	for (i in dt) {
		var item = dt[i];
		a1.push(item.id)
		if (item.children) {
			var a2 = f.getOrgId(item.children);
			a1 = a1.concat(a2);

		}
	}
	return a1;
}
f.slt_user = function() {
	$(".slt_user").unbind("click");
	$(".slt_user").click(function() {
		$(this).hide();
		var id = $(this).attr("data-id");
		$(".unslt_user[data-id='" + id + "']").show();
	});
}
f.initFrm = function() {
	var form = layui.form;
	var uuid = cou.get1("UUID");
	if (!cou.nvl(cou.get1(uuid))) {
		var MS_PARAM = cou.get1(uuid);
		var data18 = {};
		$.extend(true, data18, MS_PARAM["DATA"]);
		
		//问卷回填
		var msg_ans = data18.msg_ans;
		if(msg_ans){
			f.ans = cou.toJson(msg_ans);	
		}
		
		f.initTbl();
		
		layui.form.val('frm1', data18);
		if (data18.user_ids) {
			var user_ids = data18.user_ids.split(",");
			var user_names = data18.user_names.split(",");
			var html = "";
			for (i in user_ids) {
				var id = user_ids[i];
				var nm = user_names[i];

				html += '<a href="javascript:void()" class="layui-badge layui-bg-green slt_user" data-id="' + id + '"  style="margin:3px;padding: 2px;">' + nm + '</a>';
			}
			$("#user_ids").html(html);

		}
		f.slt_user();
	}
	layui.form.render();

	form.on('submit(demo2)', function(data) {
		var param = data.field;
		var url = "/data/msg.add";
		var dts = param.be_date.split(" ");

		param.b_date = dts[0];
		param.e_date = dts[2];
		
		param.msg_desc = layui.layedit.getText(f.idxEdit);
		
		param.msg_text = layui.layedit.getContent(f.idxEdit);
		
		if(param.msg_text==""){
			cou.msg("主体内容不能为空");
			return ;
		}
		var user_ids = [];
		$(".slt_user").each(function(){
			user_ids.push($(this).attr("data-id"));
		});
		
		param.user_ids=user_ids.join(",");
		param.msg_ans = cou.toString(f.ans);
		
		if (!cou.nvl(cou.get1(uuid))) {
			
			var MS_PARAM = cou.get1(uuid);
			
			if(!cou.nvl(MS_PARAM["DATA"])){
				url = "/data/msg.upd";	
			}
			
		}
		
		layer.load(0, {
			time : 3 * 1000
		});
		cou.post(url, param, function(res) {
			if (res.code == "1") {
				cou.msg(res.msg)
			} else {
				$("#back").trigger("click");
			}
			layer.closeAll('loading');

		});
	});

};

