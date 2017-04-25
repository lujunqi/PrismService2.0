var prismTemplete = function() {
	this.$prism__property = {};
	this.currentElement = null;
	this.html = function(obj, val) {
		this.currentElement = obj;
		obj.html(val);
		this.def(obj, val);
	};// html
	this.def = function(obj, val) {
		this.currentElement = obj;
		if (val == null) {
			var def = obj.attr("data-default");
			if (def != null) {
				obj.html(this.getValue(def));
			}
		}
	};// def默认
	this.attr = function(obj, val) {
		this.currentElement = obj;
		var data_attr = obj.attr("data-attr");
		this.$prism__property["attr"] = val;
		var data_attr_obj = $.parseJSON(data_attr);
		for (key in data_attr_obj) {
			var data_exp = data_attr_obj[key];
			var data_value = this.getValue(data_exp);
			if (data_value != null) {
				obj.attr(key, data_value);
			}
		}
		delete this.$prism__property["attr"];
	};// 设置属性
	this.text = function(obj, val) {
		this.currentElement = obj;
		obj.text(val);
		this.def(obj, val);
	};// text
	this.val = function(obj, val) {
		this.currentElement = obj;
		obj.val(val);
	};// val
	this.format = function(obj, val) {
		this.currentElement = obj;
		var str = obj.html();
		for (m in val) {
			var re = new RegExp('\\{\\{' + m + '\\}\\}', 'gm');
			str = str.replace(re, val[m]);
		}
		obj.html(str);
	};// format
	this.list = function(obj, val) {// 废弃
		this.currentElement = obj;
		var map = obj.attr("data-map");
		if (obj.data("templete_prism_$$") == null) {
			obj.data("templete_prism_$$", obj.html());
		}
		obj.html("");
		this.$prism__property.map = [];
		for (i in val) {
			var list_templete = obj.data("templete_prism_$$");
			var $div = $("<div></div>");
			
			$div.html(list_templete);
			$("[data-exp]",$div).each(function(){
				var exp = $(this).attr("data-exp");
				var maps = exp.split(".");
				if($.inArray(map,maps)!=-1){
					maps.splice($.inArray(map,maps)+1,0,i);
				}
				$(this).attr("data-exp",maps.join("."));
			});
			obj.append($div.children());
			this.$prism__property.map.push(val[i]);
		}

	};// loop
	this.grid = function(obj, val) {
		this.currentElement = obj;
		var map = obj.attr("data-map");
		if (obj.data("templete_prism_$$") == null) {
			obj.data("templete_prism_$$", obj.html());
		}
		obj.html("");
		for (i in val) {
			var $cmd = new prismTemplete();
			var $templete = $(obj.data("templete_prism_$$"));
			var $div = $("<div></div>");
			$div.html($templete);
			for (key in this.$prism__property) {
				if (!$cmd[key]) {
					if (typeof this.$prism__property[key] == "function") {
						$cmd[key] = this.$prism__property[key];
						this[key] = function(obj, val) {
						};
					}
				}

			}
			val[i]["_index"] = i;
			$cmd.data(map, val[i]);
			$cmd.preview($div);
			obj.append($div.children());
		}

	}// grid
	this.data = function(key, val) {
		this.$prism__property[key] = val;
	};// data

	this.preview = function($this) {
		var self = this;
		if ($this.attr("data-exp") != null) {
			var that = $this;
			var data_exp = that.attr("data-exp");
			var data_method = that.attr("data-method");
			var exp111 = data_exp.split(":");
			if(exp111.length==2){
				data_exp = exp111[1];
				data_method = exp111[0];
			}
			var val = self.getValue(data_exp);
			if (data_method == null) {
				data_method = "html";
			}
			self[data_method](that, val);
		}
		$("[data-exp]", $this).each(function() {
			var that = $(this);
			var data_exp = that.attr("data-exp");
			var data_method = that.attr("data-method");
			var exp111 = data_exp.split(":");
			if(exp111.length==2){
				data_exp = exp111[1];
				data_method = exp111[0];
			}
			var val = self.getValue(data_exp, that);
			if (data_method == null) {
				data_method = "html";
			}
			try {
				self[data_method](that, val);
			} catch (e) {
//				if (this.debug)
					console.log(data_method + "===" + e);
			}
		});
	};// preview
	this.debug = false;
	this.getValue = function(data_exp, that) {
		var self = this;
		var exps = data_exp.split(".");
		var val = null;
		try {
			for (i in exps) {
				var ex = exps[i];
				
				if (val == null) {
					val = self.$prism__property[ex];
				} else {
					val = val[ex];
				}
				if (typeof val == "function") {
					val = val(that);
				}
				if (val == null) {
					break;
				}
			}
		} catch (e) {
			if (this.debug) {
				console.log(e)
			}

			val = null;
		}
		return val;
	};// getValue
};
if (typeof define == "function") {
	define("prismTemplete", [], function() {
		return prismTemplete;
	});
}

String.prototype.replaceAll = function(s1, s2) {
	return this.replace(new RegExp(s1, "gm"), s2);
}
