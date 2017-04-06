<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- <div id="toolbar" class="col" style="padding-bottom: 4px">
</div> -->
<%
  String context = request.getContextPath();
  context = context.equals("/")? context : context + "/";
  %>
<div class="row">
	<div class="col-xs-12">
		<div class="input-group" id="search">
			代码:<input type="text" id="code" class="input-sm">
			名称:<input type="text" id="name" class="input-sm">
			
			<span class="">
				<button id="search_btn" type="button" class="btn btn-purple btn-sm">
					搜索
					<i class="icon-search icon-on-right bigger-110"></i>
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
<script type="text/javascript">
$(function(){
	var roleContext = '<%=context%>' +'role/';
	var $datagrid=$('#list');
	jQuery.validator.addMethod('testRole', function(value,element) {   
	    return this.optional(element) || (value.length>5 && value.indexOf('ROLE_')==0);   
	}, '角色代码必须以ROLE_开头');   
	
	$('#search_btn').click(function(){
		loadData();
	});
	
	function  validate($container) {  
		var $form = $container.find('form');
		$form.validate({
	         rules: {
	    		code: {
	    			required:true,
	    			testRole:true
	    		},
	  		 	name: 'required'
	  		 },
	    	messages: {
	    		code: '请输入代码,角色代码必须以ROLE_开头',
	    		name: '请输入说明'
	   		}
	     });
		return $form.valid();
    }

	function doAdd(){
		$.FormDialog({
	        width: 550,
	        height: 300,
	        url:roleContext+'add.do',
	        title: "新增角色",
		 	doOk:function($container){
		 		var isValidate = validate($container);
		 		if(isValidate){
		 			doSave($container,true);
		 		}
		 	}	
		});
	}
	
	function doUpdate(id){
		var initMenuTree=function(){
			var setting = {
				data: {
					simpleData: {
						enable: true,
						idKey: "id",
						pIdKey: "parentId"
					}
				},
				check: {enable: true}
			};	
			var treeUrl = roleContext+'menu.do?_rand='+Math.random();	
			$.request(treeUrl, {roleId: id}, function(data){	
				var $tree = $("#menu-tree");
				if(data && data.length>0){
					zTreeObj = $.fn.zTree.init($tree, setting, data);
					zTreeObj.expandAll(true);
				}else{
					$tree.html('<span style="color:red;">数据加载失败！</span>');
				}	
			});
		}
		
		$.FormDialog({
	        width: 550,
	        height: 500,
	        url:roleContext+'update.do?roId='+id,
	        title: "编辑角色",
		 	doOk:function($container){
		 		var isValidate = validate($container);
		 		if(isValidate){
		 			doSave($container,false);
		 		}	
		 	},open: function( event, ui ) {
		 		initMenuTree();
		 	}	
	    });
	}
	
	function doDel(datas){
		$.MessageDialog(MessageDialog.CONFIRM,{message:'确认是否删除？',doOk:function($container){
			var ids=[];
			$.each(datas,function(){
				ids.push(this.id);
			});
			$.request(roleContext+'delete.do',{roleIds:ids},function(data){
				if(data){
					$.CloseDialog($container);
					loadData();						
				}
			});
		}});
	}
	
	function doSave($container,isAdd){
		var postdata = $container.find('form').serialize();
		var menus = new Array();
		var acls =  new Array();
		if($("#menu-tree").size()>0){
			var zTreeObj = $.fn.zTree.getZTreeObj("menu-tree");
			var nodes = zTreeObj.getCheckedNodes(true);
			$.each(nodes, function(i,node){
				if(node.menu){
					menus.push(node.id);
				}else{
					var aclId = node.id;
					aclId = aclId.substr(aclId.indexOf("_")+1);
					acls.push(aclId)
				}
			});
		}
		if(menus.length>0){
			postdata += ("&"+jQuery.param({menuIds:menus}))
		}
		if(acls.length>0){
			postdata += ("&"+jQuery.param({aclIds:acls}))
		}
		
		$.request(roleContext+'save.do',postdata,function(data){
			$.CloseDialog($container);
			if(data.success){
				loadData();
				if(isAdd){
					doUpdate(data.object.id);
				}
			}else{
				alert(data.message);
			}
		});
	}
	//var $toolbar = $('#toolbar');
	function loadData(){
		$datagrid.datagrid( {
	        'checkbox':true,
			'action':true,
			'showOnePageBar':false,
			'doUpdate':function(data){
				doUpdate(data.id);
			},
		    'doDel':function(data){
		    	doDel([data]);
		    },
		    'dataModel':{
		    	 'url': roleContext+'list-data.do',
		    	 'parameters':getCommJosnParams('search')
		    },
		    toolbar:[
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
								doDel(datas);
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
	            {title:'代码',dataField:'code'},       
				{title:'名称',dataField:'name'},
				{title:'是否有效',dataField:'enabled'},
				{title:'说明',dataField:'remark'},
				{title:'创建时间',dataField:'createTime'},
				{title:'修改时间',dataField:'modifyTime'}
	        ]
		});
	}
	// init
	$(function(){
		renderNavTooltip(['角色管理','列表']);
		loadData();
    });
	
	$('#export_btn').click(function(){
		var url = roleContext+'exportSecRoleData.do';
		downloadFileByDynamicForm(url,'search','sp');
	});
});


</script>