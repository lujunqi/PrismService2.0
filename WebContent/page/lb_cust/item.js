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

		if (BACK != "") {
			window.location.href = BACK;
		} else {
			window.location.href = "index.jsp";
		}

	});

}
f.ctel = function() {
	var checkResult = null;
	var val = $("#cust_tel").val();
	if (val.trim() == "") {
		return '号码不能为空';
	}
	var param = {
		"tel" : val,
		"c_id":$("#cust_id").val()
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
				checkResult = $("#cust_tel").attr("lay-verify-msg");
			}
		},
		error : function() {
		}

	});
	return checkResult;
}
f.initFrm = function() {
	var form = layui.form;
	var res = cou.post2("/data/cust.gather",{});
	if(res.data){
		var html = "";
		
		for(i in res.data){
			var i60 = res.data[i];
			var selected = "";
				
			if(GID==i60["gather_id"]){
					
				selected = ' selected="selected" ';
			}
			html += '<option '+selected+' value="'+i60["gather_id"]+'">'+i60["gather_name"]+'</option>';
		}
		$("#gather_id").html(html);
	}
	
	
	layui.form.render();
	var cityPicker = layui.citypicker;
	var currentPicker = new cityPicker("#cust_area");

	if (OPT == "upd") {
		cou.post("/data/cust.one", {
			id : ID
		}, function(res) {
			
			form.val("layuiform", res.data);
			currentPicker.setValue(res.data.cust_area);
			layui.form.render();

		});
	}
	
	form.verify({
		ctel : function(value, item) { // value：表单的值、item：表单的DOM对象
			var res = f.ctel();
			return res;
		}
	});
	$("#cust_tel").blur(function() {
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
		var url = "/data/cust.add";
		if (OPT == "upd") {
			url = "/data/cust.upd";
		}
		cou.post(url, field, function(data) {
			$("#back").trigger("click");
		})
	});

}