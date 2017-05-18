<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	isELIgnored="false"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>注册</title>
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
            <p class="block-heading">注册</p>
            <div class="block-body">
                <form>
                    <label>姓名</label>
                    <input name="user_name" type="text" class="span12">
                    <label>身份证号码</label>
                    <input name="user_card" type="text" class="span12">
                    <label>手机</label>
                    <input name="user_tel" type="text" class="span12">
                    
                    <label>所属社区</label>
                    <select name="regional_id" class="input-xlarge">
                    	<option>望城社区A</option>
                    	<option>望城社区B</option>
                    	<option>望城社区C</option>
                    	
                    </select>
                    <label>注册类</label>
                    <select name="user_type" class="input-xlarge" id="user_type" data-exp="d" data-method="grid"
					data-map="map">
                    	<option>外部人员</option>
                    	<option>内部人员</option>
                    	
                    </select>
                    
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


