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
		cou.href("item.jsp");
	});
	$("#search").click(function() {
		f.set_table();
		$("#search_info").val("");
	});
}

f.set_table = function() {
	var table = layui.table;
	table.render({
		elem : '#demo',
		url : cou.URL("data/hetong.list"), // 数据接口
		page : true, // 开启分页
		cellMinWidth : 80, // 全局定义常规单元格的最小宽度，layui 2.2.1 新增
		where : {
			"my_user_id" : cou.my().user_id,
			"my_user_co" : cou.my().user_co,
			"info" : $("#search_info").val()
		},
		cols : [ [ // 表头
		{
			field : 'ht_kh_name',
			title : '客户名称'

		}, {
			field : 'ht_no',
			title : '合同号'
		}, {

			title : '是否结账',
			templet : function(res) {
				var m = {
					"y" : "是",
					"n" : "否"
				};
				return m[res.ht_jz_yn];
			}

		}, {

			title : '租赁类型',
			templet : function(res) {
				var m = {
					"out" : "租出",
					"in" : "租入"
				};
				return m[res.ht_zu_type];
			}
		}, {
			field : 's_ht_b_date',
			title : '租赁开始日期'
		}, {
			field : 's_ht_e_date',
			title : '租赁结束日期'
		}, {

			title : '按吨计算',
			templet : function(res) {
				var m = {
					"y" : "是",
					"n" : "否"
				};
				return m[res.ht_dun_yn];
			}
		}, {
			fixed : 'right',
			width : 165,
			align : 'center',
			toolbar : '#barDemo'
		} ] ]
	});
	table.on('tool(demo)', function(obj) {
		var item = obj.data;

		if (obj.event === 'upd') {
			top.MS_PARAM["OPT"] = "upd";
			top.MS_PARAM["DATA"] = item;
			window.location.href = "item.jsp";
		}

		if (obj.event === 'del') {
			if (item.ht_jz_yn == "n") {
				var msg = "该客户还未结帐,无法删除!请单击“编辑”后将是否结帐改为“是”后再使用删除功能即可删除当前客户的所有数据。";
				layer.alert(msg, {
					icon : 2,
					title:"提醒您",
					skin : 'layer-ext-moon'
				})
				return;
			}
			layer.confirm("是否删除", {
				btn : [ '确定', '取消' ],
				title : "提示"
			}, function() {
				var param = {};
				param.ht_id = item.ht_id;
				cou.post("data/hetong.del.d", param, function(res) {
					layer.closeAll();
					f.set_table();
				});
			});

		}
	});

};
