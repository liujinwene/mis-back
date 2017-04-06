<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
  String context = request.getContextPath();
  context = context.equals("/")? context : context + "/";
%>     
<div class="row">
	<div class="col-xs-12">
		<div class="input-group" id="search">
		创建时间:						
			<input class="date-picker input-small" id="createTimeStart" type="text" data-date-format="yyyy-mm-dd"  name="createTimeStart"/>
				<i class="icon-calendar bigger-110"></i>至
			<input class="date-picker input-small" id="createTimeEnd" type="text" data-date-format="yyyy-mm-dd"  name="createTimeEnd"/>
				<i class="icon-calendar bigger-110"></i>
			用户名:<input type="text" id="username" class="input-sm">
			名字:<input type="text" id="realname" class="input-sm">
			电话:<input type="text" id="mobile" class="input-sm">
			email:<input type="text" id="email" class="input-sm">
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
	var rootContext = '<%=context%>' +'user/';
	var $datagrid=$('#list');
	
	$('#search_btn').click(function(){
		loadData();
	});
	
	function  validate($container,isAdd) {  
		var $form = $container.find('form');
		var validateOpts={
		         rules: {
		        	 username: "required",
	        		 email: {
	        		     required: false,
	        		     email: true
	        		 },
	        		 password: {
	        		     required: isAdd,
	        		     minlength: 6
	    		    },
	    		    password2: {
	    		    	required: isAdd,
	    		    	minlength: 6,
	    		    	equalTo: "#password"
	    		    },
	    		    mobile:{
	    		    	 required: false,
	    		    	isMobile:true
	    		    }
		  		 },
		    	messages: {
		    		username: "请输入用户名",
		    		email: {
		    		     email: "请输入正确的email地址"
		    		 },
		    		 password: {
		    			 required: "请输入密码",
		    			 minlength: "密码不能小于6个字符"
	    		    },
	    		    password2: {
	    		    	required: "请输入确认密码",
	    		    	minlength: "确认密码不能小于6个字符",
	    		    	equalTo: "两次输入密码不一致"
	    		    }
		   		}
		     };
		
		$form.validate(validateOpts);
		return $form.valid();
    }

	function doAdd(){
		 $.FormDialog({
		        width: 550,
		        height: 600,
		        url:rootContext+'add.do',
		        title: "新增用户",
			 	doOk:function($container){
			 		var isValidate = validate($container,true);
			 		if(isValidate){
			 			doSave($container,true);
			 		}
			 	}		
		    });
	}
	
	function doUpdate(id){
		var initRoleTree=function(){
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
			//var treeUrl = rootContext+'user-role.do?_rand='+Math.random();	
			var treeUrl = rootContext+'user-role.do';	
			$.request(treeUrl, {userId: id}, function(data){	
				var $tree = $("#role-tree");
				if(data&&data.length>0){
					zTreeObj = $.fn.zTree.init($tree, setting, data);
					zTreeObj.expandAll(true);			
				}else{
					$tree.html('<span style="color:red;">数据加载失败！</span>');
				}	
			});
		}
		$.FormDialog({
	        width: 550,
	        height: 600,
	        url:rootContext+'update.do?userId='+id,
	        title: "编辑用户",
		 	doOk:function($container){
		 		var isValidate = validate($container,false);
		 		if(isValidate){
		 			doSave($container,false);
		 		}	
		 	},open: function( event, ui ) {
		 		initRoleTree();
		 	}	
	    });
	}
	
	function doDel(datas){
		$.MessageDialog(MessageDialog.CONFIRM,{message:'确认是否删除？',doOk:function($container){
			var ids=[];
			$.each(datas,function(){
				ids.push(this.id);
			});
			$.request(rootContext+'delete.do',{userIds:ids},function(data){
				if(data){
					$.CloseDialog($container);
					loadData();						
				}
			});
		}});
	}
	
	function doSave($container,isAdd){
		var postdata = $container.find('form').serialize();
		var roleIds = [];
		if($("#role-tree").size()>0){
			var zTreeObj = $.fn.zTree.getZTreeObj("role-tree");
			var nodes = zTreeObj.getCheckedNodes(true);
			var resourceIds = new Array();
			$.each(nodes, function(i,node){
				roleIds.push(node.id);
			});
		}
		if(roleIds.length>0){
			postdata += ("&"+jQuery.param({roleIds:roleIds}))
		}
		 
		$.request(rootContext+'save.do',postdata,function(data){
			if(data.success){
				$.CloseDialog($container);
				alert("操作成功.");
				loadData();
			}else{
				alert(data.message);
			}
		});
	}
	function doRefresh(){
		loadData();
	}
	//var $toolbar = $('#toolbar');
	function loadData(){
		$datagrid.datagrid( {
	        'checkbox':true,
			'action':true,
			'doUpdate':function(data){
				doUpdate(data.id);
			},
		    'doDel':function(data){
		    	doDel([data]);
		    },
		    'dataModel':{
		    	 'url': rootContext+'list-data.do',
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
	            {title:'用户名',dataField:'username'},       
				{title:'名字',dataField:'realname'},
				{title:'锁定',render:function(record,index){
					 return record.enabled?'否':'是';
				}},
				{title:'手机',dataField:'mobile'},
				{title:'email',dataField:'email'},
				{title:'创建时间',width:160,dataField:'createTime'},
				{title:'修改时间',width:160,dataField:'modifyTime'},
				{title:'上次登录时间',width:160,dataField:'lastLoginTime'}
	        ]
		});
	}
	// init
	$(function(){
		renderNavTooltip(['用户管理','列表']);
		loadData();
    });
	$('.date-picker').datepicker({language: 'zh-CN',autoclose:true}).next().on(ace.click_event, function(){
		$(this).prev().focus();
	});
	
	$('#export_btn').click(function(){
		var url = rootContext+'exportSecUserData.do';
		downloadFileByDynamicForm(url,'search','sp');
	});
});


</script>