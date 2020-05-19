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
		cou.href("item.jsp?opt=add");
	});
	$("#search").click(function() {
		f.set_table();
		$("#search_info").val("");
	});
};

f.set_table = function() {
	var table = layui.table;
	var $cols = [];

	cou.cols($cols, {
		field : 'cust_id',
		width : 80,
		title : 'ID'
	},"m3");

	cou.cols($cols, {
		field : 'cust_name',
		width : 100,
		title : '客户名称'
	});
	cou.cols($cols, {
		field : 'cust_space',
		width : 100,
		title : '面积'
	});
	var my = cou.my();
	cou.cols($cols, {
		field : 'cust_tel',
		width:120,
		title : '手机',
		templet : function(res) {
			if(res.user_id==my.userid){
				return res.cust_tel;
			}else if(my.isBoss){
				return res.cust_tel;
			}else{
			    return "";
			}
		}
	});
	
	cou.cols($cols, {
		field : 'cust_create_date',
		width : 120,
		title : '创建时间'
	});
	cou.cols($cols, {
		field : 'follow_info',
		width : 250,
		title : '最后回访记录'
	});
	cou.cols($cols, {
		field : 'cust_status_e',
		width : 80,
		title : '状态'
	});
	cou.cols($cols, {
		field : 'follow_num',
		width : 100,
		title : '回访次数',
		templet : function(res) {
			return res.follow_num+'次' ;
		}
		
	});
	cou.cols($cols, {
		width : 100,
		title : '未回访天数',
		templet : function(res) {
			if(res.follow_day==null){
				res.follow_day=0;
			}
			return res.follow_day ;
		}
	});

	cou.cols($cols, {
		fixed : 'right',
		width : 220,
		align : 'right',
		toolbar : '#barDemo'
	});

	table.render({
		elem : '#demo',
		url : cou.URL("/data/cust.list"), // 数据接口
		page : true, 
		
		defaultToolbar : [ 'filter'],
		height : 'full-200',
		toolbar : true,
		where : {
			"my_user_id" : cou.my().userid,
			"my_opt":cou.my_opt(),
			"info" : $("#search_info").val()
		},
		done : function(res) { // 返回数据执行回调函数
			cou.setRole();
		},
		cols : [ $cols ]
	});
	cou.setRole();

	table.on('tool(demo)', function(obj) {
		var item = obj.data;

		if (obj.event === 'upd') {
			window.location.href = "item.jsp?opt=upd&id=" + item.cust_id+"&gid="+item.gather_id;
		}
		
		if (obj.event === 'follow') {
			window.location.href = "../lb_follow/index.jsp?ftype=cust&back=../lb_cust&oid=" + item.cust_id;
		}
		if (obj.event === 'work') {//给部门主管
			var cust_status = $(this).attr("data-work");
			layer.confirm("是否提交", {
				btn : [ '确定', '取消' ],
				title : "提示"
			}, function() {
				cou.post("/data/cust.work", {
					id : item.cust_id,
					cust_status:cust_status
				}, function() {
					layer.closeAll();
					f.set_table();
				});
			});
		}
		if (obj.event === 'del') {
			layer.confirm("是否删除", {
				btn : [ '确定', '取消' ],
				title : "提示"
			}, function() {
				cou.post("/data/cust.del", {
					id : item.cust_id
				}, function() {
					layer.closeAll();
					f.set_table();
				});
			});

		}
	});

};
