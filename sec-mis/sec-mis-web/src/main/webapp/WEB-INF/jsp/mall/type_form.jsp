<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 

<div class="row">
	<div class="col-xs-12">
		<div class="tabbable">
			<div class="tab-content"> 
				<div id="form" class="tab-pane in active">
					<form class="form-horizontal" role="form">
						<c:if test="${!empty mallProductType.id }">
							<input type="hidden" name="id" value="${mallProductType.id}">
						</c:if>
						<div class="form-group">
							<label class="col-sm-2 control-label no-padding-right">商品类型 </label>
							<div class="col-sm-8">
								<input type="text" id="typeName" name="typeName" class="col-sm-6" value="${mallProductType.typeName}" maxlength="255"/>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label no-padding-right">属性名称 </label>
							<div class="col-sm-8">
								<input type="text" id="propertyName" name="propertyName" class="col-sm-6" value="${mallProductType.propertyName}" maxlength="255"/>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label no-padding-right">排序 </label>
							<div class="col-sm-8">
								<input type="text" id="orderIndex" name="orderIndex" class="col-sm-6" value="${mallProductType.orderIndex}" maxlength="255"/>
							</div>
						</div>
						
					</form>
				</div>
				
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
$(function(){ 
	$('.date-picker').datepicker({language: 'zh-CN',autoclose:true}).next().on(ace.click_event, function(){
		$(this).prev().focus();
	});
})
</script>
