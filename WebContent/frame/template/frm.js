var frm = {};
var $;
layui.use([ 'form', "laydate", "upload", "tree" ], function() {

	$ = layui.jquery;
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

	frm.initDate();
	frm.initFrm();
	frm.initTree();

});
frm.initTree = function() {

	$(".ctree").click(function() {
		var url = $(this).attr("data-tree");
		var name = $(this).attr("name");

		if (url != null) {
			cou.post(url, {}, function(res) {
				frm.data39(res,name);
				
			});
		}

	});
};
frm.data39=function(res,name){
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
			tr:tr,
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
	var tname = '#t_' + name;
	$(tname).show(500);
	tree.render({
		elem : tname,
		data : [ m['1'] ],
		click : function(obj) {
			var data = obj.data; // 获取当前点击的节点数据
			$(tname).hide(500);
			$("#"+name).val(data.id)
		}

	});
}
frm.initDate = function(ele) {
	if (ele != null) {
		var param = {
			elem : ele,
			range : "~"
		};
		var laydate = layui.laydate;
		laydate.render(param);
	} else {
		$("[data-type='mdate']").each(function() {
			var id = "#" + $(this).attr("id");
			var param = {
				elem : id,
				range : "~"
			};
			var laydate = layui.laydate;
			laydate.render(param);
		});
	}

};
frm.setmDate = function(item) {
	var p = {};
	if (!item) {
		return p;
	}
	$("[data-type='mdate']").each(function() {
		var keys = $(this).attr("data-field").split(",");
		var name = $(this).attr("name");
		var val = [];
		for (i in keys) {
			val.push(item[keys[i]])
		}
		p[name] = val.join(" ~ ")
	});
	return p;
}
frm.initFrm = function() {
	var form = layui.form;
	layui.form.render();
	var uuid = cou.get1("UUID");

	if (!cou.nvl(cou.get1(uuid))) {
		var MS_PARAM = cou.get1(uuid);

		var data18 = {};
		$.extend(true, data18, MS_PARAM["DATA"]);
		$.extend(true, data18, frm.setmDate(MS_PARAM["DATA"]));

		layui.form.val('frm1', data18);

	}

	form.on('submit(demo2)', function(data) {
		var param = data.field;
		$("[data-type='mdate']").each(function() {
			var keys = $(this).attr("data-field").split(",");
			var name = $(this).attr("name");
			var d = param[name].split("~");
			for (i in keys) {
				param[keys[i]] = d[i];
			}
		});
		var url = $("#frm1").attr("action");

		if (url == "") {
			url = "/data/" + $("#ACTION").val();
		}

		cou.post(url, param, function(res) {

			$("#back").trigger("click");
		});
	});
};