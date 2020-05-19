<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<meta charset="UTF-8">



<script type="text/javascript"
	src="http://g.alicdn.com/dingding/dingtalk-pc-api/2.3.1/index.js"></script>
<script type="text/javascript" src="../frame/layui/layui.js"></script>

<script>

	var $;
	layui.use([ 'layer'], function() {
		// 操作对象
		layer = layui.layer, $ = layui.jquery;
		
		init();
	});
	function init(){
		DingTalkPC.runtime.permission.requestAuthCode({
			corpId : "ding7e93978de9880fe135c2f4657eb6378f",
			onSuccess : function(result) {
				var code = result.code;
				
				$.post("../dingtalk",{"code":code},function(res){
					alert(res)	
				});
			},
			onFail : function(err) {
				cosole.log(err)
			}
		});
	}
</script>
</html>