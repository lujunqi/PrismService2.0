<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	isELIgnored="false"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="com.prism.common.VMControl"%>

<%
ApplicationContext context = (ApplicationContext)request.getAttribute("context");
@SuppressWarnings("unchecked")
Map<String,String> from = (Map<String,String>)context.getBean("m_unit");
VMControl vm = new VMControl(request);
System.out.println(1);
%>
<!DOCTYPE html>
<html lang="cn">
  <head>
    <meta charset="utf-8">
    <title>${VIEWNAME}</title>
    <meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <jsp:include page="/xm/header.jsp"></jsp:include> 
    <script src="/prism/xm/user/user_reg.js" type="text/javascript"></script>
  </head>

  <!--[if lt IE 7 ]> <body class="ie ie6"> <![endif]-->
  <!--[if IE 7 ]> <body class="ie ie7 "> <![endif]-->
  <!--[if IE 8 ]> <body class="ie ie8 "> <![endif]-->
  <!--[if IE 9 ]> <body class="ie ie9 "> <![endif]-->
  <!--[if (gt IE 9)|!(IE)]><!--> 
  <body class=""> 
  <!--<![endif]-->
    
    <div class="navbar">
        <div class="navbar-inner">
                <ul class="nav pull-right">
                    
                </ul>
        </div>
    </div>
    


    

    
        <div class="row-fluid">
    <div class="dialog">
        <div class="block">
            <p class="block-heading">${VIEWNAME}</p>
            <div class="block-body">
                <form>
                     <%
if(request.getAttribute("COL")!=null){
	@SuppressWarnings("unchecked")
	Map<String,Object> map_val = (Map<String,Object>)request.getAttribute("COL");
	for(Map.Entry<String,Object> en: map_val.entrySet()){
		@SuppressWarnings("unchecked")
		Map<String,Object> tmap = (Map<String,Object>)en.getValue();
				System.out.println(tmap);
			String div = String.format("<label>%1$s</label>%2$s",en.getKey(),vm.control(tmap));
		 	out.println(div);
	}
}
%> 
                    
                    <a  class="btn btn-primary pull-right">注册</a>
                    <div class="clearfix"></div>
                </form>
            </div>
        </div>
 
        <p style="text-align: center;"><a href="user_login.jsp">已有账号，直接登录</a></p>
    </div>
</div>


    


         <script src="/prism/xm/lib/bootstrap/js/bootstrap.js"></script>
    <script type="text/javascript">
        $("[rel=tooltip]").tooltip();
        $(function() {
            $('.demo-cancel-click').click(function(){return false;});
        });
    </script>
    
  </body>
</html>


