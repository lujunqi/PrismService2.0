<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	isELIgnored="false"%>
<% String paraValue=application.getInitParameter("userName"); %> <%= paraValue %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>

<style type="text/css">
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
</style>
</head>
<body>
	<video id="video"  style='width: 640px; height: 480px'></video>
	<button id='picture'>PICTURE</button>
	<canvas id="canvas" width="640" height="480"></canvas>
	<script type="text/javascript">
		var video = document.getElementById("video");
		var context = canvas.getContext("2d");
		var errocb = function() {
			console.log("wrong");
		}
		if (navigator.getUserMedia) {
			navigator.getUserMedia({
				"video" : true
			}, function(stream) {
				video.src = stream;
				video.play();
			}, errocb);
		} else if (navigator.webkitGetUserMedia) {
			navigator.webkitGetUserMedia({
				"video" : true
			}, function(stream) {
				video.src = window.webkitURL.createObjectURL(stream);
				//video.play();
			}, errocb);
		}
		document.getElementById("picture").addEventListener("click", function() {
			context.drawImage(video, 0, 0, 640, 480);
			var Pic = document.getElementById("canvas").toDataURL("image/png");
			Pic = Pic.replace(/^data:image\/(png|jpg);base64,/, "");
			console.log(Pic);
		});
	</script>
</body>
</html>