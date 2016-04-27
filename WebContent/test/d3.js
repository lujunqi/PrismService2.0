$(init);
function init(){
	$("#m_save").click(func_m_save);
}
function init_tree(){
$.post("../pa/family_d3.s", function(data) {
	makeSVG(data);
}, "json");
}
init_tree();
function slt(data){
	var dt = data["data"];
	$("#myModal").data("ft_data",dt);
	if(dt["ft_icon"]!=null){
		$("#m_ft_icon #m_ft_name").attr("src",dt["ft_icon"]);
	}
	$("[name]","#myModal").each(function(){
		$(this).val(dt[$(this).attr("name")]);
	});
	$("#myModal #m_ft_name").html(dt["ft_name"]);
	$("#my_modal").trigger("click");
}
function func_m_save(){
	var ft_data = $("#myModal").data("ft_data");
	var my_param = {};
	$("[name]","#myModal").each(function(){
		var key = $(this).attr("name");
		var val = $(this).val();
		my_param[key] = val;
	});
	my_param["ft_id"] = ft_data["ft_id"];
	if(my_param["ft_id"]!=my_param["sup_ft_id"]){//修改
		$.post("../pa/family_tree_upt.u",my_param,function(data){
			init_tree();
		},"json");
	}else{//新增
		$.post("../pa/family_tree_add.u",my_param,function(data){
			init_tree();
		},"json");
	}
	$("#m_close").trigger("click");
	
}
function makeSVG(links) {
	var nodes = {};
	$("#sup_ft_id").html("");
	// Compute the distinct nodes from the links.
	links.forEach(function(link) {
		link.source = nodes[link.source] || (nodes[link.source] = {
			name : link.source,
			data : link
		});
		link.target = nodes[link.target] || (nodes[link.target] = {
			name : link.target,
			data : link
		});
		$("#sup_ft_id").append($('<option value="'+link["ft_id"]+'">'+link["ft_name"]+'</option>'));
	});

	var width = 1300, height = 500;

	var force = d3.layout.force().nodes(d3.values(nodes)).links(links).size(
			[ width, height ]).linkDistance(60).charge(-300).on("tick", tick)
			.start();
	d3.select("body").select("svg").remove();
	var svg = d3.select("body").append("svg").attr("width", width).attr(
			"height", height);

	// Per-type markers, as they don't inherit styles.
	svg.append("defs").selectAll("marker").data(
			[ "suit", "licensing", "resolved" ]).enter().append("marker").attr(
			"id", function(d) {
				return d;
			}).attr("viewBox", "0 -5 10 10").attr("refX", 15)
			.attr("refY", -1.5).attr("markerWidth", 6).attr("markerHeight", 6)
			.attr("orient", "auto").append("path").attr("d", "M0,-5L10,0L0,5");

	var path = svg.append("g").selectAll("path").data(force.links()).enter()
			.append("path").attr("class", function(d) {
				return "link " + d.type;
			}).attr("marker-end", function(d) {
				return "url(#" + d.type + ")";
			});

	var circle = svg.append("g").selectAll("circle").data(force.nodes())
			.enter().append("circle").attr("r", 8).call(force.drag).on("click",
					function(d) {
						slt(d);
					});

	var text = svg.append("g").selectAll("text").data(force.nodes()).enter()
			.append("text").attr("x", 8).attr("y", ".31em").text(function(d) {
				return d.name;
			});

	// Use elliptical arc path segments to doubly-encode directionality.
	function tick() {
		path.attr("d", linkArc);
		circle.attr("transform", transform);
		text.attr("transform", transform);
	}

	function linkArc(d) {
		var dx = d.target.x - d.source.x, dy = d.target.y - d.source.y, dr = Math
				.sqrt(dx * dx + dy * dy);
		return "M" + d.source.x + "," + d.source.y + "A" + dr + "," + dr
				+ " 0 0,1 " + d.target.x + "," + d.target.y;
	}

	function transform(d) {
		return "translate(" + d.x + "," + d.y + ")";
	}
}