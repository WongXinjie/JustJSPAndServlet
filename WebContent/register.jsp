<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="easy-reading with django"/>
<meta name="author" content="Vagrant Wong">
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
			
			if($(this).is('#id_username')){
				if(this.value == ""){
					var errorMsg='用户名不能为空';
					$parent.append('<span class="formtips onError">'+errorMsg+'</span>');
				}
			}
			
			if($(this).is('#id_email')){
				if(this.value == ""){
					var errorMsg = '邮箱不能为空';
					$parent.append('<span class="formtips onError">'+errorMsg+'</span>');
				}else if(this.value != "" &&  /^[\w\-\.]+@[\w\-\.]+(\.\w+)+$/.test(this.value) == false){
					var errorMsg = '请输入正确的邮箱地址';
					$parent.append('<span class="formtips onError">'+errorMsg+'</span>');
				}
			}

			if($(this).is('#id_password1')){
				if(this.value == ""){
					$parent.append('<span class="formtips onError">密码不能为空</span>');
				}else if(this.value.length < 6){
					$parent.append('<span class="formtips onError">密码长度不够</span>');
				}
			}
			
			if($(this).is('#id_password2')){
				var password = $('#id_password1').val();
				if(this.value != password){
					$parent.append('<span class="formtips onError">输入密码不一致</span>');
				}
			}
		});

		$('input:last').click(function(){
			$("form :input").trigger('blur');
			var numError = $('form .onError').length;
			if( numError){
				return false;
			}
		});
	});
</script>		
					
<title>注册</title>
</head>
<body>
<div id="wrap">
<div class="container">
<%
String role = (String)request.getAttribute("role");
if(role != null && role.equals("manager")){
	request.setAttribute("role", "manager");
%>
<h2 style="text-align:center">管理者注册</h2>
<%
}else{
	request.setAttribute("role", "user");
%>
<h2 style="text-align:center">用户注册</h2>
<%
}
%>
<div class="well">
<form class="form-horizontal" action="<%=request.getContextPath()%>/register" method="post">
<input type="hidden" name="role" value="<%=request.getAttribute("role") %>"/>
	<div class="form-group required">
		<label class="control-label col-sm-2" for="id_username">用户名</label>
		<div class="col-sm-8">
		<input type="text" placeholder="用户名即昵称" class="form-control" name="username" id="id_username">
		</div>
	</div>
	<div class="form-group required">
		<label class="control-label col-sm-2" for="id_email">邮箱</label>
		<div class="col-sm-8">
		<input type="text" placeholder="请输入邮箱" class="form-control" name="email" id="id_email">
		</div>
	</div>
	<div class="form-group required">
		<label class="control-label col-sm-2" for="id_password1">密码</label>
		<div class="col-sm-8">
		<input type="password" placeholder="请输入密码" class="form-control" name="password1" id="id_password1">
		</div>
	</div>
	<div class="form-group required">
		<label class="control-label col-sm-2" for="id_passwrod2">密码(确认)</label>
		<div class="col-sm-8">
		<input type="password" placeholder="请确认密码" class="form-control" name="password2" id="id_password2">
		</div>
	</div>
	<div class="form-group">
	<div class="col-sm-offset-2 col-sm-8">
	<input type="submit" value="注册" class="btn btn-info">
	</div>
	</div>
</form>
</div>
</div>
</div>
<div id="footer">
	<div class="container">
		<p class="text-muted credit"><a href="#">书店</a>&nbsp;&nbsp;&nbsp;<a href="#">联系我们</a></p>
	</div>
</div>
</body>
</html>



