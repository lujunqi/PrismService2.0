
<%@ page language="java" import="java.util.*,java.net.*"
	pageEncoding="UTF-8"%>
<% String BASE=request.getContextPath(); %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>表格</title>
    <link rel="stylesheet" href="<%=BASE %>/frame/layui/css/layui.css">
    <link rel="stylesheet" href="<%=BASE %>/frame/static/css/style.css">
    <link rel="icon" href="<%=BASE %>/frame/static/image/code.png">
    
    <script type="text/javascript" src="<%=BASE %>/frame/layui/layui.js"></script>
    <script type="text/javascript" src="<%=BASE %>/script/common.js"></script>
    <script type="text/javascript" src="index.js"></script>
    <style type="text/css">
    .layui-laypage-limits select{
    display: none;
    }
    </style>
</head>
<body class="body">



<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
    <legend>合同管理1</legend>
</fieldset>
<script type="text/html" id="barDemo">
  <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="upd">编辑</a>
  <a class="layui-btn layui-btn-xs" lay-event="del">删除</a>
  
</script>
<div class="demoTable">
    搜索：
    <div class="layui-inline">
        <input class="layui-input" name="keyword"  id="search_info" autocomplete="off">
    </div>
    <button class="layui-btn" id="search">搜索</button>
    <button class="layui-btn" id="add">新增</button>
    
</div>
<table id="demo" lay-filter="demo"></table>

</body>
</html>