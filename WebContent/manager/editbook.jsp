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
		<div class="col-xs-12 col-sm-9">
	<%if(request.getAttribute("error") != null){%><p><%=request.getAttribute("error")%></p><%}%>
	<form class="form-horizontal" action="<%=request.getContextPath()%>/manager/book" method="post">
	<%
	Book book = (Book)request.getAttribute("book");
    if(book != null){
	%>	
	<input type="hidden" name="bid" value="<%=book.getBookID()%>" />
	<%
    }
	%>
	<div class="form-group">
		<label for="id_title" class="col-sm-2 control-label">标题</label>
		<div class="col-sm-10">
		<input type="text" class="form-control" name="title" id="id_title" value="<%if(book != null){%><%=book.getTitle()%><%}%>" >
		</div>
	</div>
	<div class="form-group">
		<label for="id_ISBN" class="col-sm-2 control-label">ISBN</label>
		<div class="col-sm-10">
		<input type="text" class="form-control" name="isbn" id="id_ISBN" value="<%if(book != null){%><%=book.getISBN()%><%}%>" >
		</div>
	</div>
	<div class="form-group">
		<label for="id_per" class="col-sm-2 control-label">出版社</label>
		<div class="col-sm-10">
		<input type="text" class="form-control" name="per" id="id_per" value="<%if(book != null){%><%=book.getPublisher()%><%}%>" >
		</div>
	</div>
	<div class="form-group">
		<label for="id_pda" class="col-sm-2 control-label">出版时间</label>
		<div class="col-sm-10">
		<input type="text" class="form-control" name="pda" id="id_pda" value="<%if(book != null){%><%=book.getPublishedDate()%><%}%>" >
		</div>
	</div>
	<div class="form-group">
		<label for="id_price" class="col-sm-2 control-label">价格</label>
		<div class="col-sm-10">
		<input type="text" class="form-control" name="price" id="id_price" value="<%if(book != null){%><%=book.getPrice()%><%}%>" >
		</div>
	</div>
	<div class="form-group">
		<label for="category" class="col-sm-2 control-label">分类</label>
		<div class="col-sm-10">
		<select id="category" name="cid" class="form-control">
		<%
		List<Category> categoryList = (List<Category>)session.getAttribute("categoryList");
		for(Category category: categoryList){
		%>
			<option value="<%=category.getCategoryID()%>"><%=category.getName()%></option>
		<%
		}
		%>
		</select>
		</div>
	</div>
	<div class="form-group">
		<label for="id_des" class="col-sm-2 control-label">简介</label>
		<div class="col-sm-10">
		<textarea class="form-control textarea" name="des" id="id_des" rows="10"><%if(book != null){%><%=book.getDescription()%><%}%></textarea>
		</div>
	</div>
	<input type="submit" class="btn btn-success col-sm-offset-11" value="提交"/>
</form>	
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