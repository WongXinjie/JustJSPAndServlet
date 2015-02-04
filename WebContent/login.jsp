<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="easy-reading with django"/>
<meta name="author" content="Vagrant Wong">
<link rel = "stylesheet" href="/static/css/base.css">
<link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap.min.css">
<link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap-theme.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/base.css">
<script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
<script src="http://cdn.bootcss.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
<style type="text/css">
      .container{
		width: 600px;
	}
</style>
<script type="text/javascript">
	$(function(){
		$('form :input').blur(function(){
			var $parent = $(this).parent();
			$parent.find(".formtips").remove();
			
			if($(this).is('#id_email')){
				if(this.value == "" ||  /^[\w\-\.]+@[\w\-\.]+(\.\w+)+$/.test(this.value) == false){
					$parent.append('<span class="formtips onError">请输入正确邮箱地址</span>');
				}
			}
			
			if($(this).is('#id_password')){
				if(this.value == ""){
					$parent.append('<span class="formtips onError">密码不能为空</span>');
				}
			}
		});
		
		$('input:last').click(function(){
			$("form :input").trigger('blur');
			var error = $('form .onError').length;
			if( error ){
				return false;
			}
		});
	});
</script>
<title>登录</title>
</head>
<body>
<div id="wrap">
<div class="container">
<%
String role = (String)request.getAttribute("role");
if(role != null && role.equals("manager")){
%>
<h2 style="text-align:center">管理者登录</h2>
<%
}else{
%>
<h2 style="text-align:center">用户登录</h2>
<% }%>

<div class="well">
<form class="form-horizontal" action="<%=request.getContextPath()%>/login" method="post" role="form">
<input type="hidden" name="role" value="<%=request.getAttribute("role") %>"/>
	<div class="form-group required">
	<label class="control-label col-sm-2"  for="id_email">邮箱</label>
	<div class="col-sm-8">
	<input type="text" placeholder="请输入注册邮箱" class="form-control" name="email" id="id_email"/>
	</div>
	</div>
	<div class="form-group required">
	<label class="control-label col-sm-2" for="id_password">密码</label>
	<div class="col-sm-8">
	<input type="password" placeholder="请输入密码" class="form-control" name="password" id="id_password"/>
	</div>
	</div>
	<div class="form-group">
	<div class="col-sm-offset-2 col-sm-10">
	<input type="submit" value="登录" class="btn btn-info">&nbsp;&nbsp;&nbsp;没有帐号？
	<a href="/register">注册</a>
	</div>
	</div>
</form>
</div>
</div>
</div>
	
<div id="footer">
	<div class="container">
		<p class="text-muted credit"><a href="#">关于微克</a>&nbsp;&nbsp;&nbsp;<a href="#">联系我们</a></p>
	</div>
</div>
</body>
</html>

