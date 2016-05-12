var prismTemplete  = function() {
	this.$prism__property = {};
	this.html = function(obj, val) {
		obj.html(val);
		this.def(obj, val);
	};// html
	this.def = function(obj, val) {
		if (val == null) {
			var def = obj.attr("data-default");
			if (def != null) {
				obj.html(this.getValue(def));
			}
		}
	};// def默认
	this.jData = function(obj,val){
		var key = obj.attr("data-exp");
		obj.data(key,val);
	};//jquery元素data
	this.attr = function(obj, val) {
		var data_attr = obj.attr("data-attr");
		this.$prism__property["attr"] = val;
		var data_attr_obj = $.parseJSON(data_attr);
		for (key in data_attr_obj) {
			var data_exp = data_attr_obj[key];
			var data_value = this.getValue(data_exp,obj);
			if (data_value != null) {
				obj.attr(key, data_value);
			}
		}
		delete this.$prism__property["attr"];
	};//设置属性
	this.text = function(obj, val) {
		obj.text(val);
		this.def(obj, val);
	};// text
	this.format = function(obj, val) {
		var str = obj.html();
		for (m in val) {
			var re = new RegExp('\\{\\{' + m + '\\}\\}', 'gm');
			str = str.replace(re, val[m]);
		}
		obj.html(str);
	};// format
	this.list = function(obj, val) {
		var map = obj.attr("data-map");
		if (obj.data("templete_prism_$$") == null) {
			obj.data("templete_prism_$$", obj.html());
		}
		obj.html("");
		for (i in val) {
			var list_templete = obj.data("templete_prism_$$");
			$div = $(list_templete);
			this.$prism__property[map] = val[i];
			this.preview($div);
			obj.append($div);
			delete this.$prism__property[map];
		}
	};// loop
	this.data = function(key, val) {
		this.$prism__property[key] = val;
	};// data

	this.preview = function($this) {
		var self = this;
		if ($this.attr("data-exp") != null) {
			var that = $this;
			var data_exp = that.attr("data-exp");
			var data_method = that.attr("data-method");
			var val = self.getValue(data_exp,that);
			if (data_method == null) {
				data_method = "html";
			}
			self[data_method](that, val);
		}
		$("[data-exp]", $this).each(function() {
			var that = $(this);
			var data_exp = that.attr("data-exp");
			var data_method = that.attr("data-method");
			var val = self.getValue(data_exp, that);
			if (data_method == null) {
				data_method = "html";
			}
			try {
				self[data_method](that, val);
			} catch (e) {
				console.log(e);
			}
		});
	};// preview
	this.getValue = function(data_exp, that) {
		var self = this;
		var exps = data_exp.split(".");
		var val = null;
		try {
			for (i in exps) {
				var ex = exps[i];
				if (ex.indexOf("{") == 0) {// 调用函数方式
					var tmp = $.parseJSON(ex);
					for ( var func_name in tmp) {
						ex = self[func_name](tmp[func_name]);
						break;
					}
				}
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
			val = null;
		}
		return val;
	};// getValue
};
if(typeof define == "function"){
	define("prismTemplete", [], function() {
		return prismTemplete;
	});
}
