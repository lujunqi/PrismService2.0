<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	isELIgnored="false"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="com.prism.common.VMControl"%>

<%
	ApplicationContext context = (ApplicationContext)request.getAttribute("context");
VMControl vm = new VMControl(request,"m_app_unit");
%>
<!DOCTYPE html>
<html lang="cn">
<head>
<%
	String path = request.getContextPath();
	String basePath =  path + "/";
%>
<base href="<%=basePath%>">
<meta charset="utf-8">
<title>${VIEWNAME}</title>
<meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<jsp:include page="/xm/header.jsp"></jsp:include>
<script type="text/javascript" src="/prism/xm/form_templete.js"></script>
<script type="text/javascript">
	$(init);
	function init() {
		${CALLBACK}
	}
</script>
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
					

					<form name="form">

						<%
							if(request.getAttribute("COL")!=null){
															@SuppressWarnings("unchecked")
															List<Map<String,Object>> list_val = (List<Map<String,Object>>)request.getAttribute("COL");
															for(Object en: list_val ){
																@SuppressWarnings("unchecked")
																Map<String,Object> tmap = (Map<String,Object>)en;
															 	out.println(vm.control(tmap));
															}
														}
						%>
						<div class="clearfix"></div>
					</form>
				</div>
			</div>

			<p style="text-align: center;">
				${FOOTER}
			</p>
		</div>
	</div>

	<jsp:include page="/xm/bootstrap_include.jsp"></jsp:include>


</body>
</html>


