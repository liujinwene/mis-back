//格式化金额.
function handleDataFormat(num) { 
	//1.先去除空格,判断是否空值和非数   
	num = num + "";   
	num = num.replace(/[ ]/g, ""); //去除空格  
	if (num == "") {   
	    return;   
    }   
	if (isNaN(num)){  
	    return;   
	}   
	//2.针对是否有小数点，分情况处理   
	var index = num.indexOf(".");   
	if (index==-1) {//无小数点   
	    var reg = /(-?\d+)(\d{3})/;   
	    while (reg.test(num)) {   
	        num = num.replace(reg, "$1,$2");   
	    }   
	} else {   
	    var intPart = num.substring(0, index);   
	    var pointPart = num.substring(index + 1, num.length);   
	    var reg = /(-?\d+)(\d{3})/;   
	    while (reg.test(intPart)) {   
	         intPart = intPart.replace(reg, "$1,$2");   
	    }   
	    num = intPart +"."+ pointPart;   
	}   
	
	return num;   
} 

//四舍五入，保留位数为roundDigit    
function   roundFun(numberRound,roundDigit)  {  
	if   (numberRound>=0)  {  
		var   tempNumber   =   parseInt((numberRound   *   Math.pow(10,roundDigit)+0.5))/Math.pow(10,roundDigit);  
		return   tempNumber;  
	}  
}
/**
 * 先保留2位小数，再进行格式化
 */
function handleDataFormat2(num){
	return handleDataFormat(roundFun(num,2));
}