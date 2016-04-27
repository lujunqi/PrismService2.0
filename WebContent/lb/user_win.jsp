<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<link rel="stylesheet" type="text/css" href="../css/global-min.css" />
<link rel="stylesheet" type="text/css" href="../css/common.css" />
<link rel="stylesheet" type="text/css" href="../css/form.css" />

</head>

<body>
<div class="wrapper comWrap">
  <div class="wrap-inner">
    <form class="baseFrm" id="baseFrm">
      <ul class="frmList clearfix">
      <li>
          <span class="lab"><label for="">名称</label></span>
          <span class="mod"><input type="text" class="text w200" id="" name="user_name" /></span>
      </li>
      <li>
          <span class="lab"><label for="">联系电话</label></span>
          <span class="mod"><input type="text" class="text w200" name="user_tel"/></span>
      </li>
      <li>
          <span class="lab"><label for="">电子邮箱</label></span>
          <span class="mod"><input type="text" class="text w200" name="user_email"/></span>
      </li>
      <li>
          <span class="lab"><label for="">账号</label></span>
          <span class="mod"><input type="text" class="text w200" name="user_acc"/></span>
      </li>
      <li>
          <span class="lab"><label for="">微信账号</label></span>
          <span class="mod"><input type="text" class="text w200" name="user_weixin"/></span>
      </li>
     </ul>
    </form>
  </div>
</div>
<script type="text/javascript">
var index = parent.layer.getFrameIndex(window.name); 
parent.layer.iframeAuto(index);
</script>
</body>
</html>