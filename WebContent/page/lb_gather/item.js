var $;
layui.config({
	base : '' // 静态资源所在路径
}).extend({
	citypicker : '__STATIC__/layui/exts/city-picker/city-picker'// 模块路径
}).use([ 'form', 'jquery', 'citypicker' ], function() {

	$ = layui.jquery;
	f.init();

});

var f = {};
f.init = function() {
	f.initFrm();
	$("#back").click(function() {
		
		window.location.href = "index.jsp";
	});

}
f.ctel = function() {
	var checkResult = null;
	var val = $("#gather_tel").val();
	if (val.trim() == "") {
		return '号码不能为空';
	}
	var param = {
		"tel" : val,
		"g_id":$("#gather_id").val()
	};

	$.ajax({
		url : cou.URL("/data/gather.tel_only"),
		type : "GET",
		contentType : "application/json", // 必须有
		dataType : "json", // 表示返回值类型，不必须
		data : param,
		async : false,
		success : function(result) {

			if (result.data.length > 0) {
				checkResult = $("#gather_tel").attr("lay-verify-msg");
			}
		},
		error : function() {
		}

	});

	return checkResult;
}
f.initFrm = function() {
	var form = layui.form;

	layui.form.render();
	var cityPicker = layui.citypicker;
	var currentPicker = new cityPicker("#gather_area");

	if (OPT == "upd") {
		cou.post("/data/gather.one", {
			id : ID
		}, function(res) {

			form.val("layuiform", res.data);
			currentPicker.setValue(res.data.gather_area)
			layui.form.render();

		});
	}

	form.verify({
		ctel : function(value, item) {
			var res = f.ctel();
			return res;
		}
	});
	$("#gather_tel").blur(function() {
		var res = f.ctel();
		if (res != null) {
			layer.msg(res, {
				icon : 5,
				time : 1000,
				shift : 6
			});
		}

	});
	form.on('submit(demo2)', function(data) {
		var field = data.field;
		var url = "/data/gather.add";
		if (OPT == "upd") {
			url = "/data/gather.upd";
		}
		cou.post(url, field, function(res) {
			if (res.data > 0) {
				$("#back").trigger("click");
			} else {
				cou.msg(res.msg);
			}
		})
	});

}