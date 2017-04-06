<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  String context = request.getContextPath();
context = context.equals("/")? context : context + "/";
%>    
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登入</title>
<link rel="stylesheet" href="<%=context%>3rd/bootstrap/css/bootstrap.min.css"/>
<link rel="stylesheet" href="<%=context%>3rd/bootstrap/css/font-awesome.min.css" />

<link rel="stylesheet" href="<%=context%>3rd/bootstrap/css/ace.min.css" />
<link rel="stylesheet" href="<%=context%>3rd/bootstrap/css/ace-rtl.min.css" />
<link rel="stylesheet" href="<%=context%>3rd/bootstrap/css/ace-skins.min.css" />

<script  type="text/javascript" src="<%=context%>3rd/jquery/jquery-2.0.3.min.js"></script>
<script type="text/javascript" src="<%=context%>3rd/layer/layer.min.js"></script>
<script type="text/javascript" src="<%=context%>3rd/layer/extend/layer.ext.js"></script>
</head>
<body class="login-layout">
	<div class="main-container">
			<div class="main-content">
				<div class="row">
					<div class="col-sm-10 col-sm-offset-1">
						<div class="login-container">
							<div class="space-32"></div>
								<div class="space-32"></div>
							<div class="center">
								<h1>
									<i class="icon-leaf green"></i>
									<span class="white">后台管理系统</span>
								</h1>
								<h4 class="blue">&copy; </h4>
							</div>

							<div class="space-6"></div>

							<div id ="login" class="position-relative">
								<div id="login-box" class="login-box visible widget-box no-border">
									<div class="widget-body">
										<div class="widget-main">
											<h4 class="header blue lighter bigger">
												<i class="icon-coffee green"></i>
												登入
											</h4>

											<div class="space-6"></div>

											<form action="j_spring_security_check" method="post" namespace="/">
												<fieldset>
													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input name="j_username" id="j_username" type="text" class="form-control" placeholder="Username"   />
															<i class="icon-user"></i>
														</span>
													</label>

													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input name="j_password" id="j_password" type="password" class="form-control" placeholder="Password"  />
															<i class="icon-lock"></i>
														</span>
													</label>

													<div class="space"></div>

													<div class="clearfix">

														<button id="submit-btn" type="button" class="width-35 pull-right btn btn-sm btn-primary" >
															<i class="icon-key"></i>
															Login
														</button>
													</div>

													<div class="space-4"></div>
												</fieldset>
											</form>


										</div><!-- /widget-main -->
									</div><!-- /widget-body -->
								</div><!-- /login-box -->
								<!-- /signup-box -->
							</div><!-- /position-relative -->
						</div>
					</div><!-- /.col -->
				</div><!-- /.row -->
			</div>
		</div><!-- /.main-container -->

<script type="text/javascript">
 $('#submit-btn').click(function(){
	var name = $('#j_username').val();
	var pwd = $('#j_password').val();
	if(!name){
		layer.tips('请输入用户名', $('#j_username'), {guide: 0, time: 2});
		$('#j_username').focus();
		return false;
	}
	if(!pwd){
		layer.tips('请输入密码', $('#j_password'), {guide: 0, time: 2});
		$('#j_password').focus();
		return false;
	}
	$("form").submit();
}); 
</script>
<script type="text/javascript" src="<%=context%>js/focus.js"></script>		
<script type="text/javascript">
$(document).ready(function() {
	autoFocus(0,"login");
});
</script>
</body>
</html>