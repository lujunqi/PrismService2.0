$(function() {
	mas();

});
function mas() {
	var $container = $('#con1_1');
	var $url = "../pa/stage.s";
	$.post($url, function(data) {
		var cmd = new prismTemplete();
		cmd.data("data", data);
		cmd.preview($container);

		$container.imagesLoaded(function() {
			$container.masonry();
			gridOpen();
			gridButton();
		});

	}, "json");
}
function gridOpen(){
	$(".grid > .context").each(function() {
		$(this).click(function() {
			layer.open({
				btn : [ 'OK' ],
				content : $(this).html()
			});
		});
	});
}
function gridButton(){
	$(".favorites",".grid").click(function(){
		var id = $(this).parent().attr("data-id");
		var index = layer.open({content:"<img src='css/loading-0.gif'>"});
		var $url = "../pa/favorites_add.u";
		var param = {};
		param["goods_id"]  = id;
		param["menber_id"] = user_id;
		param["goods_type"] = "stage";
		$.post($url,param,function(data){
			if(data["code"]==1 && data["result"]==1){
				mas();
			}
			layer.close(index);
		},"json");
		
	});
}