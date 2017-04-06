/**
 * rowRenderCompleted:function($tr,rowdata,rowindex)
 */
$.extend($.fn, {
	datagrid:function(opts){
		opts = $.extend({
			pagesize:15,
			showCount:true,
			action:false,
			checkbox:false,
			toolbar:null,
			showPagebar:true,
			doUpdate:function(rowdata){rowdata,$tr},
			doDel:function(rowdata){rowdata,$tr},
			rowRenderCompleted:null,
			renderHeader:true,
			selectedRowClass:'success',
			rowdata:'rowdata'
		 },opts);
		var settings = 'settings';
		var btnStyle={
				'ADD':{'text':'新增','icon':'ui-icon icon-plus-sign btn-purple btn-add'},	
				'MOD':{'text':'修改','icon':'ui-icon icon-pencil btn-primary btn-mod'},	
				'REFRESH':{'text':'刷新','icon':'ui-icon icon-refresh btn-success btn-refresh'},	
				'DEL':{'text':'删除','icon':'ui-icon icon-trash  btn-danger btn-del'},
				'AUDIT':{'text':'审核','icon':'ui-icon icon-piliangs btn-grey btn-mod'},
				'AFFIRM':{'text':'确认','icon':'ui-icon icon-piliangq btn-success btn-mod'}
				
			};
		function getStyle(type){
			var style = btnStyle[type];
			return style?style:{text:'',icon:''};
		}
		function rendToolbar(container){
			if(!opts.toolbar){
				return;
			}
			var $toolbar = $('<div class=" ui-pg-div hh-toolbar">').appendTo(container);
			$.each(opts.toolbar,function(index,item){
				item = $.extend({
					disabled: false
				},item||{});
				var style = getStyle(item.type);
				var $btn = $('<button role="button"/>').addClass('btn').addClass( style.icon);
				$btn.attr('data-original-title',style.text);
				if(item.id){
					$btn.prop('id',item.id);
				}
				if(item.disabled){
					$btn.addClass('disabled')
				}
				$btn.click(function(){
					if(!$(this).hasClass('disabled')){
						if($.isFunction(item.click)){
							item.click.call(this);
						}
					}
					return false;
				})
				$toolbar.append($btn);
			});
			//$toolbar.find('button').tooltip({container:'body'});
		}
		
		//////////////////
		function checkboxClicked($gridContainer){
			var hasSelected = $gridContainer.find('tr.selected').size()>0;
			if(hasSelected){
				$gridContainer.find('div.hh-toolbar > button.btn-mod').removeClass('disabled');
				$gridContainer.find('div.hh-toolbar > button.btn-del').removeClass('disabled');
	    	}else{
				$gridContainer.find('div.hh-toolbar > button.btn-mod').addClass('disabled');
				$gridContainer.find('div.hh-toolbar > button.btn-del').addClass('disabled');
	    	}
		}
		
		return this.each(function(){
			var $gridContainer = $(this);
			$gridContainer.data(settings,opts);
			
			var sortParams = {};
			var $table = null;
			
			function renderHeader(){
				var $header = $('<tr/>');
				if(opts.columns){
					if(opts.checkbox){
						var $th= $('<th class="center" width="20px"><label><input type="checkbox" class="ace" /><span class="lbl"></span></label></th>');
						$header.append($th);
						$th.find('input[type="checkbox"]').click(function(event){
				    		var that = this;
							$(this).closest('table').find('tr > td:first-child input:checkbox')
							.each(function(){
								this.checked = that.checked;
								if(that.checked){
									$(this).closest('tr').addClass(opts.selectedRowClass).addClass('selected');
								}else{
									$(this).closest('tr').removeClass(opts.selectedRowClass).removeClass('selected');
								}
							});
							checkboxClicked($gridContainer);
				    	});
					}
					if(opts.action){
						var $th= $('<th class="center" width="80px">操作</th>');
						$header.append($th);
					}
	    			$.each(opts.columns, function(index){
	    				var column = opts.columns[index];
	    				var $th = $('<th></th>').text(column.title);
	    				if(column.width){
	    					$th.attr('width',column.width);
	    				}
	    				if(column.align){
	    					$th.css('text-align',column.align);
	    				}
	    				if(column.hidden){
	    					$th.css('display','none');
	    				}
	    				if(column.sortFiled){
	    					$th.addClass("sorting");
	    					$th.click(function(){
	    						//排序点击
	    						var p={};
	    						var sortAsc;
	    						var sortFiled=column.sortFiled;
	    						if($th.hasClass('sorting')){
	    							sortAsc='desc';
	    							$th.removeClass("sorting");
	    							$th.addClass("sorting_desc");
	    						}else if($th.hasClass('sorting_desc')){
	    							sortAsc='asc';
	    							$th.removeClass("sorting_desc");
	    							$th.addClass("sorting_asc");
	    						}else{
	    							$th.removeClass("sorting_asc");
	    							$th.addClass("sorting");
	    							sortFiled=null;
	    						}
	    						p['sp[sortAsc]']=sortAsc;
	    						p['sp[sortFiled]']=sortFiled;
	    						//opts.dataModel.parameters=p;
	    						sortParams = p;
	    						loadPage(0);
	    						
	    						//console.log($(this));
	    					});
	    				}
	    				$header.append($th);
	    			});
	    		}
				
				$table.append($('<thead></thead>').append($header)).append($('<tbody></tbody>'));
		    	
			}// end renderHeader
			function initPagebar(){
				$('.pagination',$gridContainer).closest('div.text-right').remove();
				//if($('.pagination',$gridContainer).size() == 0)
				{
					$gridContainer.append($('<div class="text-right"/>').append($('<ul style="margin-top:2px;"/>').addClass('pagination')));
				}
			}
			function renderBody(){
	    		if(opts.dataModel){
					loadPage(0);
				}
	    	}//end renderBody
			function loadPage(page){
	    		var dataModel = $.extend({type:'json'},opts.dataModel || {});
	    		var parameters = {};
	    		if($.isFunction(dataModel.parameters)){
	    			parameters = dataModel.parameters();
	    		}else{
	    			parameters = dataModel.parameters;
	    		}
	    		parameters = $.extend(parameters,sortParams||{});
	    		if(opts.showPagebar){
					var pagesize = opts.pagesize;
	    			parameters.start = (page)*pagesize;
	    			parameters.end = (page+1) * pagesize;
	    			parameters.limit = pagesize;
	    			parameters.pageNo = pagesize;
	    		}
	    		
	    		var doSucess=function(data){
	    			 var $gridBody = $('tbody', $gridContainer);
 				    $gridBody.empty();
 				  //  data = data.items;
 					var totalCount = data.total;
 					var dataItems = data.items;
 					if(opts.drawTbody){
 						opts.drawTbody($gridBody,dataItems,opts);
 					}else{
 						drawTbody($gridBody,dataItems,opts);
 					}
 					if(opts.showPagebar){
 						var pageOpts = {
	    							current_page:page,
	    							num_edge_entries: 2,
	    						    items_per_page: opts.pagesize,
	    							prev_text:"上一页",
	    						    next_text:"下一页",
	    						    renderPagebarBefore:false,
	    						    show_entries_count: opts.showCount,//
	    						    callback:loadPage
	    					};
	    					if( totalCount>opts.pagesize){//
	    						$gridContainer.find('.pagination').pagination(totalCount,pageOpts);
	    					}	
 					}
 					
 					if(opts.doRenderAfter){//渲染完表格，需要做渲染后的处理
 						var doRenderAfter = opts.doRenderAfter;
 						doRenderAfter($gridBody,totalCount);
 					}	
	    		}
	    		var type = dataModel.type;
	    	
	    		var mask = showMask();
	    		if(type == 'json'){
	    			$.ajax({
	    				type: 'POST',
	    				dataType: "json",
	    				url: dataModel.url,
	    				data: parameters,
	    				success: function(data){
	    					mask.remove();
	    					doSucess(data);
	    				},
	    				error:function(){
	    					mask.remove();
	    				}
	    			});
	    		}
	    	}
			
			function showMask(){
				var gridOffset = $gridContainer.offset();
				var height = $gridContainer.outerHeight()+$('.pagination',$gridContainer).outerHeight()+25;
				var width = $gridContainer.width();
				var $mask =$('<div class="grid-mask"/>');
				$mask.append($('<div/>').css({
					float:'left',
					height:'50%',
					'margin-bottom':'-120px'
				}));
				var $loading = $('<div/>').css({
						'clear':'both',
						'text-align': 'center',
						'width': 'auto',
						'display': 'table',
						'margin-left': 'auto',
						'margin-right': 'auto',
						position:'relative',
						top:gridOffset.top/2,
						height:'240px',
						zIndex:900
				   }).append('<h1 class="header smaller lighter grey"><i class="icon-spinner icon-spin danger  bigger-125"></i>Loading....</h1>');
				$mask.append($loading);
				
				
				return $mask.css({
					 position:'absolute',
					 top:gridOffset.top,
					 left:gridOffset.left,
					 backgroundColor:"#ffffff",
					 opacity:0.2,
					 zIndex:300
					}).height(height).width(width).appendTo("body");
			}
			function getTDValue(column,record,$td,index){
				var value='';
				if(column.render!=null){
					value = column.render(record,$td,index);
				}else{
					value = record[column.dataField];
				}
				return value;
			}
			function drawTbody($tbody,data,opts){
				if($(data).size()<=0){
					appendNoResultRow($tbody, opts);
					return false;
				}
				$(data).each(function(index){
					var record = this;
					var $tr = $('<tr/>');
					if(opts.checkbox){
						var $td = $('<td class="center"><label><input type="checkbox" class="ace" /><span class="lbl"></span></label></td>');
						$tr.append($td);
						$td.find('input[type="checkbox"]').click(function(event){
					    	$(this).closest('tr').toggleClass(opts.selectedRowClass).toggleClass('selected');
					    	if(!this.checked){
					    		$gridContainer.find('thead input:checkbox').prop('checked',false);
					    	}
					    	checkboxClicked($gridContainer);
					    });
					}
					if(opts.action){
						var html = 	'<div class="visible-md visible-lg hidden-sm hidden-xs action-buttons">'
							+'	<a class="green update-btn" href="#">'
							+'		<i class="icon-pencil bigger-130"></i>'
							+'	</a>'
							+'	<a class="red del-btn" href="#">'
							+'		<i class="icon-trash bigger-130"></i>'
							+'	</a>'
							+'</div>';
						var $td = $('<td/>').append(html);
						$tr.append($td);
						$('a.update-btn', $td).click(function(){
							opts.doUpdate($tr.data(opts.rowdata),$tr);
						});
						
						$('a.del-btn', $td).click(function(){
							opts.doDel($tr.data(opts.rowdata),$tr);
						});
					}
					$(opts.columns).each(function(){
						var column = this;
						var $td = $('<td/>');
						var value = getTDValue(column,record,$td,index);
						if(column.clazz){
							$td.addClass(column.clazz);
						}
						if(column.align){
	    					$td.css('text-align',column.align);
	    				}
						$td.html(value);
						if(column.hidden){
							$td.hide();
	    				}
						$tr.append($td);
					});	
					
					if(opts.dblclick){
						$tr.css('cursor','pointer');
						$tr.dblclick(function(){
							opts.dblclick($tr);
						});
					}
					if(opts.click){
						$tr.css('cursor','pointer');
						$tr.click(function(){
							opts.click($tr);
						});
					}
					if(opts.rowRenderCompleted){
						opts.rowRenderCompleted($tr,record,index);
					}
					$tr.data(opts.rowdata,record);
					$tbody.append($tr);
				});
				if(jQuery.isFunction(opts.tableRenderCompleted)){
					opts.tableRenderCompleted($table);
				}
			}
			
			function appendNoResultRow(tableBody, opts){
				var colspan = opts.columns.length;
				if(opts.checkbox){
					colspan+=1;
				}
				if(opts.action){
					colspan+=1;
				}
	    		tableBody.append('<tr><td colspan="' + colspan + '" style="text-align:center;vectical-align:middle;height:36px;">没有记录！</td></tr>');
			}
			
			function render(){
				sortParams={};
				$gridContainer.addClass('datagrid');
				
	    		if(opts.renderHeader){
	    			$gridContainer.empty();
	    			rendToolbar($gridContainer);
	    			$table = $('<table class="table table-striped table-bordered table-hover dataTable"></table>');
					$gridContainer.append($table);
	    			renderHeader();
	    		}else{
	    			rendToolbar($gridContainer);
					$table = $('table',$gridContainer);
				}
	    		initPagebar();
	    		renderBody();
	    	}
	    	render();
		});	
	},
	selectedRowDatas:function(){
		var $gridContainer = $(this);
		var opts = $gridContainer.data('settings');
		var rowdatas=[];
		$gridContainer.find('tr.'+opts.selectedRowClass).each(function(){
			rowdatas.push($(this).data(opts.rowdata));
		});
		return rowdatas;
	}
});

/*$.fn.datagrid=function(opts){
		
};*/