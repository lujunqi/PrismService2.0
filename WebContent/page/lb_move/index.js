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

	$("#back").click(function() {

		if (BACK != "") {
			window.location.href = BACK;
		} else {
			window.location.href = "index.jsp";
		}

	});
}

f.set_table = function() {
	
	var deplist = cou.depList();
	var myDep = cou.my().department;
	var dep = {};
	for (i in myDep) {
		var d = myDep[i]+"";
		dep[d]=1;
		$("p", $("#" + d, deplist)).each(function(){		
			dep[$(this).attr("id")] = 1;
		});
	}
	var dep2 = [];
	for(k in dep){
		dep2.push(k);
	}
	
	var table = layui.table;
	var $cols = [];
	$cols.push({
		field : 'user_id',
		
		title : '用户ID'
	});
	
	$cols.push({
		field : 'user_name',
		
		title : '用户名称'
	});
	
	
	$cols.push({
		title : '所在部门',
		templet : function(res) {
			
			var dep = res.user_dep.split(",");
			var html = "";
			for(i in dep){
				html+=$("#" + dep[i], deplist).attr('name') + '  ';
			}
			return html;
		}
	});
	cou.cols($cols, {
		fixed : 'right',
		width : 300,
		align : 'center',
		toolbar : '#barDemo'
	});
	
	
	

	table.render({
		elem : '#demo',
		url : cou.URL("/data/lb.user4Dep"), // 数据接口
		page : true, // 开启分页

		where : {
			"my_user_id" : cou.my().userid,
			"dep" : dep2.join(",")
		},

		cols : [ $cols ]
	});
	cou.setRole();
	table.on('tool(demo)', function(obj) {
		var item = obj.data;

		if (obj.event === 'move') {

			layer.confirm("是否提交", {
				btn : [ '确定', '取消' ],
				title : "提示"
			}, function() {
				var url = null;
				if(FTYPE=='liangf'){
					var url = "/data/cust.liangf";
				}
				if(FTYPE=='move'){
					var url = "/data/gather.move";
				}
				
				if(url!=null){
					cou.post(url, {
						gather_id : OTHER_ID,
						uer_id:item.user_id
					}, function() {
						$("#back").trigger("click");
					});	
				}
				
			});
			
		}
	});
};
