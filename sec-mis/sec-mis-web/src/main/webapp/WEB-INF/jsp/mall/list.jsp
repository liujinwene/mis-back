<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
  String context = request.getContextPath();
  context = context.equals("/")? context : context + "/";
%>
<!-- 积分商城商品列表-->
<div class="row">
	<div class="col-xs-12">
		<div class="input-group" id="search"> 
			商品名称:<input type="text" id="productName" class="input-sm">  
			&nbsp;&nbsp;
			商品编号:<input type="text" id="artNo" class="input-sm">  
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
//校验
function validate($container){
	var $form = $container.find('form');
	var validateOpts = {
			rules:{
				artNo:"required",
				productName:"required",
				typeId:{
					 required:true,
					 min:0
				},
				number:{
					 required:true,
					 min:0
				},
				marketPrice:{
					 required:true,
					 min:0
				},
				jifen:{
					 required:true,
					 min:0
				},  
				discount:{
					 required:true,
					 min:1,
					 max:100
				},  
				tradeNum:{
					 required:true
				},  
				virtualValue:{
					 required:true,
					 min:0
				},  
				detail:"required",
				imgUrl:"required"
			},
			messages:{
				artNo:"请输入商品编号",
				productName:"请输入商品名称",
				typeId:{
					required:"请选择商品类型",
					min:"请选择商品类型"
				},
				number:{
					required:"请填写商品库存",
					min:"商品数量不能小于零"
				},
				marketPrice:{
					required:"请填写商品价格",
					min:"商品价格不能小于零"
				},
				jifen:{
					required:"请填写商品兑换积分",
					min:"商品兑换积分不能小于零"
				}, 
				discount:{
					required:"请填写商品折扣"
				}, 
				imgUrl:{
					required:"请上传图片"
				}, 
				detail:{
					required:"请填写商品介绍详情"
				}
			}
	};
	$form.validate(validateOpts);
	return $form.valid();
}

	var rootContext = '<%=context%>' +'mallProduct/';
	var $datagrid=$('#list');
	
	function loadData(){
		$datagrid.datagrid( {
	        'checkbox':true, 
			'action':true,
			'showOnePageBar':false,
			'doUpdate':function(data){
				doUpdate(data.id);
			},
		  	'doDel':function(data){
		    	doDel(data.id);
		    },
		    'dataModel':{
		    	 'url': rootContext+'list-data.do',
				 'parameters':getCommJosnParams('search')
		    },
		    toolbar:
				[
			      {
			    	  type:'ADD',
			    	  click:function(){doAdd(this);}
			      },
			      {
			    	  type:'MOD',
			    	  disabled:true,
						click:function(){
							var datas = $datagrid.selectedRowDatas();
							if(datas && datas.length>0){
								doUpdate(datas[0].id)
							}
						}
			      },
			      {
			    	  type:'DEL',
			    	  disabled:true,
						click:function(){
							var datas = $datagrid.selectedRowDatas();
							if(datas && datas.length>0){
								doDel(datas[0].id);
							}
						}
			      },
				  {
					 type:'REFRESH',
					 click:function(){
						loadData();
					 }
				  }
			    ],
	        'columns': [ 
				{
					title:'创建时间',
					//width : '5%',
					dataField:'createTime'
				}, 
	            {
	            	title:'商品编号',
	            	//width : '3%',
	            	dataField:'artNo'
	            },
	            {
	            	title:'商品名称',
	            	//width : '3%',
	            	dataField:'productName'
	            },
	            {
	            	title:'商品类型',
	            	//width : '3%',
	            	dataField:'typeName'
	            }, 
	            {
	            	title:'虚拟类型',
	            	//width : '3%',
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
	            {
	            	title:'商品数量',
	            	//width : '3%',
	            	dataField:'number'
	            }, 
	            {
	            	title:'价格',
	            	//width : '3%',
	            	//dataField:'marketPrice'
	            	render:function(record,index)
   	 			     {
   	 			         return handleDataFormat(record.marketPrice);
   	 			     }
	            }, 
	            {
	            	title:'积分',
	            	//width : '3%',
	            	dataField:'jifen'
	            }, 
	            {
	            	title:'折扣',
	            	//width : '3%',
	            	render:function(record,index)
   	 			    {
   	 			        return handleDataFormat(record.discount * 100);
   	 			    }
	            }, 
	            {
	            	title:'交易数量',
	            	//width : '3%',
	            	dataField:'tradeNum'
	            }, 
	            {
	            	title:'新品上架',
	            	//width : '3%',
	            	dataField:'newTypeSTR'
	            }, 
	            {
	            	title:'商品属性',
	            	//width : '3%',
	            	dataField:'productType'
	            }, 
	            {
	            	title:'商品是否有效',
	            	//width : '5%',
	            	dataField:'stateSTR' 
	            }, 
	            {
	            	title:'商品图片',
	            	//width : '5%',
	            	//dataField:'imgUrl'
	            	render:function(record,index)
   	 			     {
   	 			         return  "<img style=\"height: 80px;width: 150px;\" src=\""+record.imgUrl+"\"/>";;
   	 			     }
	            }
	        ]
		});
	} 
$(function(){	// init

	$('#search_btn').click(function(){
		loadData();
	});
	$(function(){
		renderNavTooltip(['积分商城','积分商城商品列表']);
		loadData();
    });
	$('.date-picker').datepicker({language: 'zh-CN',autoclose:true}).next().on(ace.click_event, function(){
		$(this).prev().focus();
	}); 
});

//展示待修改的商品的信息.
function doUpdate(id){ 
	  	
	  $.FormDialog({
        width: 800,
        height: 900,
        url:rootContext+'updateBeforMallProduct.do?id='+id,
        title: "修改商品类型信息",
	 	doOk:function($container){  
	 		var isValidate = validate($container);
	 		if(isValidate){
	 			doSave($container,false);
	 		}
	 	}
      });
	 
}

function doAdd(){
	$.FormDialog({
        width: 750,
        height: 700,
        url:rootContext+'addBeforMallProduct.do',
        title: "添加商品",
	 	doOk:function($container){
	 		var isValidate = validate($container);
	 		if(isValidate){
	 			doSave($container,true);
	 		}
	 	}
      });
	 
}
 

//修改商品状态
function doDel(mallProductId){
   	  $.MessageDialog(MessageDialog.CONFIRM,{message:'确认删除商品？',doOk:function($container){
		$.request(rootContext+'delMallProduct.do',{mallProductId:mallProductId},function(data){
			if(data.success){
				$.CloseDialog($container);
				loadData();		
				alert("删除成功.");
			}else{
				alert(data.message);
			}
		});
	  }}); 
}


//更新商品的信息.
function doSave($container,isAdd){
	var postdata = $container.find('form').serialize();
	var url = "";
	if(isAdd){
		url = "addMallProduct.do";
	}else{
		url = "upMallProduct.do";
	}
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

$('#export_btn').click(function(){
	var url = rootContext + 'exportMallProductData.do';
	var valUrl = rootContext + 'list-data.do';
	downloadFileByValHref(url, valUrl, 'search','sp');
	//downloadFileByDynamicForm(url,'search','sp');
});
</script>
