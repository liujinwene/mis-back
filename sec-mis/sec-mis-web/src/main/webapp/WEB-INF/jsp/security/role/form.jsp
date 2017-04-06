<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="row">
	<div class="col-xs-12">
		<div class="tabbable">
			<ul class="nav nav-tabs" id="myTab">
				<li class="active">
					<a data-toggle="tab" href="#role-form">
						<i class="green icon-group bigger-110"></i>
						角色
					</a>
				</li>
				<c:if test="${!empty role.id }">
					<li class="">
						<a data-toggle="tab" href="#role-menu">
							<i class="green icon-key bigger-110"></i>
							角色权限
						</a>
					</li>
				</c:if>
				
			</ul>
			<div class="tab-content">
				<div id="role-form" class="tab-pane in active">
					<form class="form-horizontal" role="form">
						<input type="hidden" name="id" value="${role.id}">
						<div class="form-group">
							<label class="col-sm-2 control-label no-padding-right">代码 </label>
							<div class="col-sm-8">
								<input type="text" name="code" class="col-sm-8" value="${role.code}" <c:if test="${!empty role.code }">readonly=""</c:if> />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label no-padding-right">说明 </label>
							<div class="col-sm-8">
								<input type="text"  name="name" class="col-sm-8"  value="${role.name}"/>
							</div>
						</div>
					</form>
				</div>
				<c:if test="${!empty role.id }">
				<div id="role-menu" class="tab-pane">
					<ul id="menu-tree" class="ztree"></ul>
				</div>
				</c:if>
			</div>
		</div>
	</div>
</div>