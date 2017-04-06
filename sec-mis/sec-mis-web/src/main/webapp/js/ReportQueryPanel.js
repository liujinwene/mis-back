/**
 * 报表查询通用查询面板
 * 参数：
 * {
 * 'target':$("#baseReportQuery"),
 * 'hideTypes':['SELECTION','MONTH'],
 * 'startCurMonth': true 起始月份为当前月份
 * 'endCurMonth': true 结束月份为当前月份
 * }
 * */
function ReportQueryPanel(tConfig){
	this.config = tConfig;
	this.target = this.config.target;
	this.panel = null;
	this.currType = null;
	//INIT
	this.initPanel();
}
ReportQueryPanel.prototype = {
	initPanel: function(){
		//Create Panel and append to target
		this.buildPanel();
		this.target.empty().append(this.panel);
		this.panel = this.target;
		this.hideTypes();
		//Bind Panel's fields data
		this.bindDatas();
		//Bind Events
		this.bindEvents();
		//Select One
		this.showDefault();
	},
	buildPanel: function(){
		var html = 
				'<label name="report_query_panel_type_selection">'
			+	'	<input type="radio" class="ace" name="reportQueryType" value="SELECTION">'
			+	'	<span class="lbl">自选</span>'
			+	'</label>'
			+	'<label name="report_query_panel_type_month">'
			+	'	<input type="radio" class="ace" name="reportQueryType" value="MONTH">'
			+	'	<span class="lbl">月</span>'
			+	'</label>'
			+	'<label name="report_query_panel_type_season">'
			+	'	<input type="radio" class="ace" name="reportQueryType" value="SEASON">'
			+	'	<span class="lbl">季</span>'
			+	'</label>'
			+	'<label name="report_query_panel_type_year">'
			+	'	<input type="radio" class="ace" name="reportQueryType" value="YEAR">'
			+	'	<span class="lbl">年</span>'
			+	'</label>'
			+'<span class="report-query-panel-condition" name="report_query_panel_selection" style="display:none">'
				+'<input name="startDate" class="date-picker input-small" type="text" data-date-format="yyyy-mm-dd" type="text" /> <i class="icon-calendar bigger-110"></i> 至 '
				+'<input name="endDate" class="date-picker input-small" type="text" data-date-format="yyyy-mm-dd" type="text"/> <i class="icon-calendar bigger-110"></i>'
			+'</span>'
			+'<span class="report-query-panel-condition" name="report_query_panel_month" style="display:none">'
				+'<select name="startYear" class="sele_year"></select> '
				+'<select name="startMonth" class="sele_year"></select> 至 '
				+'<select name="endYear" class="sele_year"></select> '
				+'<select name="endMonth" class="sele_year"></select>'
			+'</span>'
			+'<span class="report-query-panel-condition" name="report_query_panel_season" style="display:none">'
				+'<select name="startYear" class="sele_year"></select> '
				+'<select name="startSeason" class="sele_year"></select> 至 '
				+'<select name="endYear" class="sele_year"></select> '
				+'<select name="endSeason" class="sele_year"></select>'
			+'</span>'
			+'<span class="report-query-panel-condition" name="report_query_panel_year" style="display:none">'
				+'<select name="startYear" class="sele_year"></select> 至 '
				+'<select name="endYear" class="sele_year"></select>'
			+'</span>'
			;
		this.panel = $(html);
	},
	bindDatas: function(){
		var $this = this;
		var today = new Date();
		function genYearOptions(){
			var yearOptions = '';
			for(var y=2014; y<=today.getFullYear(); y++){
				yearOptions += '<option value="'+y+'">'+y+'年</option>';
			}
			return yearOptions;
		}
		
		function genMonthOptions(){
			var monthOptions = '';
			for(var m=1; m<=12; m++){
				monthOptions += '<option value="'+m+'">'+m+'月</option>';
			}
			return monthOptions;
		}
		
		function genSeasonOptions(){
			var seasonOptions = '';
			seasonOptions += '<option value="1">第一季度</option>';
			seasonOptions += '<option value="2">第二季度</option>';
			seasonOptions += '<option value="3">第三季度</option>';
			seasonOptions += '<option value="4">第四季度</option>';
			return seasonOptions;
		}
		
		function getDateInterval(){
			var startDate = $this.config.startDate? $this.config.startDate : new Date(today.getFullYear(), today.getMonth(), today.getDate()-7);
			var endDate = $this.config.endDate? $this.config.endDate : new Date(today.getFullYear(), today.getMonth(), today.getDate()-1);
			return {'startDate':startDate, 'endDate':endDate};
		}
		
		var dateInterval = getDateInterval();
		this.panel.find("input[name='startDate']").val(dateInterval.startDate.format("yyyy-mm-dd"));
		this.panel.find("input[name='endDate']").val(dateInterval.endDate.format("yyyy-mm-dd"));
		
		var yearSelectors = this.panel.find("select[name='startYear'],select[name='endYear']");
		yearSelectors.append(genYearOptions());
		yearSelectors.find("option[value='"+today.getFullYear()+"']").attr("selected",true);
		
		var monthStartSelectors = this.panel.find("select[name='startMonth']");
		monthStartSelectors.append(genMonthOptions());
		if($this.config.startCurMonth){
			monthStartSelectors.find("option[value='"+(today.getMonth()+1)+"']").attr("selected",true);
		} 
		var monthEndSelectors = this.panel.find("select[name='endMonth']");
		monthEndSelectors.append(genMonthOptions());
		if($this.config.endCurMonth){
			monthEndSelectors.find("option[value='"+(today.getMonth()+1)+"']").attr("selected",true);
		}
		
		var seasonSelectors = this.panel.find("select[name='startSeason'],select[name='endSeason']");
		seasonSelectors.append(genSeasonOptions());
	},
	bindEvents: function(){
		var $this = this;
		//Bind Type Selector
		this.panel.find("input[name='reportQueryType']").click(function(){
			$this.currType = $(this).val();
			$this.showCondition();
		});
		//Bind StartDate and EndDate
		this.panel.find("input[name='startDate'],input[name='endDate']").datepicker({language: 'zh-CN',autoclose:true}).next().on(ace.click_event, function(){
			$(this).prev().focus();
		});
	},
	hideAllCondition: function(){
		$("span.report-query-panel-condition").hide();
	},
	showCondition: function(tType){
		//Hidden all
		this.hideAllCondition();
		//Show selected
		this.findCondition(tType).show();
	},
	findCondition: function(tType){
		var type = tType? tType.toLowerCase() : this.currType.toLowerCase();
		var conditionPanelName = 'report_query_panel_'+type;
		return this.panel.find('span[name="'+conditionPanelName+'"]');
	},
	hideTypes: function(tTypes){
		var types = tTypes? tTypes : this.config.hideTypes;
		if(!types){
			return;
		}
		var $this = this;
		$.each(types, function(i, type){
			$this.panel.find("label[name='report_query_panel_type_"+type.toLowerCase()+"']").hide();
		});
	},
	/**
	 * parentKey为查询条件的Key..
	 */
	getResult: function(parentKey){
		var tempResult = this._getResult();
		if(!parentKey){
			return tempResult;
		}
		var result={};
		var getParamKey=function(paramKey,parentKey){
			return parentKey+'['+paramKey+']';
		}
		for(var key in tempResult){
			result[getParamKey(key,parentKey)]=tempResult[key];
		}
		return result;
	},
	_getResult: function(){
		var result = {'dateType': this.currType};
		var fields = this.findCondition().find("input,select");
		var getSeasonMonthRange=function(season){
			season = parseInt(season);
			switch(season){
			  case 1:
				return {'start':1, 'end':3};
			  case 2:
				return {'start':4, 'end':6};
			  case 3:
				return {'start':7, 'end':9};
			  case 4:
				return {'start':10, 'end':12};
			  default:
				return null;
			}
		}
		$.each(fields, function(index, field){
			var field = $(field);
			result[field.attr("name")] = field.val();//.replace(new RegExp('-',"gm"),'');
		});
		//Type is SELECTION then return
		if(this.currType == "SELECTION"){
			return result;
		}//Other type need append startDate and endDate to result
		else if(this.currType == "MONTH"){
			var startDate = new Date(result.startYear, result.startMonth-1, 1);//month 0-11
			var endDate = new Date(result.endYear, result.endMonth, 0);
			result["startDate"] = startDate.format("yyyy-mm-dd");
			result["endDate"] = endDate.format("yyyy-mm-dd");
			return result;
		}else if(this.currType == "SEASON"){
			var startSeasonMonths = getSeasonMonthRange(result["startSeason"]);
			var endSeasonMonths = getSeasonMonthRange(result["endSeason"]);
			var startDate = new Date(result.startYear, startSeasonMonths.start-1, 1);//month 0-11
			var endDate = new Date(result.endYear, endSeasonMonths.end, 0);
			result["startDate"] = startDate.format("yyyy-mm-dd");
			result["endDate"] = endDate.format("yyyy-mm-dd");
			return result;
		}else if(this.currType == "YEAR"){
			var startDate = new Date(result.startYear, 0, 1);//month 0-11
			var endDate = new Date(result.endYear, 12, 0);
			result["startDate"] = startDate.format("yyyy-mm-dd");
			result["endDate"] = endDate.format("yyyy-mm-dd");
			return result;
		}
	},
	formatResult: function(tResult){
		var result = tResult? tResult : this.getResult();
		var paramHash = {};
		for(var k in result){
			paramHash["reportQuery."+k] = result[k];
		}
		return paramHash;
	},
	getUrlParams: function(tResult){
		return $.param(this.formatResult(tResult));
	},
	showDefault: function(){
		$this = this;
		var allTypes = ['SELECTION','MONTH','SEASON','YEAR'];
		var tmpTypes = new Array();
		if(this.config.hideTypes){
			$.each(allTypes, function(index, item){
				if($.inArray(item, $this.config.hideTypes) == -1){
					tmpTypes.push(item);
				}
			});
		}else{
			tmpTypes = tmpTypes.concat(allTypes);
		}
		this.panel.find("label[name='report_query_panel_type_"+tmpTypes[0].toLowerCase()+"']").children("input[name='reportQueryType']").click();
	}
};