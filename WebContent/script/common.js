var cou = {};
cou.my_opt = function() {
	var my = cou.my();
	mopt = "X";
	if(my.isBoss){
		mopt = "BOSS";
	}else{
		var dep =my.isLeaderInDepts;
		 dep = dep.replace(/{/g,"{\"");
		 dep = dep.replace(/,/g,",\"");
		 dep = dep.replace(/:/g,"\":");
		
		console.log(dep)
		var md = cou.toJson(dep);
		var deps = []
		for(k in md){
			if(md[k]){
				deps.push(k);
			}
		}
		if(deps.length>0){
			mopt = deps.join(",");
		}
		
	}
	return mopt;
};
cou.cols = function($cols, param, lay_role) {
	if (lay_role == null) {
		$cols.push(param);
	} else {
		var my_role = cou.myRole();
		var dRole = cou.role();
		var m_role = [];
		if (dRole[lay_role]) {
			m_role = cou.toJson(dRole[lay_role].roles)["k"];
		}
		if (m_role == null) {
			m_role = [];
		}
		var n_role = m_role.filter(function(val) {
			return my_role.indexOf(val) > -1
		});
		if (n_role.length > 0) {
			$cols.push(param);
		}
	}
};
cou.ROLES = null;
cou.role = function() {
	var result = {};
	var roles = cou.ROLES;
	if (cou.nvl(roles)) {
		$.ajax({
			url : cou.URL("/data/lb.roles"),
			type : "GET",
			contentType : "application/json", // 必须有
			dataType : "json", // 表示返回值类型，不必须

			async : false,
			success : function(res) {

				for (i in res.data) {
					var it = res.data[i];
					result[it.role_no] = it;
				}
				cou.ROLES = result;
			},
			error : function() {
			}

		});
	}
	return cou.ROLES;
}
// 部门列表
cou.depList = function() {
	var root = $("<p>");
	$.ajax({
		url : cou.URL("/dingtalk?action=DEP_LIST"),
		type : "get",
		contentType : "application/json", // 必须有
		dataType : "json", // 表示返回值类型，不必须

		async : false,
		success : function(res) {

			if (res.errcode == 0) {
				var deps = res.department;
				for (var i = 0; i < deps.length; i++) {
					var dep = deps[i];
					var ele = $("<p>");
					ele.attr("id", dep.id);
					ele.attr("name", dep.name);
					ele.attr("parentid", dep.parentid);

					if (dep.parentid == null) {
						root.append(ele);
					} else {
						$("#" + dep.parentid, root).append(ele);
					}
				}
			}
		},
		error : function() {
		}

	});

	return root;
}
cou.setRole = function() {
	var dRole = cou.role();

	var my_role = cou.myRole();
	$("[lay-role]").each(function() {
		var lay_role = $(this).attr("lay-role");
		var m_role = [];
		if (dRole[lay_role]) {
			m_role = cou.toJson(dRole[lay_role].roles)["k"];
		}
		if (m_role == null) {
			m_role = [];
		}
		var n_role = m_role.filter(function(val) {
			return my_role.indexOf(val) > -1
		});
		// console.log(n_role,1,m_role,2,my_role)
		if (n_role.length > 0) {
			$(this).show();
		} else {
			$(this).hide();
		}
	});
}
cou.myRole = function() {
	var myRole = cou.my().roles;
	var my_role = [];
	for (i in myRole) {
		my_role.push(myRole[i]["id"] + "");
	}
	return my_role;
}
cou.set = function(key, obj) {
	localStorage.setItem(key, JSON.stringify(obj));
};
cou.get = function(key) {
	var obj = localStorage.getItem(key);
	if (obj == "") {
		return {};
	} else if (obj == null) {
		return {};
	} else {
		return JSON.parse(obj);
	}
}
cou.set1 = function(key, obj, clr) {
	if (clr) {
		sessionStorage.clear();
	}
	sessionStorage.setItem(key, JSON.stringify(obj));
}
cou.clr1 = function() {
	sessionStorage.clear();
}
cou.get1 = function(key) {
	var obj = sessionStorage.getItem(key);
	if (obj == "") {
		return {};
	} else if (obj == null) {
		return {};
	} else {
		return JSON.parse(obj);
	}
}
cou.del2 = function(key) {
	sessionStorage.removeItem(key);
}
cou.toJson = function(obj) {
	if (obj == null) {
		return {};
	}
	return JSON.parse(obj);
}
cou.toString = function(obj) {
	return JSON.stringify(obj);
}
cou.del = function(key) {
	localStorage.removeItem(key);
}
cou.msg = function(info, fn) {
	layer.msg(info, {
		offset : [ '50%' ],
		time : 1000
	}, function() {
		if (fn != null) {
			fn();
		}
	});

};

cou.href = function(url) {

	if (url.indexOf("/") == 0) {
		window.location.href = cou.URL(url);
	} else {
		window.location.href = url;
	}

};
cou.my = function() {
	var my = cou.get("my_user");
	return my;
}
cou.nvl = function(v) {
	if (v == null) {
		return true;
	}
	if (v == "") {
		return true;
	}
	if (Array.prototype.isPrototypeOf(v) && v.length === 0) {
		return true;
	}
	if (Object.prototype.isPrototypeOf(v) && Object.keys(v).length === 0) {
		return true;
	}
	return false;
}
cou.getQuery = function(variable) {
	var query = window.location.search.substring(1);
	var vars = query.split("&");
	for (var i = 0; i < vars.length; i++) {
		var pair = vars[i].split("=");
		if (pair[0] == variable) {
			return pair[1];
		}
	}
	return (false);
}

var BURL = "{0}{1}";

cou.post = function(url, param, fn, load) {
	var loadIndex = '';
	if (load) {
		loadIndex = layer.load();
	}
	if (cou.my() != null) {
		param.my_user_id = cou.my().userid;
		param.my_user_name = cou.my().name;
	}

	var nurl = BURL.format(BASE, url);

	$.post(nurl, param, function(res) {
		layer.close(loadIndex);

		fn(res);
	}, "json");

};

cou.post2 = function(url, param) {
	var result = {};
	if (cou.my() != null) {
		param.my_user_id = cou.my().userid;
		param.my_user_name = cou.my().name;
	}

	var nurl = BURL.format(BASE, url);

	$.ajax({
		url : nurl,
		type : "POST",
		// contentType: "application/json;charset=UTF-8",
		dataType : "json", // 表示返回值类型，不必须
		data : param,
		async : false,
		success : function(res) {
			result = res;

		},
		error : function() {
			return {};
		}

	});
	return result;
};

cou.URL = function(url, fn) {

	return BURL.format(BASE, url);
};
cou.uuid = function(len) {
	if (len == null) {
		len = 36;
	}
	var s = [];
	var hexDigits = "0123456789abcdef";
	for (var i = 0; i < len; i++) {
		s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);

	}

	var uuid = s.join("");
	return uuid;
};
String.prototype.format = function(args) {
	var result = this;
	if (arguments.length > 0) {
		if (arguments.length == 1 && typeof (args) == "object") {
			for ( var key in args) {
				if (args[key] != undefined) {
					var reg = new RegExp("({" + key + "})", "g");
					result = result.replace(reg, args[key]);
				}
			}
		} else {
			for (var i = 0; i < arguments.length; i++) {
				if (arguments[i] != undefined) {
					var reg = new RegExp("({)" + i + "(})", "g");
					result = result.replace(reg, arguments[i]);
				}
			}
		}
	}
	return result;
}
