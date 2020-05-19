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
}

f.set_table = function() {
	var my = cou.my();
	var table = layui.table;
	var $cols = [];
	cou.cols($cols, {
		field : 'gather_id',
		width:80,
		title : '编号'
	},"m2");
	cou.cols($cols, {
		field : 'gather_name',
		width:120,
		title : '姓名'
	});
	cou.cols($cols, {
		field : 'gather_co',
		width:120,
		title : '单位'
	});
	
	cou.cols($cols, {
		field : 'gather_tel',
		width:120,
		title : '手机',
		templet : function(res) {
			if(res.user_id==my.userid){
				return res.gather_tel;
			}else if(my.isBoss){
				return res.gather_tel;
			}else{
			       
			    return "";
			}
		}
	});
//	cou.cols($cols, {
//		title : '微信',
//		width:180,
//		templet : function(res) {
//			var m = {
//				"Y" : "",
//				"N" : "(未关注)"
//			};
//			if(res.gather_weixin){
//			return res.gather_weixin+m[res.gather_weixin_jion];
//			}else{
//				return "";
//			}
//			
//		}
//	});
	cou.cols($cols, {
		field : 'gather_type',
		sort: true,
		width:80,
		title : '性质'
	});
	cou.cols($cols, {
		field : 'user_name',
		sort: true,
		width:120,
		title : '隶属人'
	});
	cou.cols($cols, {
		field : 'gather_level',
		width:80,
		title : '等级'
	});
	cou.cols($cols, {
		field : 'follow_num',
		width:80,
		title : '回访次数',
		templet : function(res) {
			return res.follow_num +"次";
		}
	});
	cou.cols($cols, {
		field : 'gather_create_date',
		width:120,
		title : '创建时间'
	});
	cou.cols($cols, {
		field : 'cust_num',
		width:80,
		title : '客户量'
	});
	cou.cols($cols, {
		field : 'measure_num',
		width:80,
		title : '量房数'
	});
	cou.cols($cols, {
		field : 'measure_pay_num',
		width:80,
		title : '信息费'
	});
	cou.cols($cols, {
		field : 'sign_num',
		width:100,
		title : '信息提成'
	});
	cou.cols($cols, {
		fixed : 'right',
		width : 300,
		align : 'right',
		toolbar : '#barDemo'
	});

	table.render({
		elem : '#demo',
		url : cou.URL("/data/gather.list"), // 数据接口
		page : true, // 开启分页
		defaultToolbar : [ 'filter' ],
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
			window.location.href = "item.jsp?opt=upd&id=" + item.gather_id;
		}
		if (obj.event === 'acust') {
			window.location.href = "../lb_cust/item.jsp?opt=add&back=../lb_gather&gid=" + item.gather_id;
		}
		if (obj.event === 'follow') {
			window.location.href = "../lb_follow/index.jsp?ftype=gather&back=../lb_gather&oid=" + item.gather_id;
		}
		if (obj.event === 'move') {
			window.location.href = "../lb_move/index.jsp?ftype=move&back=../lb_gather&oid=" + item.gather_id;
		}
		
		if (obj.event === 'del') {
			layer.confirm("是否删除", {
				btn : [ '确定', '取消' ],
				title : "提示"
			}, function() {

				cou.post("/data/gather.del", {
					gather_id : item.gather_id
				}, function() {
					layer.closeAll();
					f.set_table();
				});
			});

		}
	});

};
