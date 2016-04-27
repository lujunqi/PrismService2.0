<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<link rel="stylesheet" type="text/css" href="../css/global-min.css" />
<link rel="stylesheet" type="text/css" href="../css/common.css" />
<link rel="stylesheet" type="text/css" href="../css/form.css" />
<script type="text/javascript">
function resize(){
	var index = parent.layer.getFrameIndex(window.name); 
	parent.layer.iframeAuto(index);
}
</script>
</head>

<body onload="resize();">
<div class="wrapper comWrap">
  
  <div class="wrap-inner">
  	
    <form class="baseFrm" id="baseFrm">
      
      <ul class="frmList clearfix">
      <li>
          <span class="lab"><label for="">舞台名称：</label></span>
          <span class="mod"><input type="text" class="text w200" id="" name="stage_name" /></span>
      </li>
      <li>
          <span class="lab"><label for="">选择图片：</label></span>
          <span class="mod"><input type="text" class="text w200" name="stage_url"/></span>
      </li>
      <li>
          <span class="lab"><label for="">描叙：</label></span>
          <span class="mod"><textarea class="w350 h100 mt5" name="stage_desc" cols="5" rows="5"></textarea></span>
      </li>
     </ul>
    </form>
  </div>
  <div class="wrap-btm clearfix">
    <div class="wrap-btm-l">&nbsp;</div>
    <div class="wrap-btm-r">&nbsp;</div>
  </div>
</div>
</body>
</html>