<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<script type="text/javascript" src="scripts/jquery.js"></script>
<body>


	<p>
		画布： <a href="javascript:" id="aa">++</a> <a href="javascript:" id="bb">--</a>

	</p>
	<canvas id="myCanvas" width="1000" height="600"
		style="border: 1px solid #d3d3d3; background: #ffffff;">
Your browser does not support the HTML5 canvas tag.
</canvas>
	<script type="text/javascript" src="plug/canvas/hidpi-canvas.js"></script>
	<script>
		var h = 271 * 3;
		var w = 381 * 3;
		var $top = 0, $left = 0;

		window.onload = function() {
			var Canvas = document.getElementById("myCanvas");
			var bg = new Image();
			bg.src = "4K2.jpg";
			var cxt = Canvas.getContext("2d");
			var canvas = $(Canvas);
			var getCanvasPoint = function(x, y) {
				var canvasOffset = canvas.offset();
				return {
					x : x - canvasOffset.left,
					y : y - canvasOffset.top
				}
			};
			var move = false;
			var nTop = $top;
			var nLeft = $left;

			canvas.on("mousedown", function(e) {
				move = true;
				var point = getCanvasPoint(e.pageX, e.pageY);
				nTop = point.y-$top;
				nLeft = point.x-$left;

			});
			canvas.on("mousemove", function(e) {
				if (!move) {
					return;
				}
				var point = getCanvasPoint(e.pageX, e.pageY);
				$top = point.y - nTop;
				$left = point.x - nLeft; 
				setCxt(cxt, w, h, bg);
			});
			canvas.on("mouseup", function(e) {
				if (!move) {
					return;
				}
				move = false;
				var point = getCanvasPoint(e.pageX, e.pageY);
				nTop = point.y;
				nLeft = point.x;
			});

			var bg = new Image();
			//你指定了图片的地址，但是图片的加载是需要时间的

			bg.src = "4K2.jpg";
			//执行完上面一行，马上就开始画这个图
			//虽然bg这个对象有了，但是图片还没有加载完成，因此画的时候什么都没有

			bg.onload = function() {

				setCxt(cxt, w, h, bg);
			};
			$("#aa").click(function() {
				w = w + 10;
				h = h + 10;
				setCxt(cxt, w, h, bg);

			});
			$("#bb").click(function() {
				w = w - 10;
				h = h - 10;

				setCxt(cxt, w, h, bg);
			});
		};
		function setCxt(cxt, w, h, bg) {

			// polyfill 提供了这个方法用来获取设备的 pixel ratio  
			var getPixelRatio = function(context) {
				var backingStore = context.backingStorePixelRatio || context.webkitBackingStorePixelRatio || context.mozBackingStorePixelRatio || context.msBackingStorePixelRatio || context.oBackingStorePixelRatio || context.backingStorePixelRatio || 1;

				return (window.devicePixelRatio || 1) / backingStore;
			};

			var ratio = getPixelRatio(cxt);
			console.log(ratio);
			cxt.mozImageSmoothingEnabled = true;
			cxt.webkitImageSmoothingEnabled = true;
			cxt.msImageSmoothingEnabled = true;
			cxt.imageSmoothingEnabled = true;
			cxt.quality = 0;
			cxt.clearRect($left, $top, w * ratio, h * ratio);
			cxt.drawImage(bg, $left, $top, w * ratio, h * ratio);
		}
	</script>

</body>
</html>