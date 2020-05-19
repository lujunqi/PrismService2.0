var $;
layui.use([ 'layer' ], function() {
	// 操作对象
	$ = layui.jquery;

	init();
});

function init() {

	dd.runtime.permission.requestAuthCode({
		corpId : CORPID,
		onSuccess : function(result) {
			var code = result.code;
			$.post(BASE + "/dingtalk", {
				"code" : code
			}, function(res) {
			
				cou.set("my_user", res);
				$("#my_user_name").text(res.name);
				
				initClick();
	
				initMenu();
			}, "json");
		},
		onFail : function(err) {
			alert(err)
		}

	});
}

function initMenu(){
	
	$.post(BASE+"/json/1.json",{},function(res){
	
		var div = $("<div>");
		
		mkMenu(res.data,0,div);
		
		$("#menu_div").html(div.html());
	});
}
function mkMenu(res,k,div){
	for(i in res){
		var item = res[i];
		var li =$('<li class="layui-nav-item layui-nav-itemed"><a></a></li>');
		if(k!=0){
			li = $('<dd><a></a></dd>');
		}
		$("a",li).html(item.text)
		if(item.href){
			$("a",li).attr("href","../"+item.href);
			$("a",li).attr("target","ifrm");
			
		}
		if(item.subset){
			var dl = $('<dl class="layui-nav-child"></dl>')
			
			mkMenu(item.subset,1,dl);
			li.append(dl);
		}
		
		div.append(li);
	}
}
function initClick(){
	var isHide = true;
	$("#menu").click(function(){
		if(isHide){
			$("#menu_div").hide(500);
			isHide = false;
		}else{
			$("#menu_div").show(500);
			isHide = true;
		}
		
	});
}