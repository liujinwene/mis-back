<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
  String context = request.getContextPath();
  context = context.equals("/")? context : context + "/";
%>
<div class="row">
	<div class="col-xs-12">
		<div class="tabbable">
			<ul class="nav nav-tabs" id="myTab">
				<li class="active">
					<a data-toggle="tab" href="#form">
						<i class="green icon-user bigger-110"></i>
						用户
					</a>
				</li>
				<c:if test="${!empty user.id }">
					<li class="">
						<a data-toggle="tab" href="#user-role">
							<i class="green icon-group bigger-110"></i>
							用户角色
						</a>
					</li>
				</c:if>
				
			</ul>
			<div class="tab-content">
				<div id="form" class="tab-pane in active">
					<form class="form-horizontal" role="form">
						<input type="hidden" name="id" value="${user.id}">
						<%-- --%>
						<input type="hidden" name="accountNonExpired" value="1">
						<input type="hidden" name="accountNonLocked" value="1">
						<input type="hidden" name="credentialsNonExpired" value="1">
						
						<div class="form-group">
							<label class="col-sm-2 control-label no-padding-right">用户名 </label>
							<div class="col-sm-8">
								<input type="text" name="username" class="col-sm-6" value="${user.username}" <c:if test="${!empty user.username }">readonly=""</c:if> />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label no-padding-right">名字</label>
							<div class="col-sm-8">
								<input type="text" id="realname" name="realname" class="col-sm-6"  value="${user.realname}"/>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label no-padding-right">密码</label>
							<div class="col-sm-8">
								<input type="password" id="password" name="password" class="col-sm-6"  value=""/>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label no-padding-right">确认密码</label>
							<div class="col-sm-8">
								<input type="password" id="password2" name="password2" class="col-sm-6"  value=""/>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label no-padding-right">账号启用</label>
							<div class="col-sm-8">
							<label>
								<input type="checkbox" class="ace ace-switch ace-switch-2" name="enabled"<c:if test="${user.enabled or empty user.id}">checked="checked"</c:if>/>
								<span class="lbl"></span>
							</label>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label no-padding-right">手机</label>
							<div class="col-sm-8">
								<input type="text"  name="mobile" class="col-sm-6"  value="${user.mobile}"/>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label no-padding-right">email</label>
							<div class="col-sm-8">
								<input type="text"  name="email" class="col-sm-6"  value="${user.email}"/>
							</div>
						</div>
					</form>
				</div>
				<c:if test="${!empty user.id }">
					<div id="user-role" class="tab-pane">
						<ul id="role-tree" class="ztree"></ul>
					</div>
				</c:if>
			</div>
		</div>
	</div>
</div>
