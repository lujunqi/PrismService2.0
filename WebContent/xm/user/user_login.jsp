<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	isELIgnored="false"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>登录</title>
    <meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <jsp:include page="/xm/header.jsp"></jsp:include> 

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
            <p class="block-heading">登录</p>
            <div class="block-body">
                <form>
                    <label>手机号码</label>
                    <input type="text" class="span12">
                    <label>验证码</label>
                    <span class="span12">
                    <input type="text" >
                    <img alt="验证码" src="">
                   </span>
                    
                    <a href="index.html" class="btn btn-primary pull-right">登录</a>
                    <div class="clearfix"></div>
                </form>
            </div>
        </div>
 
        <p style="text-align: center;"><a href="user_reg.jsp">未有账号，立即注册</a></p>
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


