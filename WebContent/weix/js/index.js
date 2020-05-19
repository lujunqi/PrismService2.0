var $;
var wx = {};
layui.use([ 'laytpl','jquery' ], function() {
	$ = layui.jquery;
	wx.init();
});

wx.init= function (){
	var url="/data/weix.msg.list";
	var param = {};
	var laytpl = layui.laytpl;
	
	cou.post(url, param, function(res) {
		
		laytpl($("#wxlist_tmp").html()).render(res, function(string) {

			$("#wxlist").html(string);
			
			$(".msg").click(function(){
				cou.href("msg.jsp?id="+$(this).attr("data-id"));
			})
		});
		;
	});
	
};