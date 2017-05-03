$.ajaxSetup({
	headers : {
		"POST_TYPE" : "JQUERY"
	},
	complete : function(xhr, status) {
		if (xhr.status == 250) {
			top.login();
		}
	}

});
$.fn.serializeObject = function() {
	var obj = {};
	var count = 0;
	$.each(this.serializeArray(), function(i, o) {
		var n = o.name, v = o.value;
		count++;
		obj[n] = obj[n] === undefined ? v : $.isArray(obj[n]) ? obj[n].concat(v) : [ obj[n], v ];
	});
	return obj;
};
// 表单验证
p_verify = {};
p_verify.frm = "form";
p_verify.val = function(o){
	return document[p_verify.frm][o.attr("name")].value;
}
p_verify["noNull"] = function(o) {

	var v = p_verify.val(o);
	if (v == "") {
		p_verify.msg(o, "必填项不能为空")
	} else {
		o.attr("v_check", "Y");
	}
	return p_verify["verify"](o);
};
p_verify["number"] = function(o) {
	var v = p_verify.val(o);
	if (isNaN(v)) {
		o.attr("v_check", "Y");
	} else {
		p_verify.msg(o, "必须填写数字")
	}
	return p_verify["verify"](o);
};
p_verify["verify"] = function(o) {
	if (o.attr("v_check") == "Y") {
		return true;
	} else {
		return false;
	}
};
p_verify.msg = function(o,msg){
	if(o.attr("MSG")){
		o.attr("v_check", o.attr("MSG"));
	}else{
		o.attr("v_check",msg);
	}
}
p_verify.get = function(myUrl, myData, success, myDataType) {
	$.ajax({
		url : myUrl,
		type : 'get',
		async:false,
		dataType :myDataType,
		data : myData,
		success : success
	});
};
