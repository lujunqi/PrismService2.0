var $;
layui.use([ 'form', "laydate", "upload", "layedit", "table" ], function() {
	$ = layui.jquery;
	f.initEdit();
	f.initData();
	
});
var f = {};
f.ans = [];
f.idxEdit = 0;

f.initEdit = function() {

	var layedit = layui.layedit;
	layedit.set({
		uploadImage : {
			url : BASE + '/up/file',
			type : 'post'
		}
	});

	tool = [ 'strong' // 加粗
	, 'italic' // 斜体
	, 'underline' // 下划线
	, 'del' // 删除线

	, '|' // 分割线

	, 'left' // 左对齐
	, 'center' // 居中对齐
	, 'right' // 右对齐
	, 'link' // 超链接
	, 'unlink' // 清除链接
	, 'face' // 表情
	, 'image' // 插入图片
	, 'help' // 帮助
	]

	// var index = layedit.build('demo',{tool:tool}); //建立编辑器
	f.idxEdit = layedit.build('msg_text'); // 建立编辑器
	
	$("#ans_add").click(function() {

		layer.open({
			type : 1,
			area : [ "100%", "100%" ],
			shade : .05,
			shadeClose : !0,
			moveType : 1,
			title : "添加调查问卷",
			skin : "layui-layer-msg",
			content : $("#ans_win").html(),
			success : function(l, n) {
				var form = layui.form;
				form.render();

				form.on("select(ans_type)", function(data) {
					var m = {
						"checkbox" : 1,
						"radio" : 1
					};
					if (m[data.value] == 1) {
						$("#def_item", l).show();
					} else {
						$("#def_item", l).hide();
					}
				});

				form.on('checkbox(verify)', function(data) {

					$("input[name='verify']").prop("checked", false);
					$(this).prop("checked", true);
					form.render('checkbox');
				});
				
				l.find(".layui-btn-primary").on("click", function() {
					layer.closeAll();
				});
				layui.form.on("submit(ans-code-yes)", function(e) {

					var v = [];
					$("input:checkbox[name='verify']:checked").each(function() {
						v.push($(this).val());
					});
					var item = e.field;

					item.ver = v;
					item.idx = f.ans.length;
					
					f.ans.push(item);
					f.initTbl();

					layer.closeAll();
				});
			}
		});
	});
};


f.initTbl = function() {
	var table = layui.table;
	
	table.render({
		elem : '#ans_tbl',
		cols : [ [ // 标题栏
		 {
			field : 'cname',
			
			title : '名称'
		}, {
			field : 'def',
			title : '选项'
		}, {
			fixed : 'right',
			
			align : 'center',
			toolbar : '#ans_bar'
		} ] ],
		data : f.ans
	});
	table.on('tool(ans_tbl)', function(obj) {
		var item = obj.data;
		if (obj.event === 'del') {
			layer.confirm("是否删除", {
				btn : [ '确定', '取消' ],
				title : "提示"
			}, function() {
				
				for(i in f.ans){
					var i2= f.ans[i];
					if(i2.idx == item.idx){
						f.ans.splice(i, 1); 

						break;
					}
				}
				
				f.initTbl();
				layer.closeAll();
			});

		}
	});
}

f.initData = function() {
	cou.post("/data/weix.user_group",{},function(res){
		if(cou.nvl(res.data)){
			$("#add_slt_user").click(function() {
			cou.msg("没有权限");
			});
		}else{
			
			
			$("#add_slt_user").click(function() {
				  layer.open({
					    type: 1,
					    area : [ "100%", "50%" ],
					    top:0,
						shade : .05,
						shadeClose : !0,
						title:"选择用户",
						
					    content: $("#sltUsers").html(),
					    success : function(l, n) {
					    	var group = res.data.user_group.split(",");
							var html = "";
							for(var i in group){
								html+='<a style="margin: 5px;" class="layui-badge slt_group" data-id="'+group[i]+'">'+group[i]+'</a>';
							}
							
							$("#user_group",l).html(html);
							$(".slt_group").click(function(){
								var gid = $(this).attr("data-id");
								f.slt_group(gid,l);
							});
					    }
					  });

			});			
		}
	});


	
	$("#clr_slt_user").click(function() {
		$(".slt_user").trigger("click");
	});
	
};

f.slt_group =function(gid,l){
	cou.post("/data/weix.slt_user_group",{group:gid},function(res){
		console.log(res)
		var html = "";
		var item = res.data;
		if(item.length>0){
			$("#all_slt_div",l).show();
		}else{
			$("#all_slt_div",l).hide();
		}
		for(var i in item){
			html+='<a style="margin: 5px;" class="layui-badge layui-bg-green unslt_user" data-id="'+item[i]["user_id"]+'">'+item[i]["user_name"]+'</a>';
			
		}
		$("#my_group",l).html(html);
		$(".unslt_user",l).unbind("click");
		$(".unslt_user",l).click(function(){
			
			$(this).hide();
			var id = $(this).attr("data-id");
			var nm = $(this).text();
			
			var y = $(".slt_user[data-id='"+id+"']");
			if(y.length>0){
				y.show();
			}else{
				var a  = $('<a href="javascript:" class="layui-badge layui-bg-green slt_user" data-id="' + id + '"  style="margin:3px;padding: 2px;">' + nm + '</a>');
				$("#user_ids").append(a);
			}
			f.slt_user();
		});
		$("#all_slt_user",l).click(function(){
			$(".unslt_user",l).trigger("click")	
		});
		
		
	});
};
f.slt_user = function() {
	$(".slt_user").unbind("click");
	$(".slt_user").click(function() {
		$(this).hide();
		var id = $(this).attr("data-id");
		$(".unslt_user[data-id='" + id + "']").show();
	});
}
f.initFrm = function() {
	var form = layui.form;
	var uuid = cou.get1("UUID");
	if (!cou.nvl(cou.get1(uuid))) {
		var MS_PARAM = cou.get1(uuid);
		var data18 = {};
		$.extend(true, data18, MS_PARAM["DATA"]);
		
		//问卷回填
		var msg_ans = data18.msg_ans;
		if(msg_ans){
			f.ans = cou.toJson(msg_ans);	
		}
		
		f.initTbl();
		
		layui.form.val('frm1', data18);
		if (data18.user_ids) {
			var user_ids = data18.user_ids.split(",");
			var user_names = data18.user_names.split(",");
			var html = "";
			for (i in user_ids) {
				var id = user_ids[i];
				var nm = user_names[i];

				html += '<a href="javascript:" class="layui-badge layui-bg-green slt_user" data-id="' + id + '"  style="margin:3px;padding: 2px;">' + nm + '</a>';
			}
			$("#user_ids").html(html);

		}
		f.slt_user();
	}
	layui.form.render();

	form.on('submit(demo2)', function(data) {
		var param = data.field;
		var url = "/data/msg.add";
		var dts = param.be_date.split(" ");

		param.b_date = dts[0];
		param.e_date = dts[2];
		
		param.msg_desc = layui.layedit.getText(f.idxEdit);
		
		param.msg_text = layui.layedit.getContent(f.idxEdit);
		
		if(param.msg_text==""){
			cou.msg("主体内容不能为空");
			return ;
		}
		var user_ids = [];
		$(".slt_user").each(function(){
			user_ids.push($(this).attr("data-id"));
		});
		
		param.user_ids=user_ids.join(",");
		param.msg_ans = cou.toString(f.ans);
		
		if (!cou.nvl(cou.get1(uuid))) {
			
			var MS_PARAM = cou.get1(uuid);
			
			if(!cou.nvl(MS_PARAM["DATA"])){
				url = "/data/msg.upd";	
			}
			
		}
		
		layer.load(0, {
			time : 3 * 1000
		});
		cou.post(url, param, function(res) {
			if (res.code == "1") {
				cou.msg(res.msg)
			} else {
				$("#back").trigger("click");
			}
			layer.closeAll('loading');

		});
	});

};
