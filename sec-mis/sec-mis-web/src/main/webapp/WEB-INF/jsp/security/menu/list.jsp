<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%
  String context = request.getContextPath();
  context = context.equals("/")? context : context + "/";
%>     
<input class="btn btn-purple" type="button" id="addRoot" value="新增菜单根节点"/>
 <div>
   <ul id="treeUl" class="ztree"></ul>
</div>
<link rel="stylesheet" href="<%=context%>3rd/zTree_v3/css/zTreeStyle/zTreeStyle.css" type="text/css">   
<script type="text/javascript" src="<%=context%>3rd/zTree_v3/js/jquery.ztree.all-3.5.js"></script>
<script type="text/javascript">
$(function(){
	var rootContext = '<%=context%>'+'menu/';
	renderNavTooltip(['菜单管理','列表']);
	var setting = {
		data:{
			simpleData: {
				enable: true,
				idKey: "id",
				pIdKey: "parentId"
			}
		},
		view: {
			addHoverDom: addHoverDom,
			removeHoverDom: removeHoverDom,
			selectedMulti: false
		}
		,
		edit: {
			enable: true,
			showRemoveBtn: false,
			showRenameBtn: false
		},callback:{
			beforeDrag:function(treeId, treeNodes) {
					return false;
				}
		}
	};
	function  validate($container) {  
		var $form = $container.find('form');
		$form.validate(
			{
	         rules: {
	        	 location: "required",
	  		 	name: "required",
	  		 	pattern:"required",
	  		 	orderNum:{
	  		 		required:true,
	  		 		min:1
	  		 	}
	  		 },
	    	messages: {
	    		location: "请输入菜单地址",
	    		name: "请输入菜单名称",
	    		pattern:"请输入Acl地址",
	    		orderNum:"显示序号大于0"
	   		}
	     });
		return $form.valid();
    }
	/**
	* opts:{
		container:$container,
		isNew:true/false,
		isMenu:true/false,
		currNode:currNode
	}
	*/
	function doSave(opts){
		var currNode = opts.currNode;
		var $container = opts.container;
		var isNew = opts.isNew;
		var isMenu = opts.isMenu;
		var postdata = $container.find('form').serialize();
		var url = rootContext;
		url += (isMenu?'save.do':'save-acl.do');
		$.request(url,postdata,function(msgBack){
			if(msgBack.success){
				var zTree = $.fn.zTree.getZTreeObj("treeUl");
				if(isNew){
					zTree.addNodes(currNode, msgBack.object);
				}else{
					var data = msgBack.object;
					currNode.url = data.url;
					currNode.name = data.name;
					zTree.updateNode(currNode);
				}
				$.CloseDialog($container);
			}else{
				alert(msgBack.message);
			}
		});
	}

	function doAdd(treeNode,isMenuAdd){
		var url = rootContext;
		if(treeNode){
			if(isMenuAdd){
				url += ('add.do?parentId='+treeNode.id);
			}else{
				url += ('add-acl.do?menuId='+treeNode.id);
			}
		}else{
			url += 'add.do';
		}

		var title = isMenuAdd?'新增菜单':'新增Acl';
		$.FormDialog({
		        width: 450,
		        height: 400,
		        url:url,
		        title: title,
			 	doOk:function($container){
			 		var isValidate = validate($container);
			 		if(isValidate){
			 			doSave({container:$container,
			 				isNew:true,
			 				isMenu:isMenuAdd,
			 				currNode:treeNode});
			 		}
			 	}	
		  });
	}
	function getAclId(id){
		return id.substr(id.indexOf("_")+1);
	}
	function doUpdate(treeNode){
		var url = rootContext;
		var isMenuUpdate;
		if(treeNode.menu){
			isMenuUpdate = true;
			url += ('update.do?menuId='+treeNode.id);
		}else{
			isMenuUpdate = false;
			url += ('update-acl.do?aclId='+getAclId(treeNode.id));
		}
		var title = isMenuUpdate?'编辑菜单':'编辑Acl';
		 $.FormDialog({
		        width: 450,
		        height: 400,
		        url:url,
		        title: title,
			 	doOk:function($container){
			 		var isValidate = validate($container);
			 		if(isValidate){
			 			doSave({container:$container,
			 				isNew:false,
			 				isMenu:isMenuUpdate,
			 				currNode:treeNode});
			 		}
			 	}	
		    });
	}
	
	function addHoverDom(treeId, treeNode){
		//$('.button.edit,.button.remove').unbind().remove();
		var tId = treeNode.tId;
		
		if ($("button.ztree-btn").size()>0) {
			return;
		}
		
		$('button.ztree-btn').remove();
		
		var aObj = $("#"+tId+"_span");
		if(treeNode.menu){
			var addStr = '<button id="'+tId+'_add" class="btn btn-purple ztree-btn" title="新增菜单">新增菜单</button>';
			aObj.append(addStr);
			$("#" +tId+"_add").bind('click',function(){	
					doAdd(treeNode,true);
					return false;
			});	
			
			var addAcl = '<button id="'+tId+'_addAcl" class="btn btn-pink ztree-btn" title="新增Acl">新增Acl</button>';
			aObj.append(addAcl);
			 $("#"+tId+"_addAcl").bind("click", function(){
				doAdd(treeNode,false);
				return false;
			});	
		}
		var edit = '<button id="'+tId+'_edit" title="编辑" class="btn btn-primary ztree-btn">编辑</button>';
		aObj.append(edit);
		$("#"+tId+"_edit").bind('click', function(){
			doUpdate(treeNode);
			return false;
		});
		var remove = '<button id="'+tId+'_remove" title="删除" class="btn btn-danger ztree-btn">删除</button>';
		aObj.append(remove);
	
		$('#'+tId+'_remove').bind('click', function(){
			$.MessageDialog(MessageDialog.CONFIRM,{message:'确认是否删除？',doOk:function($container){
				var url = rootContext;
				var parameters = {};
				if(treeNode.menu){
					url +=('delete.do?_rand='+Math.random());
					parameters.menuId = treeNode.id;
				}else{
					url += ('delete-acl.do?_rand='+Math.random());
					parameters.aclId = getAclId(treeNode.id);
				}
				
				$.request(url, parameters, function(success){										
					if(success){
						var zTree = $.fn.zTree.getZTreeObj("treeUl");
						zTree.removeNode(treeNode);	
						$.CloseDialog($container);
					}else{
						alert('删除失败');
					}
				});
			}});
		});
	};
	
	function removeHoverDom(treeId, treeNode){		
		$('button.ztree-btn').unbind().remove();
	};
	
	$("#addRoot").click(function(){
		doAdd(null,true);
	});

	var treeUrl = rootContext+'tree-data.do?_rand='+Math.random();
	$.request(treeUrl, '', function(data){	
		if(data&&data.length>0){
			var zTreeObj = $.fn.zTree.init($("#treeUl"), setting, data);
			//zTreeObj.expandAll(true);			
		}
	});
});
</script>
</html>