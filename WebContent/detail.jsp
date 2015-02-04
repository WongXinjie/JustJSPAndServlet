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

<title>首页</title>
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
      <li class="dropdown">
        <a href="/" class="dropdown-toggle" data-toggle="dropdown"><b class="caret"></b>分类</a>
        <ul class="dropdown-menu">
        <li>
        <% 
        List<Category> categoryList = (List<Category>)session.getAttribute("categoryList");
		for(Category category: categoryList){
        %>
          <li><a href="<%=request.getContextPath()%>/books?category=<%=category.getCategoryID()%>"><%=category.getName()%></a></li>
         <%
		}
         %>
        </ul>
      </li>
    </ul>
      <form class="navbar-form navbar-left" action="#" method="GET">
      <div class="form-group">
        <input type="text" class="form-control" name="title" placeholder="搜图书标题、ISBN">
      </div>
      <button type="submit" class="btn btn-default">搜索</button>
    </form>
    <%
    BookUser bookUser = (BookUser)session.getAttribute("loginUser");
    if( bookUser != null){
    %>
    <ul class="nav navbar-nav navbar-right">
    <li><a href="<%=request.getContextPath()%>/user/info.jsp">我的资料</a></li>
    <li><a href="<%=request.getContextPath()%>/user/logout">退出</a></li>
    </ul>
    <%
    }else{
    %>
    <ul class="nav navbar-nav navbar-right">
    <li><a href="<%=request.getContextPath() %>/login">登录</a></li>
    <li><a href="<%=request.getContextPath()%>/register">注册</a></li>	 
    </ul>
    <%}%>
   </div>
  </div>
</nav>

<div class="container">
	<div id="content" class="row row-offcanvas row-offcancas-right">
		<div class="col-xs-12 col-sm-9">
		<%
		Book book = (Book) request.getAttribute("book");
		%>
		<p><a href="<%=request.getContextPath()%>/detail.jsp/?bid=<%=book.getBookID()%>"><%=book.getTitle()%></a></p>
		<p><%=book.getISBN() %></p>
		<p><%=book.getPrice() %></p>
		<p><%=book.getPublisher()%></p>
		<p><%=book.getPublishedDate()%></p>
		<p><%=book.getDescription()%></p>
		<button class="btn btn-success" id="id_buy">购买</button>&nbsp;&nbsp;<button class="btn btn-info" id="id_collect">收藏</button>
		<div id="comment-area">
		<%
		List<BookUserComment> commentList = (List<BookUserComment>)request.getAttribute("commentList");
		for(BookUserComment comment: commentList){
		%>
		<p><%=comment.getCommentTime()%>&nbsp;&nbsp;<%=comment.getBookUserID()%></p>&nbsp;&nbsp;<%=comment.getContent() %></p>
		<%
		}
		%>
		</div>
	<form class="form-horizontal" <% if(session.getAttribute("loginUser") != null){%>action="<%=request.getContextPath()%>/user/comment" method="post" <%}%> id="comment-form">
	<% if(session.getAttribute("loginUser") == null){%> <fieldset disabled><%}%>
	<input type="hidden" name="bid" value="<%=book.getBookID()%>"/>
	<div class="form-group required">
	<div class="col-sm-12 textarea">
		<textarea rows="3" class="form-control comment" name="content" id="id_content"></textarea>
	</div>
	</div>
	<div class="form-group">
	<div class="col-sm-offset-11">
		<input type="submit" value="提交" class="btn btn-default"/>
	</div>
	</div>
	<% if(session.getAttribute("loginUser") == null){%></fieldset><%}%>
</form>
</div>
	<div id="sidebar" class="col-xs-6 col-sm-3 sidebar-offcanvas">
		<%if(session.getAttribute("loginUser") != null){ %>
		<a href="<%=request.getContextPath()%>/orders?status=unpay">未付款订单</a>
		<a href="<%=request.getContextPath()%>/orders?status=payed">已付款订单</a>
		<a href="<%=request.getContextPath()%>/orders">全部订单</a>
		<%}else{%>
		<p>登录或注册</p>
		<%}%>
	</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	$(function(){
		$('#comment-form').submit(function(){
			$('#id_error_message').html('');
			var $strlen = $('#id_content').val().length;
			if($strlen > 280){
				$('#id_error_message').html('不能超过280字！');
				return false;
			}
			$.ajax({
				url: '<%=request.getContextPath()%>/user/comment',
				type: 'POST',
				data: $(this).serialize(),
				success: function(data){
					alert(data);
				}
			});
			return false;
		});
		$('#id_buy').click(function(){
			<%if(session.getAttribute("loginUser") == null){%>
				alert("您还没有登录!");
			<%}else{%>
			$.ajax({
				url: '<%=request.getContextPath()%>/user/addtocart',
				type: 'POST',
				data: 'bid=<%=book.getBookID() %>',
				success: function(data){
					alert(data);
				}
			});
			<%}%>
			return false;
		});
		$('#id_collect').click(function(){
			<%if(session.getAttribute("loginUser") == null){%>
				alert("您还没有登录!");
			<%}else{%>
			$.ajax({
				url: '<%=request.getContextPath()%>/user/addcollection',
				type: 'POST',
				data: 'bid=<%=book.getBookID() %>',
				success: function(data){
					alert(data);
				}
			});
			<%}%>
			return false;
		});
	});
</script>
<div id="footer">
	<div class="container">
		<p class="text-muted credit"><a href="#">关于书店</a>&nbsp;&nbsp;&nbsp;<a href="#">联系我们</a></p>
	</div>
</div>
</body>
</html>



