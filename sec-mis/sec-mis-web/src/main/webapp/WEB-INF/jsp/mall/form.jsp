<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%
  	String context = request.getContextPath();
	context = context.equals("/")? context : context + "/";
	request.setCharacterEncoding("UTF-8");
%> 
 <style>
.upload_file2{position:absolute;left:0;top:0;font-size:90px;margin:-20px 0 0 -1100px;opacity:0;filter:alpha(opacity=0);cursor:pointer;}
.upload_file3{position: absolute;left: -999px;top: -999px;opacity:0;filter:alpha(opacity=0);}
.upload_a {margin-left: 20px;cursor:pointer;}
.col-sm-8 label {margin-right:13px; }
.col-sm-8 label input {margin-left:5px; }

</style>
<div class="row">
	<div class="col-xs-12">
		<div class="tabbable">
			<div class="tab-content"> 
			
				<div id="form" class="tab-pane in active">
					<form class="form-horizontal" role="form">
						<c:if test="${!empty mallProduct.id }">
							<input type="hidden" name="id" value="${mallProduct.id}">
						</c:if>
						<div class="form-group">
							<label class="col-sm-2 control-label no-padding-right">商品编号 </label>
							<div class="col-sm-8">
								<input type="text" id="artNo" name="artNo" class="col-sm-6" value="${mallProduct.artNo}" maxlength="255"/>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label no-padding-right">商品名称 </label>
							<div class="col-sm-8">
								<input type="text" id="productName" name="productName" class="col-sm-6" value="${mallProduct.productName}" maxlength="255"/>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label no-padding-right">商品类型 </label>
							<div class="col-sm-8">
								<select id="typeId" name="typeId">
									<option value="-1">--请选择--</option>
									<c:if test="${!empty mallProductTypes}">
										<c:forEach var="item" items="${mallProductTypes}">
											<c:choose>
												<c:when test="${!empty mallProduct.typeId }">
													<c:if test="${mallProduct.typeId == item.id }">
			                                     		<option value="${item.id}" selected="selected">${item.typeName}</option>
													</c:if>
													<c:if test="${mallProduct.typeId != item.id }">
			                                     		<option value="${item.id}" >${item.typeName}</option>
													</c:if>
												</c:when>
												<c:otherwise>
													<option value="${item.id}">${item.typeName}</option>
												</c:otherwise>
											</c:choose>
                                  		</c:forEach>
									</c:if>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label no-padding-right">库存</label>
							<div class="col-sm-8">
								<input type="text" id="number" name="number" class="col-sm-6" value="${mallProduct.number}" maxlength="255"/>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label no-padding-right">商品价格</label>
							<div class="col-sm-8">
								<input type="text" id="marketPrice" name="marketPrice" class="col-sm-6" value="${mallProduct.marketPrice}" maxlength="255"/>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label no-padding-right">商品兑换积分值</label>
							<div class="col-sm-8">
								<input type="text" id="jifen" name="jifen" class="col-sm-6" value="${mallProduct.jifen}" maxlength="255"/>
							</div>
						</div> 
						<div class="form-group">
							<label class="col-sm-2 control-label no-padding-right">积分折扣</label>
							<div class="col-sm-8">
								<input type="text" id="discount" name="discount" class="col-sm-6" value="<fmt:formatNumber value="${(mallProduct.discount * 100)}" pattern="0"/>" maxlength="255"/>折 (1-100)
							</div>
						</div> 
						<div class="form-group">
							<label class="col-sm-2 control-label no-padding-right">交易数量</label>
							<div class="col-sm-8">
								<input type="text" id="tradeNum" name="tradeNum" class="col-sm-6" value="${mallProduct.tradeNum}" maxlength="255"/>
							</div>
						</div> 
						<div class="form-group">
							<label class="col-sm-2 control-label no-padding-right">置顶</label>
							<div class="col-sm-8">
								<c:if test="${empty mallProduct.top }">
									<label>不置顶<input type="radio" value="0" name="top" checked="checked"></label>
									<label>置顶<input type="radio" value="1" name="top" ></label>
								</c:if>
								<c:if test="${!empty mallProduct.top }">
									<c:choose>
										<c:when test="${mallProduct.top == 1 }">
											<label>不置顶<input type="radio" value="0" name="top" ></label>
											<label>置顶<input type="radio" value="1" name="top" checked="checked"></label>
										</c:when>
										<c:otherwise>
											<label>不置顶<input type="radio" value="0" name="top" checked="checked"></label>
											<label>置顶<input type="radio" value="1" name="top"></label>
										</c:otherwise>
									</c:choose>
								</c:if>
							</div>
						</div> 
						<div class="form-group">
							<label class="col-sm-2 control-label no-padding-right">新品上架</label>
							<div class="col-sm-8">
								<c:if test="${empty mallProduct.newType }">
									<label>新品<input type="radio" value="1" name="newType" checked="checked"></label>
									<label>非新品<input type="radio" value="0" name="newType" ></label>
								</c:if>
								<c:if test="${!empty mallProduct.newType }">
									<c:choose>
										<c:when test="${mallProduct.newType == 1 }">
											<label>新品<input type="radio" value="1" name="newType" checked="checked"></label>
											<label>非新品<input type="radio" value="0" name="newType" ></label>
										</c:when>
										<c:otherwise>
											<label>新品<input type="radio" value="1" name="newType" ></label>
											<label>非新品<input type="radio" value="0" name="newType"checked="checked"></label>
										</c:otherwise>
									</c:choose>
								</c:if>
							</div>
						</div> 
						<div class="form-group">
							<label class="col-sm-2 control-label no-padding-right">商品是否有效</label>
							<div class="col-sm-8">
								<c:if test="${empty mallProduct.state }">
									<label><input type="radio" value="1" name="state" checked="checked">有效</label>
									<label><input type="radio" value="0" name="state" >无效</label>
								</c:if>
								<c:if test="${!empty mallProduct.state }">
									<c:choose>
										<c:when test="${mallProduct.state == 1 }">
											<label>有效<input type="radio" value="1" name="state" checked="checked"></label>
											<label>无效<input type="radio" value="0" name="state" ></label>
										</c:when>
										<c:otherwise>
											<label>有效<input type="radio" value="1" name="state"></label>
											<label>无效<input type="radio" value="0" name="state" checked="checked"></label>
										</c:otherwise>
									</c:choose>
								</c:if>
							</div>
						</div> 
						<div class="form-group">
							<label class="col-sm-2 control-label no-padding-right">商品限制</label>
							<div class="col-sm-8">
								<c:if test="${empty mallProduct.confine }">
									<label>限制一个<input type="radio" value="1" name="confine" ></label>
									<label>不限制<input type="radio" value="0" name="confine" checked="checked"></label>
								</c:if>
								<c:if test="${!empty mallProduct.confine }">
									<c:choose>
										<c:when test="${mallProduct.confine == 1 }">
											<label>限制一个<input type="radio" value="1" name="confine" checked="checked"></label>
											<label>不限制<input type="radio" value="0" name="confine" ></label>
										</c:when>
										<c:otherwise>
											<label>限制一个<input type="radio" value="1" name="confine"></label>
											<label>不限制<input type="radio" value="0" name="confine" checked="checked"></label>
										</c:otherwise>
									</c:choose>
								</c:if>
							</div>
						</div> 
						<div class="form-group">
							<label class="col-sm-2 control-label no-padding-right">物品类型</label>
							<div class="col-sm-8">
								<c:if test="${empty mallProduct.productType }">
									<label>虚拟物品<input type="radio" value="1" name="productType" checked="checked" onclick="virtualShow();"></label>
									<label>实物<input type="radio" value="2" name="productType" onclick="virtualShow();"></label>
								</c:if>
								<c:if test="${!empty mallProduct.productType }">
									<c:choose>
										<c:when test="${mallProduct.productType == 1 }">
											<label>虚拟物品<input type="radio" value="1" name="productType" checked="checked" onclick="virtualShow();"></label>
											<label>实物<input type="radio" value="2" name="productType" onclick="virtualHide();"></label>
										</c:when>
										<c:when test="${mallProduct.productType == 2 }">
											<label>虚拟物品<input type="radio" value="1" name="productType" onclick="virtualShow();"></label>
											<label>实物<input type="radio" value="2" name="productType" checked="checked" onclick="virtualHide();"></label>
										</c:when>
										<c:otherwise>
											<label>虚拟物品<input type="radio" value="1" name="productType" checked="checked" onclick="virtualShow();"></label>
											<label>实物<input type="radio" value="2" name="productType" onclick="virtualHide();"></label>
										</c:otherwise>
									</c:choose>
								</c:if>
								<c:choose>
										<c:when test="${mallProduct.productType == 1 }">
											<div id="virtual" style="border: 1px solid #666666;">
										</c:when> 
										<c:otherwise>
											<div id="virtual" style="display: none;" >
										</c:otherwise>
									</c:choose>
									<div class="form-group" >
										<label class="col-sm-2 control-label no-padding-right">虚拟类型</label>
										<div class="col-sm-8">
											<c:choose>
												<c:when test="${mallProduct.virtualType == 0 }">
													<label>其他<input type="radio" value="0" name="virtualType"  checked="checked"></label>
													<label>礼金<input type="radio" value="1" name="virtualType" ></label>
													<label>收益卡<input type="radio" value="2" name="virtualType" ></label>
													<label>红包<input type="radio" value="3" name="virtualType" ></label>
												</c:when>
												<c:when test="${mallProduct.virtualType == 1 }">
													<label>其他<input type="radio" value="0" name="virtualType" ></label>
													<label>礼金<input type="radio" value="1" name="virtualType" checked="checked"></label>
													<label>收益卡<input type="radio" value="2" name="virtualType" ></label>
													<label>红包<input type="radio" value="3" name="virtualType" ></label>
												</c:when>
												<c:when test="${mallProduct.virtualType == 2 }">
													<label>其他<input type="radio" value="0" name="virtualType" ></label>
													<label>礼金<input type="radio" value="1" name="virtualType" ></label>
													<label>收益卡<input type="radio" value="2" name="virtualType"checked="checked" ></label>
													<label>红包<input type="radio" value="3" name="virtualType" ></label>
												</c:when>
												<c:when test="${mallProduct.virtualType == 3 }">
													<label>其他<input type="radio" value="0" name="virtualType" ></label>
													<label>礼金<input type="radio" value="1" name="virtualType" ></label>
													<label>收益卡<input type="radio" value="2" name="virtualType" ></label>
													<label>红包<input type="radio" value="3" name="virtualType" checked="checked"></label>
												</c:when>
												<c:otherwise>
													<label>其他<input type="radio" value="0" name="virtualType" checked="checked"></label>
													<label>礼金<input type="radio" value="1" name="virtualType" ></label>
													<label>收益卡<input type="radio" value="2" name="virtualType" ></label>
													<label>红包<input type="radio" value="3" name="virtualType" ></label>
												</c:otherwise>
											</c:choose>
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label no-padding-right">虚拟值</label>
										<div class="col-sm-8">
											<input type="text" id="virtualValue" name="virtualValue" class="col-sm-6" value="${mallProduct.virtualValue}" maxlength="255"/>
											元/倍
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label no-padding-right"></label>
											<span style="color: red">(注意单位：  收益卡~倍  红包~元  礼金~元)</span>
									</div>
								</div>
							</div>
						</div>  
						<div class="form-group">
							<label class="col-sm-2 control-label no-padding-right">商品图片</label>
							<div class="col-sm-8">
								<img id="imgUrl2" style="height: 150px;width: 450px;" src="${mallProduct.imgUrl}"/>
								<input value="${mallProduct.imgUrl}" id="imgUrl" name="imgUrl" type="hidden" onfocus="this.blur();" >
							</div>
							<div id="div1">
									<button id="loadBtn" class="btn"><i class="icon-cloud-upload bigger-120"></i>上传文件</button>
									<input id="fileupload" type="file" class="upload_file3" name="uploadfile"   data-url="<%=context %>mallProduct/uploadimgURL.do">				
							</div>
							<br>
							<div id="div2">图片大小限制2M</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label no-padding-right">详情介绍</label>
							<div style="padding-left: 30px;">
								<form name="example" method="post" action="demo.jsp">
									<textarea id="content1" name="detail" cols="100" rows="8" style="width:700px;height:200px;visibility:hidden;">${mallProduct.detail}</textarea>
								</form>
							</div>
						</div> 
					</form>
				</div> 
			</div>
		</div>
	</div>
</div>   
<script>
	function kedit(keid) {
		var keditor = KindEditor.create('#' + keid,
		{
			width : "80%", //编辑器的宽度为70% 
			filterMode : false, //不会过滤HTML代码
			resizeMode : 1,//编辑器只能调整高度 
			imageUploadJson : '<%=context%>3rd/kindeditor/upload_json.jsp',
			fileManagerJson : '<%=context%>3rd/kindeditor/upload_json.jsp',
			allowFileManager : true,
			afterCreate : function() {
				var self = this;
				KindEditor.ctrl(document, 13, function() {
					self.sync();
					document.forms['example'].submit();
				});
				KindEditor.ctrl(self.edit.doc, 13, function() {
					self.sync();
					document.forms['example'].submit();
				});
			},
			items : [ 'source', '|', 'fullscreen', 'undo', 'redo', 'print',
						'cut', 'copy', 'paste', 'plainpaste', 'wordpaste', '|',
						'justifyleft', 'justifycenter', 'justifyright',
						'justifyfull', 'insertorderedlist',
						'insertunorderedlist', 'indent', 'outdent',
						'subscript', 'superscript', '|', 'selectall', '-',
						'title', 'fontname', 'fontsize', '|', 'textcolor',
						'bgcolor', 'bold', 'italic', 'underline',
						'strikethrough', 'removeformat', '|', 'image',
						'advtable', 'hr', 'emoticons', 'link', 'unlink', '|',
						'about' ],
			afterBlur : function() {
				this.sync();
			},//和DWZ 的 Ajax onsubmit 冲突,提交表单时 编辑器失去焦点执行填充内容
			newlineTag : "br"
		});
	}
</script>
<SCRIPT type="text/javascript">
$(function (){
 	kedit("content1");
});
</SCRIPT>
<script type="text/javascript">
function virtualShow(){
	$("#virtual").show();
}
function virtualHide(){
	$("#virtual").hide();
}
 
$(function(){
	
	$('.date-picker').datepicker({language: 'zh-CN',autoclose:true}).next().on(ace.click_event, function(){
		$(this).prev().focus();
	});
	$('#loadBtn').click(function(){
		$('#fileupload').click();
		return false;
	});
	$('#fileupload').fileupload({
		dataType:"json",
		singleFileUploads: false,
		maxNumberOfFiles: 1,
		acceptFileTypes:/(\.|\/)(bmp|jpg|gif|png|jpeg)$/i,
		messages:{
			acceptFileTypes:'只能上传bmp|jpg|gif|png|jpeg 类型文件',
			maxNumberOfFiles: '只能选择一个文件'
		}, 
		done: function (e, data) {
			var result = data.result; 
			if(result.success){
				var imgUrl = result.object;
				$("#imgUrl2").attr("src",imgUrl);
				$("#imgUrl").attr("value",imgUrl);
				alert("上传成功");
	    	}else{ 
				alert(result.message);
	    	}
	    }
	});
})
</script>