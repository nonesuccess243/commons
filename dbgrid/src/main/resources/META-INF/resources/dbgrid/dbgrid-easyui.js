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
	
})(jQuery);
