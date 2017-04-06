/**
 * 文件上传
 * 
 */

function forImport(obj){
	var params = $(obj).attr("name");
	var strArray = new Array();
	strArray = params.split(",");
	var url = getContextPath()+'/common/forImport.do?tempalteFileName='+strArray[0]+"&actionUrl="+strArray[1];
	//alert("url:"+url);
	$.FormDialog({
        width: 550,
        height: 300,
        url:url,
        title: "导入",
	 	doOk:function($container){
	 		$.CloseDialog($container);
	 		loadData();
	 	}	
	});
}

function towardImportPage(obj){
	var params = $(obj).attr("name");
	var strArray = new Array();
	strArray = params.split(",");
	strArray2 = strArray[1].split("&");
	//className=scoreService&methodName=validAndSaveScore2
	var url = getContextPath()+'/common/towardImportPage.do?tempalteFileName='+strArray[0]+"&"+strArray2[0]+"&"+strArray2[1];
	//alert("url:"+url);
	$.FormDialog({
        width: 550,
        height: 300,
        url:url,
        title: "导入",
	 	doOk:function($container){
	 		$.CloseDialog($container);
	 		loadData();
	 	}	
	});
}