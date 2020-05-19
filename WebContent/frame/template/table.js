var tbl = {};
var fn = {};
tbl.barDemo = function() {
	var html = '';
	for (i in tbl.event()) {
		var item = tbl.event()[i];
		if(!item.check){
			html += '<a class="layui-btn layui-btn-xs" lay-event="x' + i + '">' + item.title + '</a>';	
		}else{
			html += '{{# if('+item.check+'){ }}';
			html += '<a class="layui-btn layui-btn-xs" lay-event="x' + i + '">' + item.title + '</a>';
			html += '{{# } }}';
		}
		
	}
	$("#barDemo").html(html);
};
tbl.setHeader = function(header) {
	if (header != null) {
		$("#content_header").html("");
		for (i in header) {
			var item = header[i];
			$("#content_header").append(ele[item.type](item, i))
		}
	}
}
var ele = {};
ele.text = function(item, i) {
	var node = $('<span>' + item.title + '</span>');
	return node;
}
ele.input = function(item, i) {
	var node = $('<div class="layui-inline">' + '<input class="layui-input" placeholder="' + item.placeholder + '" name="' + item.id + '"  id="' + item.id
			+ '" autocomplete="off">' + '</div>');
	return node;
}

ele.btn = function(item, i) {
	var event = item.fn;
	var node = $('<button class="layui-btn" id="btn_' + i + '">' + item.title + '</button>');
	node.unbind("click");
	node.on('click', function() {

		fn[event.key](event, {});
	});
	return node;
};

tbl.set_table = function(url) {
	var table = layui.table;

	var table_name = table.render({
		id : "ptable",
		elem : '#demo',
		headers : {
			token : "JSON.stringify(this)"
		},
		url : cou.URL($("#demo").attr("data-url"), this), // 数据接口
		page : true, // 开启分页
		cellMinWidth : 80, // 全局定义常规单元格的最小宽度，layui 2.2.1 新增
		where : tbl.where(),
		
		cols : [ tbl.cols ]
	});
	var page_size = table_name.config.limit;
	
	table.on('tool(demo)', function(obj) {

		for (i in tbl.event()) {
			var item = tbl.event()[i];
			if (obj.event === "x" + i) {

				var event = item.fn;

				if (event && fn[event.key]) {

					fn[event.key](event, obj.data);
				}

			}

		}
	});
};
fn.from = function(event, item) {
	var uuid = cou.uuid(6);
	var MS_PARAM = {};

	MS_PARAM["DATA"] = item;
	MS_PARAM["HREF"] = $("#MYURL").val();
	cou.set1("UUID", uuid, true);
	cou.set1(uuid, MS_PARAM);

	cou.href(event["page_url"]);

};
fn.set_table = function(event, item) {
	tbl.set_table();
};
fn.confirm = function(event, item) {
	if (event.begin) {
		console.log(event.begin)
	}

	layer.confirm(event.msg, {
		btn : [ '确定', '取消' ],
		title : "提示"
	}, function() {
		var param = {};

		cou.post(event['data_url'], item, function(res) {
			layer.closeAll();

			if (res.code != 0) {
				cou.msg(res.msg);
			}
			tbl.set_table();

		});
	});
};
