$(init);
var mydata = {};
function init() {
	init_tree();
	$("#m_save").click(func_m_save);
	
}
function init_tree(){
	$.post("../pa/family_tree.s", function(data) {
		obj(data);
		$("a", "#tree").each(function(){
			$(this).click(func_tree);
			
		});
		$("#SupFtId").click(function(){
			var ft_id = $("[name='ft_id']","#myModal").val();
			$("[name='sup_ft_id']","#myModal").val(ft_id);
		});
	}, "json");
}

function func_m_save(){
	var ft_data = $("#myModal").data("ft_data");
	var my_param = {};
	$("[name]","#myModal").each(function(){
		var key = $(this).attr("name");
		var val = $(this).val();
		my_param[key] = val;
	});
	if(my_param["ft_id"]!=my_param["sup_ft_id"]){//修改
		$.post("../pa/family_tree_upt.u",my_param,function(data){
			init_tree();
		},"json");
	}else{//新增
		$.post("../pa/family_tree_add.u",my_param,function(data){
			init_tree();
		},"json");
	}
	$("#m_close").trigger("click");
	
}
function func_tree() {

	var id = $(this).parent().attr("id");
	var dt = mydata[id];
	$("#myModal").data("ft_data",dt);
	if(dt["ft_icon"]!=null){
		$("#m_ft_icon #m_ft_name").attr("src",dt["ft_icon"]);
	}
	$("[name]","#myModal").each(function(){
		$(this).val(dt[$(this).attr("name")]);
	});
	$("#myModal #m_ft_name").html(dt["ft_name"]);
	$("#my_modal").trigger("click");
}
function obj(data) {
	$("#tree > ul").html("");
	$("#sup_ft_id").html("");
	for (index in data) {
		var id = data[index]["ft_id"];
		mydata["id" + id] = data[index];
		var sup_id = data[index]["sup_ft_id"];
		var name = data[index]["ft_name"];
		$("#sup_ft_id").append($('<option value="'+id+'">'+name+'</option>'));
		var html = '<li id="id{0}">';
		html += '<a data-toggle="modal" href="#" style="color:{2}">';
		html+='{1}</a></li>';
		if ($("#id" + sup_id, "#tree").length == 0) {//不存在
			var color = "#000";
			html = String.format(html, id, name,color);
			$("#tree > ul").append($(html));
		} else {
			if ($("#id" + sup_id + " > ul").length == 0) {
				$("#id" + sup_id).append($("<ul>"));
			}
			var color = "#000";
			if (data[index]["ft_sex"] == 2) {
				color = "#f3c";
			}
			html = String.format(html, id, name, color);
			$("#id" + sup_id + " > ul").append($(html));
		}
	}
}
String.format = function() {
	if (arguments.length == 0)
		return null;
	var str = arguments[0];
	for (var i = 1; i < arguments.length; i++) {
		var re = new RegExp('\\{' + (i - 1) + '\\}', 'gm');
		str = str.replace(re, arguments[i]);
	}
	return str;
}