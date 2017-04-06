<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
  String context = request.getContextPath();
  context = context.equals("/")? context : context + "/";
%>
<!-- 积分商城商品发货订单列表-->
<div class="row">
	<div class="col-xs-12">
		<div class="input-group" id="search"> 
			用户名:<input type="text" id="username" class="input-sm">  
			&nbsp;&nbsp;
			手机号:<input type="text" id="mobilePhoneNumber" class="input-sm">  
			&nbsp;&nbsp;
			商品属性：<select id="productType" name="productType">
						<option value="" selected="selected">请选择</option>
						<option value="1">虚拟商品</option>
						<option value="2" >实物</option>
				   </select>
			&nbsp;&nbsp;
			发货状态：<select id="mstate" name="mstate">
						<option value=""  selected="selected">请选择</option>
						<option value="1">未发货</option>
						<option value="2">已发货</option>
				   </select>
			<span class=""> 
				<button id="search_btn" type="button" class="btn btn-purple btn-sm">
					搜索 <i class="icon-search icon-on-right bigger-110"></i>
				</button>
			</span>
			&nbsp;&nbsp;
			<button id="export_btn" type="button" class="btn btn-purple btn-sm">
				导出
				<i class="icon-download icon-on-right bigger-110"></i>
			</button> 
		</div>
		<div id="list"></div>
	</div>
</div>
<link rel="stylesheet" href="<%=context%>3rd/zTree_v3/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="<%=context%>3rd/zTree_v3/js/jquery.ztree.all-3.5.js"></script>
<script type="text/javascript" src="<%=context%>js/filedownload.js"></script>
<script type="text/javascript" src="<%=context%>js/util.js"></script>
<script type="text/javascript">
//校验
function validate($container){
	var $form = $container.find('form');
	var validateOpts = {
			rules:{
				expressCompany:"required",
				expressNo:"required",
				remark:"required" 
			},
			messages:{
				username:"请输入快递公司",
				mobilePhoneNumber:"快递单号", 
				mobilePhoneNumber:"发货备注" 
			}
	};
	$form.validate(validateOpts);
	return $form.valid();
}
	var rootContext = '<%=context%>' +'mallProduct/';
	var $datagrid=$('#list');
	
	function loadData(){
		$datagrid.datagrid( {
	        'checkbox':false, 
			'action':false,
			'doUpdate':function(data){ doUpdate(data.id); },
		    'doDel':function(data){ doDel([data]); },
		    'dataModel':{
		    	 'url': rootContext+'listEMSOrder-data.do',
				 'parameters':getCommJosnParams('search')
		    },
	        'columns': [
				{title:'操作',width: "2%",render:function(record,index){ 
				var fieldUrlStr = "";
					if(1 == record.state){
						fieldUrlStr = fieldUrlStr + '<a href="#" onclick="return doHarvest('+ record.id +','+ record.state +','+ record.productType +');">发货</a>';
					}
				    return fieldUrlStr;
				}},
				{ title:'用户手机号码', width : '2%', dataField:'mobilePhoneNumber'},
				{ title:'用户名', width : '2%', dataField:'username'}, 
	            { title:'用户姓名', width : '3%', dataField:'realName'},
	            { title:'商品名称', width : '4%', dataField:'productName'}, 
	            { title:'商品属性', width : '3%', dataField: 'productTypeSTR'},
	            {
	            	title:'虚拟类型',
	            	width : '3%',
	            	render:function(record,index)
   	 			     {
   	 			        if(record.virtualType == 1){
   	 			        	return "礼金";
   	 			        }else if(record.virtualType == 2){
   	 			      	  return "收益卡";
   	 			        }else if(record.virtualType == 3){
   	 			      	  return "红包";
   	 			        }else{
   	 			        	return "";
   	 			        }
   	 			     }
	            }, 
	            { title:'虚拟值', width : '3%', dataField: 'virtualValue'},
	            { title:'数量', width : '2%', dataField:'number'}, 
	            { title:'收货人', width : '4%', dataField:'harvestMan'},
	            { title:'收货手机号码', width : '4%', dataField:'harvestMobile'},
	            { title:'收货地址', width : '4%', dataField:'harvestAddress'}, 
	            { title:'发货状态', width : '3%', dataField:'stateD'},
	            { title:'快递公司', width : '4%', dataField:'expressCompany'},
	            { title:'快递单号', width : '4%', dataField:'expressNo'},
	            { title:'操作人', width : '4%', dataField:'sysOper'},
	            { title:'发货备注', width : '6%', dataField:'remark'} 
	        ]
		});
	} 
$(function(){	// init

	$('#search_btn').click(function(){
		loadData();
	});
	$(function(){
		renderNavTooltip(['积分商城','积分商城商品发货订单列表']);
		loadData();
    });
	$('.date-picker').datepicker({language: 'zh-CN',autoclose:true}).next().on(ace.click_event, function(){
		$(this).prev().focus();
	});
});

//发货
function doHarvest(mallOrderId,state,productType){
	$.FormDialog({
        width: 750,
        height: 400,
        url:rootContext+'harvestBefor.do?mallOrderId='+mallOrderId+'&productType='+productType,
        title: "积分商城发货",
	 	doOk:function($container){
	 		var isValidate = validate($container);
	 		if(isValidate){
	 			if(window.confirm('确认发货？')){
	 				var postdata = $container.find('form').serialize();
	 				var url = "harvestMallTradeOrder.do"; 
	 				$.request(rootContext+url,postdata,function(data){
	 					$.CloseDialog($container);
	 					if(data.success){
	 						loadData();
	 						alert("更新成功.");
	 					}else{
	 						alert(data.message);
	 					}
	 				});
 	            }	 
	 		}
	 	}
      });
}


$('#export_btn').click(function(){
	var url = rootContext + 'exportMallTradeOrderData.do';
	var valUrl = rootContext + 'listOrder-data.do';
	downloadFileByValHref(url, valUrl, 'search','sp');
});
</script>