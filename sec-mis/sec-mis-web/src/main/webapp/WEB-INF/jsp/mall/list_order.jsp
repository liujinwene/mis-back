<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
  String context = request.getContextPath();
  context = context.equals("/")? context : context + "/";
%>
<!-- 积分商城商品订单列表-->
<div class="row">
	<div class="col-xs-12">
		<div class="input-group" id="search"> 
			兑换时间: <input class="date-picker input-small" id="createdDateStart" type="text" data-date-format="yyyy-mm-dd" name="createdDateStart" />
					<i class="icon-calendar bigger-110"></i>至 
					<input class="date-picker input-small" id="createdDateEnd" type="text" data-date-format="yyyy-mm-dd" name="createdDateEnd" /> 
					<i class="icon-calendar bigger-110"></i> 
			用户名:<input type="text" id="username" class="input-sm">  
			&nbsp;&nbsp;
			手机号:<input type="text" id="mobilePhoneNumber" class="input-sm">  
			&nbsp;&nbsp;
			商品属性：<select id="productType" name="productType">
						<option value="" selected="selected">请选择</option>
						<option value="1">虚拟商品</option>
						<option value="2">实物</option>
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
<link rel="stylesheet"
	href="<%=context%>3rd/zTree_v3/css/zTreeStyle/zTreeStyle.css"
	type="text/css">
<script type="text/javascript"
	src="<%=context%>3rd/zTree_v3/js/jquery.ztree.all-3.5.js"></script>
<script type="text/javascript" src="<%=context%>js/filedownload.js"></script>
<script type="text/javascript" src="<%=context%>js/util.js"></script>
<script type="text/javascript">

	var rootContext = '<%=context%>' +'mallProduct/';
	var $datagrid=$('#list');
	
	function loadData(){
		$datagrid.datagrid( {
	        'checkbox':false, 
			'action':false,
			'doUpdate':function(data){
				doUpdate(data.id);
			},
		    'doDel':function(data){
		    	doDel([data]);
		    },
		    'dataModel':{
		    	 'url': rootContext+'listOrder-data.do',
				 'parameters':getCommJosnParams('search')
		    },
	        'columns': [
				{
					title:'用户手机号码',
					width : '3%',
					dataField:'mobilePhoneNumber'
				},
				{
	            	title:'用户名',
	            	width : '4%',
	            	dataField:'username'
	            }, 
	            {
	            	title:'用户姓名',
	            	width : '3%',
	            	dataField:'realName'
	            },
	            {
					title:'兑换时间',
					width : '7%',
					dataField:'createTime'
				},
	            {
	            	title:'商品名称',
	            	width : '10%',
	            	dataField:'productName'
	            }, 
	            {
	            	title:'商品类型',
	            	width : '3%',
	            	dataField:'typeName'
	            }, 
	            {
	            	title:'商品属性',
	            	width : '3%',
	            	dataField: 'productTypeSTR'
	            }, 
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
	            {
	            	title:'兑换数量',
	            	width : '3%',
	            	dataField:'number'
	            },
	            {
	            	title:'兑换前积分',
	            	width : '3%',
	            	//dataField:'number'
	            	render:function(record,index){
	            		if(record.balance != null && record.balance != ''){
		            		return (record.jifen * record.number) + record.balance;
	            		}else{
							return '';            			
	            		}
	            	}	
	            }, 
	            {
	            	title:'消耗积分',
	            	width : '5%',
	            	//dataField:'jifen'
	           		render:function(record,index){
	            		return  record.jifen * record.discount * record.number;
	            	}	
	            },  
	            {
	            	title:'兑换状态',
	            	width : '3%',
	            	dataField:'stateS'
	            }, 
	        ]
		});
	} 
$(function(){	// init

	$('#search_btn').click(function(){
		loadData();
	});
	$(function(){
		renderNavTooltip(['积分商城','积分商城商品订单列表']);
		loadData();
    });
	$('.date-picker').datepicker({language: 'zh-CN',autoclose:true}).next().on(ace.click_event, function(){
		$(this).prev().focus();
	});
});
 
$('#export_btn').click(function(){
	var url = rootContext + 'exportMallTradeOrderData.do';
	var valUrl = rootContext + 'listOrder-data.do';
	downloadFileByValHref(url, valUrl, 'search','sp');
	//downloadFileByDynamicForm(url,'search','sp');
});
</script>