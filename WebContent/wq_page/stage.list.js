$(function() {
	mas();

});
function mas() {
	var $container = $('#con1_1');
	$container.hide();
	var $url = "../pa/stage.s";
	$.post($url, function(data) {
		var cmd = new prismTemplete();
		cmd.data("data", data);

		cmd.funcFav = function(obj, val) {
			if (val != null) {
				if (val["fav"] == 0) {
					obj.html("收藏");
					obj.attr("data-fav", "Y");
				} else {
					obj.html("取消");
					obj.attr("data-fav", "N");
				}
			}
		};

		cmd.preview($container);
		$container.imagesLoaded(function() {
			$container.show();
			$container.masonry();
			
			gridOpen();
			gridButton();
		});

	}, "json");
}
function gridOpen() {
	$(".grid > .context").each(function() {
		$(this).click(function() {
			window.location.href="single.jsp"; 
		});
	});
}
function gridButton() {
	$(".order",".grid").click(function(){
		var $that = $(this);
		var id = $that.parent().attr("data-id");
		var goods_data = JSON.stringify($that.data("map")); 
		var param = {};
		param["goods_id"] = id;
		param["menber_id"] = user_id;
		param["goods_type"] = "stage";
		param["goods_data"] = goods_data;
		var url = "../pa/order_add.u";
		$.post(url, param, function(data) {
			
		},"json");
	});
	$(".favorites", ".grid").click(function() {
		var $that = $(this);
		var id = $that.parent().attr("data-id");
		var index = layer.open({
			content : "<img src='css/loading-0.gif'>"
		});
		var $url = "../pa/favorites_add.u";
		var param = {};
		param["goods_id"] = id;
		param["menber_id"] = user_id;
		param["goods_type"] = "stage";
		var map = {};
		map["Y"] = {
			url : "../pa/favorites_add.u",
			fav : "N",
			title : "取消"
		};
		map["N"] = {
			url : "../pa/favorites_del.u",
			fav : "Y",
			title : "收藏"
		};
		var obj = map[$that.attr("data-fav")];
		$.post(obj.url, param, function(data) {
			if (data["code"] == 1 && data["result"] == 1) {
				$that.html(obj.title);
				$that.attr("data-fav", obj.fav);
			}
			layer.close(index);
		}, "json");

	});
}