<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="plug/layui/css/layui.css" media="all">

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="scripts/jquery.js"></script>
<script type="text/javascript" src="plug/layer/layer.js"></script>
<script type="text/javascript" src="plug/layui/layui.js"></script>
<script type="text/javascript">
$(function(){
	$.get("pa/lb_cust_infos.s",{},function(res){
		console.log(res,"lb_cust")
	},"json");
	$.get("pa/lb_user.s",{},function(res){
		console.log(res,"lb_user")
	},"json");

	
});

	layui.use('upload', function() {
		layui.upload({
			url : 'upload',
			ext : 'jpg|png|gif', //那么，就只会支持这三种格式的上传。注意是用|分割。

			success : function(res, input) {
				if(res.result=="Y"){
					$("#myImg").attr("src","upload/"+res.fileName);
				}
			}
		});
	});
</script>
</head>
<body>

	选择一个文件:
	<input type="file" name="uploadFile" lay-ext="jpg|png|gif"
		class="layui-upload-file" />
	<br />
	<br />
	<div>
	<img src="" id="myImg" width="300">
	</div>
</body>	
</html>