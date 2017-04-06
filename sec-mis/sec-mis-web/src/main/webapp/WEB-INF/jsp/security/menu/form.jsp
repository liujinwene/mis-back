<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="row">
	<div class="col-xs-12">
		<form class="form-horizontal" role="form">
			<input type="hidden" name="parentId" value="${menu.parentId}">
			<input type="hidden" name="id" value="${menu.id}">
			<div class="form-group">
				<label class="col-sm-2 control-label no-padding-right">名称 </label>
				<div class="col-sm-8">
					<input type="text" name="name" class="col-sm-12" value="${menu.name}" />
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label no-padding-right">地址 </label>
				<div class="col-sm-8">
					<input type="text"  name="location" class="col-sm-12"  value="${menu.location}"/>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label no-padding-right">icon</label>
				<div class="col-sm-8">
					<input type="text"  name="icon" class="col-sm-12"  value="${menu.icon}"/>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label no-padding-right">是否显示</label>
				<div class="col-sm-8">
					<label>
						<input type="checkbox" class="ace ace-switch ace-switch-2" name="display"<c:if test="${menu.display or empty menu.id}">checked="checked"</c:if>/>
						<span class="lbl"></span>
					</label>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label no-padding-right">显示序号</label>
				<div class="col-sm-8">
					<input type="text" name="orderNum" class="col-sm-6" value="${menu.orderNum}"/>
				</div>
			</div>
		</form>
	</div>
</div>