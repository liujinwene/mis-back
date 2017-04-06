<%@page import="java.util.List"%>
<%@page import="com.sec.security.model.SecMenu"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
  String context = request.getContextPath();
  context = context.equals("/")? context : context + "/";
%>    
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>后台管理系统</title>
<jsp:include page="include/head-links.jsp"></jsp:include>

<style type="text/css">
.btn.btn-app.btn-xss {
	width: 40px;
	font-size: 11px;
	border-radius: 4px;
	padding-bottom: 3px;
	padding-top: 2px;
	line-height: 1.45
}
</style>
<jsp:include page="include/head-script.jsp"></jsp:include>
<script type="text/javascript">
	var CONTEXT_PATH = '<%=context%>';
</script>
</head>
<body>
<div class="navbar navbar-default" id="navbar">
	<script type="text/javascript">
		try{ace.settings.check('navbar' , 'fixed')}catch(e){}
	</script>

	<div class="navbar-container clearfix" id="navbar-container">
		<div class="navbar-header pull-left">
			<a href="#">
                <img src="images/logo_xyb.jpg" alt="" width="189"/>
			</a>
		</div>
        <ul id="top-menu" class="nav nav-tabs pull-left navbar-tabs" role="tablist">
        </ul>
        <div class="navbar-header pull-right">
            <p class="nabar-headerInfor">
            	欢迎您：${user.username} 
            	<a href="#">站点首页</a>
            	<a href="#" id="user-profile-btn"><i class="icon-user"></i>修改密码</a>
            	<a href="logout.do"><i class="icon-off"></i>安全退出</a>
            
            </p>
        </div>
	</div>
</div>
<div class="main-container" id="main-container">
	<div class="main-container-inner">
			<a href="#" id="menu-toggler" class="menu-toggler display">
					<span class="menu-text"></span>
				</a>
		<div class="sidebar" id="sidebar">
			<div class="sidebar-collapse" id="sidebar-collapse">
				<i class="icon-double-angle-left" data-icon1="icon-double-angle-left" data-icon2="icon-double-angle-right"></i>
			</div>

		</div>
		
		<div class="main-content">
			<div class="breadcrumbs" id="breadcrumbs">
				<script type="text/javascript">
					try{ace.settings.check('breadcrumbs' , 'fixed')}catch(e){}
				</script>

				<ul class="breadcrumb" id="breadcrumb">
					
				</ul>
				<div class="nav-search" id="nav-search">
					 <button id="page-content-back" class="btn nav-search-input" style="width:80px;display:none;">
						<i class="icon-reply"></i>
						返回
					 </button>
				<!-- 	 <form class="form-search">
								<span class="input-icon">
									<input type="text" placeholder="Search ..." class="nav-search-input" id="nav-search-input" autocomplete="off" />
									<i class="icon-search nav-search-icon"></i>
								</span>
							</form> -->
				</div>
			</div>
			<div class="page-content" id="page-content">
			</div>	
		</div>
	</div>
</div>
<script type="text/javascript">
	$(function(){
		var menus = ${menuJson};
		var context = CONTEXT_PATH;
		
		var $sidebar = $('#sidebar');

		function renderChildren($parent,children){
			if(children!=null && children.length>0){
				$.each(children,function(){
					var menu = this;
					var $li = $('<li/>');
					$parent.append($li);
					if(menu.children && menu.children.length>0){
						var $a = $('<a href="#" class="dropdown-toggle"/>')
							 		.append($('<i/>').addClass(menu.icon))
							  		.append('<span class="menu-text">' + menu.name + '</span>')
									.append('<b class="arrow icon-angle-down"></b>');
						    $a.bind('click',function(){
						    	$(this).closest('li').toggleClass('active').siblings('li.active').removeClass('active');
						    });
							var $ul = $('<ul class="submenu"/>');
							$li.append($a).append($ul);
							renderChildren($ul,menu.children);
					}else{
						var icon = menu.icon;
						var $a = $('<a href="#"/>').attr('data-url',menu.url)
						  .append($('<i/>').addClass(icon))
						  .append('<span class="menu-text">' + menu.name + '</span>');
						$a.bind('click',function(){
							var url = $(this).attr('data-url');
							if(url && url!='#'){
								if(url.indexOf('/')==0){
									url=url.substr(1);
								}
								loadContentPage(context+url);
							}
							$parent.find('li.active').removeClass('active');
							$(this).closest('li').addClass('active');
							return true;
						});
						$li.append($a);
					}
				});
			}
		}
		function renderTopMenu(){
			var $topMenus = $('#top-menu');
			$.each(menus,function(){
				var menu = this;
				var li = '<li role="presentation" id="top_'+menu.id+'">';
				    li += '<a href="#">';
				if(menu.icon){
					li += ('<i class="green bigger-110 '+menu.icon+'"></i>');
				}
				li += ( this.name + '</a></li>');
				
				var $topMenu = $(li);
				if(menu.children!=null && menu.children.length>0){
					var leftMenu= $('<ul class="nav nav-list left-list" id="left_'+menu.id+'" style="display:none;"/>');
					$sidebar.append(leftMenu);
					renderChildren(leftMenu, menu.children);
				}else{
					$topMenu.addClass('disabled');
				}
				$topMenus.append($topMenu);
			});
			$topMenus.find('li').click(function(){
				var $li = $(this);
				if($li.hasClass('disabled')){
					return;
				}
				$sidebar.find('ul.left-list').hide();
				var id = $li.prop('id');
				var leftId = 'left_'+id.substr(id.indexOf('_')+1);
				$topMenus.find('li.active').removeClass('active');;
				$li.addClass('active');
				$('#'+leftId).show();
				
				var $li2 = $('#'+leftId).find('li:first');
				if($li2)
				{
				$li2.removeClass('active');
				$li2.find('a:first').click();
				var $a = $li2.find('ul a:first');
				if($a.html() != undefined){
					$a.click();
				  }
			    }
				
			});
			$sidebar.append($('#sidebar-collapse'));
			
			////
			$('#sidebar').find('i').each(function(){
				if(!$(this).prop('class')){
					//console.log($(this).prop('class'));
					if($(this).closest('ul').prop('id')){
						$(this).addClass('icon-list');
					}else{
						$(this).addClass('icon-double-angle-right');
					}
				}
			});
			
			var $topFirst = $topMenus.find('li:first');
			if($topFirst.size()>0){
				$topFirst.click();
				//找到对应的左侧第一个菜单
				var topid = $topFirst.attr("id").split("_")[1];
				var $li_left=  $("#left_"+topid+">li").first();
				$("a:first",$li_left).click();
				//$a_left.click();
			}
		}
		
		function changePwd(){
			var validate=function($container) {  
				var $form = $container.find('form');
				var validateOpts={
				         rules: {
			        		 'old-password': {
			        			 required: true,
			        		     minlength: 6
			    		    },
			        		 password: {
			        		     required: true,
			        		     minlength: 6
			    		    },
			    		    password2: {
			    		    	required: true,
			    		    	minlength: 6,
			    		    	equalTo: "#password"
			    		    }
				  		 },
				    	messages: {
			        		 'old-password': {
			        			 required: "请输入旧密码",
				    			 minlength: "密码不能小于6个字符"
			    		    },
				    		 password: {
				    			 required: "请输入密码",
				    			 minlength: "密码不能小于6个字符"
			    		    },
			    		    password2: {
			    		    	required: "请输入确认密码",
			    		    	minlength: "确认密码不能小于6个字符",
			    		    	equalTo: "两次输入密码不一致"
			    		    }
				   		}
				     };
				$form.validate(validateOpts);
				return $form.valid();
		    }
			$.FormDialog({
		        width: 450,
		        height: 350,
		        url:context+'user/change-pwd.do',
		        title: "修改密码",
			 	doOk:function($container){
			 		var isValidate = validate($container);
			 		if(isValidate){
			 			//var postdata = $container.find('form').serialize();
			 			$.request(context+'user/save-pwd.do',{newPassword:$('#password').val(),oldPassword:$('#old-password').val()},function(data){
			 				if(data.success){
			 					$.CloseDialog($container);
			 					showMessager(data.message);
			 				}else{
			 					alert(data.message);
			 				}
			 			});
			 		}	
			 	}
		    });
		}
		
		
		
		function init(){
			renderTopMenu();
			
			try{ace.settings.check('sidebar', 'collapsed')}catch(e){}
			try{ace.settings.check('sidebar', 'fixed')}catch(e){}
			$('#page-content-back').click(function(){
				doGoback();
			});
			$('#user-profile-btn').click(function(){
				changePwd();
			});
		}
		init();
	});
	function showMessager(msg) {
		$.messager.show({
	        title:'提示',
	        msg:msg,
	        timeout:3000,
	        showType:'show'
	    });
	}
</script>	
</body>
</html>