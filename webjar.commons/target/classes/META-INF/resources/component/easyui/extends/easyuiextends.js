
/**
 * dbgrid的easyui支持

 */

/**
 * 重写datagrid的textarea编辑组件，使其支持rows和columns属性
 */
$.extend($.fn.datagrid.defaults.editors, {  
	textarea: {  
		init: function(container, options){  
			var input = $('<textarea class=\"datagrid-editable-input\"></textarea>').appendTo(container);  
			if( typeof(options) == "undefined")
				return input;
			if( typeof(options.rows) != "undefined" )
				$(input).attr("rows", options.rows);
			if( typeof( options.columns) != "undefined" )
				$(input).attr("cols", options.columns);
			return input;  
		},  
		getValue: function(target){  
			return $(target).val();  
		},  
		setValue: function(target, value){  
			$(target).val(value);  
		},  
		resize: function(target, width){  
			var input = $(target);  
			if ($.boxModel == true){  
				input.width(width - (input.outerWidth() - input.width()));  
			} else {  
					input.width(width);  
			}  
		}  
	}
}); 

/**
 * dbgrid超链接点击函数
 * @param func
 */
function handleDbGridA( selector, rowIndex, func ){
	$(selector).datagrid('selectRow',rowIndex);
	var data = $(selector).datagrid('getSelected');
	func(data);
}

(function($) {
	$.fn.dbgrid = function(options) {
		
		var obj = this;
		
		var grid = options.grid;

		$.get(grid + ".jsonmeta" + "?random=" + Math.random(),function(data){
			var info = eval("(" + data + ")");
			
			
			options.columns = info.columns;
			options.url = grid + ".jsonlist";
			options.pageSize = info.pageSize;
			options.pageList = [info.pageSize];
			
			var defaults = {
				title: info.title,
				rownumbers:true,
				pagination: true,
				striped: true,
				singleSelect: true,
				collasible: true,
				nowrap:false,
				handleColumn: function(columns){},
				simpleeditors:{},
				simpleas:{}
			} ;
			
			options = $.extend(defaults, options);
			
			//生成editor
			$.each( options.simpleeditors, function(index, simpleeditor){
				$.each(options.columns[0], function(index, data){
					if( data.field == simpleeditor.field ){
						data.editor = simpleeditor.editor;
					}
				});
			});
			
			//生成超链接
			$.each( options.simpleas, function(index, simplea ){
				var i = -1;
				$.each(options.columns[0], function(index, data) {
					if( data.field == simplea.field ){
						i = index;
					}
				});
				
				
				var frmtFn = function( value, rowData, rowIndex ) {
					var _content = "";
					var _as = simplea.as;
					if( $.isFunction(_as) )
						_as = _as(rowData);
					$.each(_as, function(index, a){
						_content += '<a href="###" onclick="handleDbGridA(\'' + obj.selector + '\', @rowIndex, ' + a.handle + ')">'+a.text+'</a> ';
					});
					return _content.replace(/@rowIndex/g, rowIndex);
					return "";
				};
				if( i == -1 ) {
					options.columns[0][options.columns[0].length] = {
							field: simplea.field,
							formatter: frmtFn,
							title:simplea.text, 
							width:simplea.width
					};
				}else{
					options.columns[0][i].formatter = frmtFn;
				}
			});
			
			if( typeof(options.columnWidths) != "undefined")
			{
				$.each(options.columnWidths, function(index, value){
					if( typeof( info.columns[0][index]) !== "undefined" )
						info.columns[0][index].width=value;
				});
			}
			
			
			options.handleColumn(options.columns);
			
			$(obj).datagrid(options);
		});
	};
	
	$.fn.dbcombogrid = function(options) {
		var obj = this;
		var grid = options.grid;

		$.get("jsoncolumn.jsonmeta?dbgrid=" + grid,function(data){
			var info = eval("(" + data + ")");
			if( typeof(options.columnWidths) != "undefined")
			{
				$.each(options.columnWidths, function(index, value){
					info.columns[0][index].width=value;
				});
			}
			
			options.columns = info.columns;
			options.url = "jsonlist.jsonlist?dbgrid=" + grid;
			options.pageSize = info.pageSize;
			options.pageList = [info.pageSize];
			
			var defaults = {
				singleSelect: true,
				mode:'local'
			} ;
			
			options = $.extend(defaults, options);
			
			$(obj).combogrid(options);
		});
	};
	
	$.fn.codecomplete = function(options) {
		options.url = '/core/code/CodeComplete.action?typeCode=' 
				+ options.typeCode;
		options.valueField = 'code';
		options.textField = 'name';
		
		var defaults = {
				mode: 'local',
				typeCode:'',
				filter:function(q, rowData){
					var _q = q.trim().toUpperCase();
					var m = (rowData.mnemonic || "____").toUpperCase();
					var n = (rowData.name || "____").toUpperCase();
					console.debug("_q=" + _q + " m=" + m + " n=" + n);
					console.debug(m.indexOf(_q));
					console.debug(n.indexOf(_q));
					if( m.indexOf(_q) != -1 
							|| n.indexOf(_q) != -1 )
						return true;
					else 
						return false;
				},
				formatter: function(rowData) {
					return rowData.name + "[" + rowData.mnemonic + "]";
				}
		};
		
		options = $.extend(defaults, options);
		
		$(this).combobox(options);
		
	};
	
	$.fn.reportgrid = function(options){
		var _defaults = {
				method: 'post',
				fitColumns: true,
				singleSelect: true,
				rownumbers: true,
				showFooter: false
		};
		options = $.extend(_defaults, options);
		if( options.showFooter ){
			if( options.url.indexOf('?') == -1 ){
				options.url += "?showFooter=true";
			}else{
				options.url += "&showFooter=true";
			}
		}
		$(this).datagrid(options);
	}
	
})(jQuery);

/**
 * 打开窗口
 * argument参数暂时保留位置，无用，可暂时传入null解决。
 * 默认参数：
 * modal:true,
		resizable: false,
		minimizable: false,
		maximizable: false,
		collapsible: false,
		draggable: true
 */
$.showDialog=function (url,argument,options){
	if( url.indexOf("?") != -1 )
	{
		url += ("&random=" + Math.random());
	}else
	{
		url += ("?random=" + Math.random());
	}

	var defaults = {
		modal:true,
		resizable: false,
		minimizable: false,
		maximizable: false,
		collapsible: false,
		draggable: true
	};
	
	options = $.extend(defaults, options);
	
	if( typeof(options.title) == undefined || options.title == null || options.title == "null"){
		options.title='弹出窗口';
	}
	
	var winId = "win";
	var frameId = "frame";
	if( !(typeof(options.winId) == undefined || options.winId == null || options.winId == "null")){
		winId=options.winId;
	}
	if( !(typeof(options.frameId) == undefined || options.frameId == null || options.frameId== "null")){
		frameId=options.frameId;
	}
	if( typeof(options.parent) != undefined && options.parent != null && options.parent== true){
		options.onClose = function(){
			parent.$('#' + frameId).css('display', 'none');
			parent.$('#' + winId).css('display', 'none');
			parent.$('#' + frameId).remove();
			parent.$('#' + winId).remove();
			
		};
		parent.$('body').append("<div id='"+winId+"' class='easyui-window'><iframe id='"+ frameId +"' width='100%' height='100%' frameborder='0' scrolling='auto'></iframe></div>");
		parent.$('#' + frameId).attr('src',url);
		parent.$('#' + winId).window(options);
		parent.$('#' + winId).window('open');
		
	}else
	{
		options.onClose = function(){
			$('#' + frameId).css('display', 'none');
			$('#' + winId).css('display', 'none');
			$('#' + frameId).remove();
			$('#' + winId).remove();
			
	    };
		$('body').append("<div id='"+winId+"' class='easyui-window'><iframe id='"+ frameId +"' width='100%' height='100%' frameborder='0' scrolling='auto'></iframe></div>");
		$('#' + frameId).attr('src',url);
		$('#' + winId).window(options);
		$('#' + winId).window('open');
	}
		
	
};
function openWin(URL, width, height) {
	var top = (screen.availHeight - height - 30) / 2;
	var left = (screen.availWidth - width - 10) / 2;
	var features = "width="
			+ width
			+ "px,height="
			+ height
			+ "px,top="
			+ top
			+ "px,left="
			+ left
			+ "px,resizable=no,status=no,toolbar=no,location=no;menubar=no,directories=no,scrollbars=yes";
	if (URL.indexOf("?") == -1) {
		URL += "?Random=" + Math.random();
	} else {
		URL += "&Random=" + Math.random();
	}
	var name = URL.substr(0, URL.indexOf(".")).replace("!", "")
			.replace(/\//g, "").replace("&", "");
	win = window.open(URL, name, features, false);
	if (win == null) {
		alert("您的浏览器阻止了该程序的运行，\n请允许弹出窗口或启用intranet设置")
	}
	return win;
}
/**
 * 提示消息
 * @returns
 */
$.showMessage = function(message) {
	$.messager.show({
		title:'提示消息',
		msg:message,
		timeout:3000
	});
};


//获得当前时间字符串	
function getDateStr()
{   
	var currDate,strDate;
	var year,month,day;
	currDate = new Date();
	year = currDate.getYear();
	month = currDate.getMonth()+1;
	day = currDate.getDate();
	if(month<10)
	    month="0"+month;
	if(day<10)
	  day="0"+day;
	strDate = year+"-"+month+"-"+day; 
	return strDate;	
}

