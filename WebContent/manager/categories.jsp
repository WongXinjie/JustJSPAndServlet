<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="com.bookstores.service.*" %>
<%@ page import="com.bookstores.domain.*" %>
<%@ page import="com.bookstores.utils.*" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="blogs"/>
<meta name="author" content="wongxinjie">

<link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap.min.css">
<link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap-theme.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/base.css">
<script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
<script src="http://cdn.bootcss.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>

<title>管理后台</title>
</head>
<body>
<div id="wrap">
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
  <!-- Brand and toggle get grouped for better mobile display -->
  <div class="container">
  <div class="navbar-header">
    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#">
      <span class="sr-only">Toggle navigation</span>
      <span class="icon-bar"></span>
      <span class="icon-bar"></span>
      <span class="icon-bar"></span>
    </button>
    <a class="navbar-brand" href="<%=request.getContextPath()%>/">书店</a>
  </div>

  <!-- Collect the nav links, forms, and other content for toggling -->
  <div class="collapse navbar-collapse" id="easyreading_bar">
    <ul class="nav navbar-nav">
      <li><a  class="navbar-brand" href="/"></a></li>
       <li><a href="<%=request.getContextPath()%>/books">书籍管理</a></li>
      <li><a href="<%=request.getContextPath()%>/manager/categories.jsp">分类管理</a></li>
    </ul>
      <form class="navbar-form navbar-left" action="#" method="GET">
      <div class="form-group">
        <input type="text" class="form-control" name="title" placeholder="搜图书标题、ISBN">
      </div>
      <button type="submit" class="btn btn-default">搜索</button>
    </form>
    <ul class="nav navbar-nav navbar-right">
    <li><a href="<%=request.getContextPath()%>/manager/info.jsp">我的资料</a></li>
    <li><a href="<%=request.getContextPath()%>/logout">退出</a></li>
    </ul>
   </div>
  </div>
</nav>

<div class="container">
	<div id="content" class="row row-offcanvas row-offcancas-right">
		<div class="col-xs-12 col-sm-9 col-sm-offset-2">
		 <%
		List<Category> categoryList =(List<Category>)session.getAttribute("categoryList");
		for(Category category: categoryList){
		%>
		<p><%=category.getName()%>
		&nbsp;&nbsp;<a href="<%=request.getContextPath()%>/manager/category?caid=<%=category.getCategoryID()%>" class="btn btn-success">编辑</a>
		&nbsp;&nbsp;<a href="<%=request.getContextPath()%>/manager/deletecategory?cid=<%=category.getCategoryID()%>" class="btn btn-danger">删除</a>
		</p>
		<%
		}
		%>
	<a href="<%=request.getContextPath()%>/manager/category" class="btn btn-success btn-large">添加分类</a>
		</div>
		
	</div>
</div>
</div>

<div id="footer">
	<div class="container">
		<p class="text-muted credit"><a href="#">关于书店</a>&nbsp;&nbsp;&nbsp;<a href="#">联系我们</a></p>
	</div>
</div>
</body>
</html>