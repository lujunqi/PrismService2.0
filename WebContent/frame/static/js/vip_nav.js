/**
 * Created by Administrator on 2017/5/20.
 * @name:   vip-admin 后台模板 菜单navJS
 * @author: 随丶
 */
layui.define(['layer', 'element'], function (exports) {
    // 操作对象
    var layer = layui.layer
        , element = layui.element
        , $ = layui.jquery;

    // 封装方法
    var mod = {
    	addHtml2:function(res,obj, treeStatus) {
    		
    		var view = "";
    		 if (res.data) {
                 $(res.data).each(function (k, v) {
                     v.subset && treeStatus ? view += '<li class="layui-nav-item layui-nav-itemed">' : view += '<li class="layui-nav-item">';
                     if (v.subset) {
                         view += '<a href="javascript:;"><i class="layui-icon">' + v.icon + '</i>' + v.text + '</a><dl class="layui-nav-child">';
                         $(v.subset).each(function (ko, vo) {
                             view += '<dd>';
                             if(vo.target){
                                 view += '<a href="' + vo.href + '" target="_blank">';
                             }else{
                                 view += '<a href="javascript:;" href-url="' + vo.href + '">';
                             }
                             view += '<i class="layui-icon">' + vo.icon + '</i>' + vo.text + '</a></dd>';
                         });
                         view += '<dl>';
                     } else {
                         if (v.target) {
                             view += '<a href="' + v.href + '" target="_blank">';
                         } else {
                             view += '<a href="javascript:;" href-url="' + v.href + '">';
                         }
                         view += '<i class="layui-icon">' + v.icon + '</i>' + v.text + '</a>';
                     }
                     view += '</li>';
                 });
             }
             // 添加到 HTML
             $(document).find(".layui-nav[lay-filter=" + obj + "]").html(view);
             // 更新渲染
             element.init();
    	},
        // 添加 HTMl
        addHtml: function (addr, obj, treeStatus, data) {
        	var my_role = [];
        	for(i in data){
        		my_role.push(""+data[i]["id"]);
        	}
        	
            // 请求数据
            $.get(addr, {}, function (res) {
                var view = $("<div>");
                if (res.data) {
                    $(res.data).each(function (k, v) {
                    	var li = $('<li data-name="home" class="layui-nav-item"></li>');
                    	
                		
                    	if (v.subset) {
                    		
                    		
                    		var a = $('<a href="javascript:;" '+func_dis(my_role,v.role)+' ><i class="layui-icon">' + v.icon + '</i>' + v.text + '<span class="layui-nav-more"></span></a>');
                    		var dl = $('<dl class="layui-nav-child" '+func_dis(my_role,v.role)+' ></dl>');
                    		$(v.subset).each(function (k1, v1) {
                    			dis1 = "";
                    			var dd = $('<dd><a href="javascript:;"  '+func_dis(my_role,v1.role)+'  >'+v1.text+'</a></dd>');
                    			if(v1.href){
                    				dd = $('<dd><a href="javascript:;"  '+func_dis(my_role,v1.role)+'  href-url="'+v1.href+'">'+v1.text+'</a></dd>');
                    				dl.append(dd);
                    			}
                    			
                    			if(v1.subset){
                    				var	li2 = $('<dd data-name="home" '+func_dis(my_role,v1.role)+' class="layui-nav-item"><a href="javascript:" onClick="vShow('+k+','+k1+');">'+v1.text+'<span class="layui-nav-more"></span></a></dd>');
                    				dl.append(li2);
                    				$(v1.subset).each(function (k2, v2) {
                    					
                    					var dd2  = $('<dd class="layui-hide v_'+k+'_'+k1+'" style="padding-left:15px;"><a href="javascript:;" href-url="'+v2.href+'">'+v2.text+'</a></dd>');
                    					dl.append(dd2);
                    				});
                    				
                    				
                    				
                    			}
                    			
                    		});
                    		li.append(a);
                    		li.append(dl);
                    		
                    	}else{
                    		
                    		var a = $('<a href="javascript:;" '+func_dis(my_role,v.role)+'  href-url="' + v.href + '"><i class="layui-icon">' + v.icon + '</i>' + v.text + '</a>');
                    		if (v.target) {
                    			a.attr("target","_blank");
                    		}
                    		li.append(a);
                    	}
                    	
                    	view.append(li);
                    	
                    });
                } else {
                    layer.msg('接受的菜单数据不符合规范,无法解析');
                }
                // 添加到 HTML
                  $(document).find(".layui-nav[lay-filter=" + obj + "]").html(view.html());
                // 更新渲染
                element.init();
            },'json');
        }
        // 左侧主体菜单 [请求地址,过滤ID,是否展开,携带参数]
    	, main: function (addr, obj, treeStatus, data) {
            // 添加HTML
            this.addHtml(addr, obj, treeStatus, data);
        }
    	, main2: function (res, obj, treeStatus) {
            this.addHtml2(res, obj, treeStatus);
        }
        
        // 顶部左侧菜单 [请求地址,过滤ID,是否展开,携带参数]
    	, top_left: function (addr, obj, treeStatus, data) {
            // 添加HTML
            this.addHtml(addr, obj, treeStatus, data);
        }
    	, top_left2: function (res, obj, treeStatus) {
            // 添加HTML
    		this.addHtml2(res, obj, treeStatus);
        }
        

    };

    // 输出
    exports('vip_nav', mod);
});
function func_dis(my_role,mrole){
	if(my_role==null){
		my_role = [];
	}
	var roles = cou.role();
	var m_role = null;
	if(roles[mrole]){
		var r146 = roles[mrole]["roles"]
		
		if(r146!=null){
			
			m_role =cou.toJson( r146)["k"];
		}
	
	}
	if(m_role==null){
		m_role = [];
	}
	
	var n_role = m_role.filter(
			  function (val) { return my_role.indexOf(val) > -1 }
			 );
	var dis = "";
	if(cou.my().isBoss){
		
		n_role[0]=1;
	}
	if(n_role.length==0){
		dis = ' style="display:none;" ';
	}
	
	return dis;
}
function vShow(k,k1){
	var bar = $(".v_"+k+"_"+k1);
	
	
	if(bar.hasClass("layui-hide")){
		bar.removeClass('layui-hide');
        bar.addClass('layui-show');
	}else{
		bar.removeClass('layui-show');
        bar.addClass('layui-hide');
	}
	
	
}
