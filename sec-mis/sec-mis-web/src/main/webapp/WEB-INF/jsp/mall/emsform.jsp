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
						<input type="hidden" id="id" name="id" value="${mallOrderId }" class="col-sm-6"  maxlength="255"/>
						<input type="hidden" id="productType" name="productType" value="${productType }" class="col-sm-6"  maxlength="255"/>
				<c:choose>
					<c:when test="${productType == 2 }">
						<div class="form-group">
							<label class="col-sm-2 control-label no-padding-right">快递公司 </label>
							<div class="col-sm-8">
								<input type="text" id="expressCompany" name="expressCompany" class="col-sm-6"  maxlength="255"/>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label no-padding-right">快递单号 </label>
							<div class="col-sm-8">
								<input type="text" id="expressNo" name="expressNo" class="col-sm-6" maxlength="255"/>
							</div>
						</div>
					</c:when>
					<c:otherwise>
						<div class="form-group">
							<div class="col-sm-8">
								<input type="hidden" value="非实物" id="expressCompany" name="expressCompany" class="col-sm-6"  maxlength="255"/>
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-8">
								<input type="hidden" value="非实物" id="expressNo" name="expressNo" class="col-sm-6" maxlength="255"/>
							</div>
						</div>
					</c:otherwise>
				</c:choose>
						<div class="form-group">
							<label class="col-sm-2 control-label no-padding-right">发货备注 </label>
							<div class="col-sm-8">
								<textarea rows="6" cols="6" id="remark" name="remark" class="col-sm-6"></textarea>
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
	$('.date-picker').datetimepicker({language: 'zh-CN',autoclose:true}).next().on(ace.click_event, function(){
		$(this).prev().focus();
	});
})
</script>
