/**
 * 文件下载
 * 需要使用到common.js中的 getCommJosnParams，getCommJosnParams 这两个函数
 */


/**
 * 根据divId查询div，生成ajax中需要的input内容
 * @param pJosn
 * @param divId
 * @returns {String}
 */
function genCommonInputParams(pJosn,divId,paramPrefix){
	var pStr="";
	var paramName = "";
	var paramValue = "";
	var inputs = "";
	var p = pJosn==undefined||pJosn==null?getCommJosnParams(divId):pJosn;
	for(var key in p){
		if(paramPrefix==undefined||paramPrefix==null || paramPrefix ==''){
			paramName = key.substring(key.indexOf("[")+1,key.indexOf("]"));
		}else{
			paramName = paramPrefix+"['"+key.substring(key.indexOf("[")+1,key.indexOf("]"))+"']";
		}
		paramValue = p[key];
		inputs = '<input type="hidden" name="' + paramName + '" value="' + paramValue+ '" />';
		pStr += inputs;
	}
	return pStr;
}

/**
 * 根据divId查询div，生成get请求参数
 * @param pJosn
 * @param divId
 * @returns {String}
 */
function genCommonParamsForGet(pJosn,divId,paramPrefix){
	var pStr="";
	var paramName = "";
	var paramValue = "";
	var inputs = "";
	var p = pJosn==undefined||pJosn==null?getCommJosnParams(divId):pJosn;
	for(var key in p){
		if(paramPrefix==undefined||paramPrefix==null || paramPrefix ==''){
			paramName = key.substring(key.indexOf("[")+1,key.indexOf("]"));
		}else{
			paramName = paramPrefix+"['"+key.substring(key.indexOf("[")+1,key.indexOf("]"))+"']";
		}
		paramValue = p[key];
		inputs = "&"+paramName+"="+paramValue;
		pStr += inputs;
	}
	return pStr.substring(1);
}



/**
 * 使用动态生成form的方式下载文件
 * @param url
 * @param divId，例如：search
 * @param paramPrefix,例如：sp
 */
function downloadFileByDynamicForm(url,divId,paramPrefix){
	var inputs = genCommonInputParams(null,divId,paramPrefix);
	//alert('inputs:'+inputs);
	// request发送请求
	jQuery('<form action="' + url + '" method="' +  'post' + '">' + inputs + '</form>')
	.appendTo('body').submit().remove();
}

/**
 * 使用href的方式来下载文件
 * @param url
 * @param divId，例如：search
 * @param paramPrefix,例如：sp
 */
function downloadFileByHref(url,divId,paramPrefix){
	
	var param = genCommonParamsForGet(null,divId,paramPrefix);
	var href =url+"?"+param;
	//alert('href:'+href);
	window.location.href=href;
	
}
function downloadFileByValHref(url,valUrl,divId,paramPrefix){
	var count = 0;
	var params = getCommJosnParams(divId);
	$.ajax({
		url: valUrl,
		type:'post',
		async: false,
		data:params,
		dataType:'json',
		success : function(data){
			count = data.total;
		}
	});
	if(count > 10000) {
		//alert("导出的记录数超过1万条,请分批导出！");
		$.MessageDialog(MessageDialog.INFO,{message:'导出的记录数超过1万条,请分批导出！'});
		return;
	}else if(count == 0){
		$.MessageDialog(MessageDialog.INFO,{message:'没有数据可导出！'});
		return;
	}
	var param = genCommonParamsForGet(null,divId,paramPrefix);
	var href =url+"?"+param;
	window.location.href=href;
}
