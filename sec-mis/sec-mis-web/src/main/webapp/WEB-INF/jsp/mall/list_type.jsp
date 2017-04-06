<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
  String context = request.getContextPath();
  context = context.equals("/")? context : context + "/";
%>
<!-- 积分商城商品类型列表-->
<div class="row">
	<div class="col-xs-12">
		<div class="input-group" id="search"> 
			商品类型:<input type="text" id="typeName" class="input-sm">  
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
				typeName:"required",
				propertyName:"required",
				orderIndex:{
					 required:true,
					 digits:true
				}
			},
			messages:{
				typeName:"请输入商品类型名称",
				propertyName:"请输入商品属性名称",
				orderIndex:{
					required:"请输入排序",
					required:"请输入正数"
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
			'action':false,
			'showOnePageBar':false,
			'doUpdate':function(data){
				doUpdate(data.id);
			},
		    'doDel':function(data){
		    	doDel([data]);
		    },
		    'dataModel':{
		    	 'url': rootContext+'listType-data.do',
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
				 type:'REFRESH',
				 click:function(){
					loadData();
				 }
			  }
		    ],
	        'columns': [
				{title:'操作',width : '4%',render:function(record,index){
					var fieldUrlStr = '<a href="#" onclick="return doUpdate('+ record.id +');">修改 </a>'; 
					var html = 	'<div class="visible-md visible-lg hidden-sm hidden-xs action-buttons">'
					+'	<a class="green update-btn" href="#" onclick="return doUpdate('+ record.id +');">'
					+'		<i class="icon-pencil bigger-130"></i>'
					+'	</a>'
					+'</div>';
				    return html;
				}},        
				{
					title:'创建时间',
					//width : '10%',
					dataField:'createTime'
				}, 
				{
					title:'最后修改时间',
					//width : '5%',
					dataField:'modifyTime'
				}, 
	            {
	            	title:'商品类型',
	            	//width : '5%',
	            	dataField:'typeName'
	            },  
	            {
	            	title:'属性名称',
	            	//width : '5%',
	            	dataField:'propertyName'
	            },
	            {
	            	title:'排序',
	            	//width : '5%',
	            	dataField:'orderIndex'
	            } 
	        ]
		});
	} 
$(function(){	// init

	$('#search_btn').click(function(){
		loadData();
	});
	$(function(){
		renderNavTooltip(['积分商城','积分商城商品类型列表']);
		loadData();
    });
	$('.date-picker').datepicker({language: 'zh-CN',autoclose:true}).next().on(ace.click_event, function(){
		$(this).prev().focus();
	});
});

//展示待修改的商品类型的信息.
function doUpdate(id){ 
	  $.FormDialog({
        width: 750,
        height: 700,
        url:rootContext+'updateBeforMallProductType.do?id='+id,
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
        url:rootContext+'addBeforMallProductType.do',
        title: "添加商品类型",
	 	doOk:function($container){
	 		var isValidate = validate($container);
	 		if(isValidate){
	 			doSave($container,true);
	 		}
	 	}
      });
}

//更新商品类型的信息.
function doSave($container,isAdd){
	var postdata = $container.find('form').serialize();
	var url = "";
	if(isAdd){
		url = "addMallProductType.do";
	}else{
		url = "upMallProductType.do";
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
	var url = rootContext+'exportMallProductTypeData.do';
	downloadFileByDynamicForm(url,'search','sp');
});
</script>