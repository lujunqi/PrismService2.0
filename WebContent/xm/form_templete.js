function verify(body, $verify) {
	var flag = true;
	$("[p_verify]", body).each(function() {
		var v = $(this).attr("p_verify");
		$verify[v]($(this));
		if ($(this).attr("v_check") != "Y") {
			$(this).addClass("layui-form-danger");
			$(this).focus();
			msg($(this).attr("v_check"));
			flag = false;
		} else {
			$(this).removeClass("layui-form-danger");
		}
	});
	return flag;
}
function msg(info, title) {
	if (!title) {
		title = "提示";
	}
	modal({
		"body" : info,
		"title" : title,
		"button" : [ {
			"name" : "关闭",
			"type" : "cancel"

		} ]
	});
}
function modal(p) {
	$("#myModalTitle", '#myModal').html(p["title"]);
	$("#myModalBody", '#myModal').html(p["body"]);
	$("#myModalFooter", "#myModal").html("");
	if (p["button"]) {
		for (var i = 0; i < p["button"].length; i++) {
			$b = p["button"][i];
			var btn = $("<button type='button' class='btn btn-default'></button>");
			btn.html($b.name);
			if ($b.func) {
				btn.data("func", $b.func);
				btn.click(function() {
					$(this).data("func")($("#myModal"), $(this));
				});
			}
			if ($b.type == "primary") {
				btn.addClass("btn-primary");
			}
			if ($b.type == "cancel") {
				btn.attr("data-dismiss", "modal");
			}
			$("#myModalFooter", "#myModal").append(btn);
		}
	}
	$('#myModal').modal({backdrop: 'static'});
}
$(function() {
	 $('#myModal').modal('hide');
	$("#xxx").click(function() {
		msg("tttt")
		// modal({
		// "title" : "提示",
		// "body" : "学习学习",
		// "button" : [ {
		// "name" : "按钮1",
		// "func" : function(m) {
		// m.modal('hide');
		// }
		// }, {
		// "name" : "关闭",
		// "type" : "cancel"
		//
		// }, {
		// "name" : "提交",
		// "type" : "primary"
		//
		// } ]
		// });
	});
});