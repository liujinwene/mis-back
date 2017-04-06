<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="row">
	<div class="col-xs-12">
		<form class="form-horizontal" role="form">
			<input type="hidden" name="menuId" value="${acl.menuId}">
			<input type="hidden" name="id" value="${acl.id}">
			<div class="form-group">
				<label class="col-sm-2 control-label no-padding-right">名称 </label>
				<div class="col-sm-8">
					<input type="text" name="name" class="col-sm-12" value="${acl.name}" />
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label no-padding-right">地址 </label>
				<div class="col-sm-8">
					<input type="text"  name="pattern" class="col-sm-12"  value="${acl.pattern}"/>
				</div>
			</div>
		</form>
	</div>
</div>