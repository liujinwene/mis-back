<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
  String context = request.getContextPath();
  context = context.equals("/")? context : context + "/";
%>
<div class="row">
	<div class="col-xs-12">
		<div class="input-group" id="search">
			操作时间: <input class="date-picker input-small" id="createTimeStart"
				type="text" data-date-format="yyyy-mm-dd" name="createTimeStart" />
			<i class="icon-calendar bigger-110"></i>至 <input
				class="date-picker input-small" id="createTimeEnd" type="text"
				data-date-format="yyyy-mm-dd" name="createTimeEnd" /> <i
				class="icon-calendar bigger-110"></i> 
				用户名:<input type="text" id="userName"  name="userName" class="input-sm"> 
				内容:<input type="text" id="operateContent"  name="operateContent" class="input-sm"> 
				<span class="">
				<button id="search_btn" type="button" class="btn btn-purple btn-sm">
					搜索 <i class="icon-search icon-on-right bigger-110"></i>
				</button>
				</span>
		</div>
		<div id="list"></div>
	</div>
</div>
<script type="text/javascript">

	var rootContext = '<%=context%>' +'syslog/';
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
		    	 'url': rootContext+'list-data.do',
				 'parameters':getCommJosnParams('search')
		    },
	        'columns': [
	            {
	            	title:'用户名',
	            	width : '8%',
	            	dataField:'userName'
	            },       
				{
	            	title:'姓名',
	            	width : '8%',
	            	dataField:'realName'
	            	
	            },
				{
	            		title:'内容',
	            		width : '30%',
	            		dataField:'operateContent'
	            		
	            },
				
				//{
	            //title:'类型',
	            	//width : '10%',
	            	//render:function(record,index)
	   	 			 // {
	            	//	var r = record.operateType;
	            		//if( r=0){
	            		//	return '解锁';
	            		//}else if(r=1){
	            		//	return '提现';
	            		//}else if(r=2){
	            		//	return '补单';
	            			//}
	   	 			    // }
	            // }
	            // ,
				{
	            	title:'操作时间',
	            	width : '10%',
	            	dataField:'operateTime'
	            }
				
	        ]
		});
	}
		
		
$(function(){	

	$('#search_btn').click(function(){
		loadData();
	});
	$(function(){
		renderNavTooltip(['日志信息','列表']);
		loadData();
    });
	$('.date-picker').datepicker({language: 'zh-CN',autoclose:true}).next().on(ace.click_event, function(){
		$(this).prev().focus();
	});
});


</script>