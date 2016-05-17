$(function(){
	
	Date.prototype.format = function(fmt)   
	{ //author: meizz   
	  var o = {   
	    "M+" : this.getMonth()+1,                 //月份   
	    "d+" : this.getDate(),                    //日   
	    "h+" : this.getHours(),                   //小时   
	    "m+" : this.getMinutes(),                 //分   
	    "s+" : this.getSeconds(),                 //秒   
	    "q+" : Math.floor((this.getMonth()+3)/3), //季度   
	    "S"  : this.getMilliseconds()             //毫秒   
	  };   
	  if(/(y+)/.test(fmt))   
	    fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));   
	  for(var k in o)   
	    if(new RegExp("("+ k +")").test(fmt))   
	  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
	  return fmt;   
	}  ;
	
	var dateDefault = {
		format:"yyyy-mm-dd",
    	minView:'month',
    	autoclose:true,
    	todayBtn:true,
    	todayHighlight:true,
    	language:'zh-CN'
	};
	
	$(".datepicker").each(function( index, object ){
		$(object).addClass("input-small");
		var divId;
		if( typeof($(object).attr("id")) == "undefined" ){
			divId = "datepicker" + parseInt(1000*Math.random());
		}else {
			var divId = $(object).attr("id") + "datePickerDiv";
		}
		
		var value = $(object).attr("value");
		if( value == "today"){
			$(object).attr("value", new Date().format("yyyy-MM-dd"));
		}
		$(object).attr("pattern", "^(\\d{4})\\-(\\d{2})\\-(\\d{2})$");
			
		$(object).wrap("<span class='input-append date' id='" + divId + "' ></span>");
		$(object).after("<span class='add-on'> <i class='icon-calendar'> </i> </span></div>");
		//$(object).attr("readonly", "readonly");
		$("#" + divId).datetimepicker(dateDefault);
	});
});