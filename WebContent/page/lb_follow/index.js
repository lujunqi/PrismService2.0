var $;
layui.use([ 'table', 'laytpl', 'laydate' ], function() {

	var laydate = layui.laydate;
	$ = layui.jquery;
	f.init();
});
var f = {}
f.init = function() {

	f.set_table();
	f.click();
};
f.click = function() {
	$("#add").click(function() {
		var follow_info = $("#follow_info").val();
		
		var param = {};
		if(cou.nvl(follow_info)){
			
			layer.msg("跟进结果不能为空", {
				icon : 5,
				time : 1000,
				shift : 6
			});
			return;
		}
		
		param["follow_info"] = follow_info;
		param["follow_type"] = FOLLOW_TYPE;
		param["my_user_name"] = cou.my().name;
		param["my_user_id"] = cou.my().userid;
		param["other_id"] = OTHER_ID;
		cou.post("/data/follow.add",param,function(res){
			$("#follow_info").val("");
			f.set_table();
		},true);
		
	});
	$("#back").click(function() {
		
		if (BACK != "") {
			window.location.href = BACK;
		} else {
			window.location.href = "index.jsp";
		}

	});
}

f.set_table = function() {
	var table = layui.table;
	var $cols = [];
	$cols.push({
		field : 'follow_id',
		width:80,
		title : 'ID'
	});
	$cols.push({
		field : 'follow_create_date',
		width:120,
		title : '时间'
	});
	$cols.push({
		field : 'follow_name',
		width:120,
		title : '跟进人'
	});
	$cols.push({
		field : 'follow_info',
		title : '信息'
	});
	

	table.render({
		elem : '#demo',
		url : cou.URL("/data/follow.list"), // 数据接口
		page : true, // 开启分页
		
		
		where : {
			"other_id" : OTHER_ID,
			"my_user_name" : cou.my().name,
			"follow_type" : FOLLOW_TYPE,
			
		},
		
		cols : [ $cols ]
	});
	cou.setRole();

	
};
