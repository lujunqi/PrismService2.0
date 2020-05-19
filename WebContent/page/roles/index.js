var $;
layui.use([ 'table', 'tree' ], function() {

	$ = layui.jquery;
	f.init();
});
var f = {}
f.init = function() {
	f.setTree();
	f.setTable();
};
f.setTable = function() {

	var table = layui.table;
	var $cols = [];

	cou.cols($cols, {
		field : 'role_no',
		width : 80,
		title : '权限编码'
	});

	cou.cols($cols, {
		field : 'role_note',
		title : '权限描叙'
	});
	cou.cols($cols, {
		field : 'role_type',
		title : '权限分类'
	});
	cou.cols($cols, {
		title : '角色',
		templet : function(res) {
			roles = cou.toJson(res.roles);
			if(cou.nvl(roles)){
				return "";
			}else{
				return roles["v"] ;	
			}
			
		}
	});
	cou.cols($cols, {
		fixed : 'right',
		width : 100,
		align : 'center',
		toolbar : '#barDemo'
	});

	table.render({
		elem : '#demo',
		url : cou.URL("/data/roles.list"), // 数据接口
		page : true, 
		limit:30,
		height : 'full-200',
		done : function(res) { // 返回数据执行回调函数
			cou.setRole();
		},
		cols : [ $cols ]
	});
	cou.setRole();
	table.on('tool(demo)', function(obj) {
		var item = obj.data;

		if (obj.event === 'upd') {
			if(!f.DingRole){
				layer.msg("获取钉钉数据失败！", {
					icon : 5,
					time : 1000,
					shift : 6
				});
				return;
			}
			var tree = layui.tree;
			tree.reload('tree1', {});
			var role_key = [];
			if (item.roles) {
				role_key = cou.toJson(item.roles);
				
				tree.setChecked('tree1', role_key["k"]);
			}
		      

			layer.open({
				type : 1,
				title : '选择角色',
				area : [ '893px', '600px' ],
				content : $("#treeWin"),
				btn : [ '提交', '关闭' ],
				btn1 : function(index, layero) {
					
					var td = tree.getChecked('tree1');
					var div2 = $("<div>");
					f.t91(td,div2,0);
					console.log(div2.html())
					var roles = {k:[],v:[]};
					$("div",div2).each(function(){
						roles["k"].push($(this).attr("k"));
						roles["v"].push($(this).attr("v"));
					});
					var param ={};
					param["role_no"] = item.role_no;
					param["roles"] = cou.toString(roles);
					cou.post("/data/roles.upd",param,function(){
						f.setTable();
						layer.closeAll();
					},true);
				},
				btn2 : function(index, layero) {
					layer.closeAll();
				}
			});
		}
	});
};
f.t91 = function(td,div,idx){
	for(var i=0;i<td.length;i++){

		var d1 = $("<div>");
		d1.attr("k",td[i]["id"]);
		d1.attr("v",td[i]["title"]);
		if(idx!=0){div.append(d1);}
		
		if(td[i].children){
			f.t91(td[i].children,div,1);
		}
	}
}
f.DingRole = false;
f.setTree = function() {
	var tree = layui.tree
	$.post(cou.URL("/dingtalk"), {
		"action" : "roleList"
	}, function(res) {
		if (res.errcode == 0) {
			var list = res.result.list;
			f.DingRole = true;
			tree.render({
				elem : '#tree1',
				data : list,
				id : "tree1",
				checked : true,
				spread : true,
				showCheckbox : true
			});

		} else {
			console.log(res.errmsg)
		}
	}, "json");
};