$.fn.jqGd_defaults = {
		pgbuttons : false,
		pginput : false,
		datatype : "local",
		cache : false, 
		width : 1170,
		rownumbers : true,
		altRows : true,
		sortable : false,
		colAlign: 'center',
		simpleas:[]
};

$.fn.jqGd_optionsPool = {};

$.fn.jqGd = function(options) {
	var obj = this;
	
	options = $.extend($.fn.jqGd_defaults, 
						options);
	
	$.fn.jqGd_optionsPool[$(obj).attr("id")] = options;
	
	$.each(options.simpleas, function(index, simplea) {
		var i = -1;
		$.each(options.colModel, function(index, data) {
			if (data.title == simplea.name
					&& typeof (simplea.name) != "undefined") {
				i = index;
			}
		});
		var frmtFn = function(value, opts, rowData) {
			var _content = "";
			var _as = simplea.as;
			if ($.isFunction(_as))
				_as = _as(rowData);
			$.each(_as, function(index, a) {
				var iconHtml = "";
				if( typeof(a.icon) === "undefined" ){
					a.icon="hand-right";
				}
				iconHtml = '<i class=\'' + "icon-" + a.icon + "\'></i>";
				_content += '<a href="###" ' + a.style
						+ ' onclick="javascript:' + a.handle + '(' + rowData.id
						+ ')">' + iconHtml + a.text + '</a>    ';
			});
			return _content;
		};// frmtFn
		if (i == -1) {
			options.colModel[options.colModel.length] = {
				name : simplea.name,
				formatter : frmtFn,
				title : simplea.text,
				width : simplea.width,
				cellattr : simplea.style
			};
			options.colNames[options.colNames.length] = simplea.name;
		} else {
			options.colModel[i].formatter = frmtFn;
			options.colModel[i].cellattr = simplea.style;
		}// if
	});// $.each
	
	$.each(options.colModel, function( index, c ){
		c.align=options.colAlign;
	});
	
	console.log(options);
	
	$(obj).jqGrid(options);

};

$.fn.dbgrid = function(options) {
	var grid = options.grid;
	var obj = this;
	// 获得格式
	$.post(grid + ".jqmeta?random=" + Math.random(),
			function(data) {
				// alert(data);
				var info = eval("(" + data + ")");
				options.colModel = info.columns;
				options.pageSize = info.pageSize;
				options.pageList = [ info.pageSize ];
				options.lastpage = info.lastpage;
				options.reccount = info.recNum;
				colNames = [];
				$.each(info.columns, function(index, col) {
					colNames[index] = col.title;
				});
				options.colNames = colNames;
				// alert(data);
			});
	
	// 获得数据
	$.post(grid + ".jqlist", options.params, function(data) {
		var mydata = eval("(" + data + ")");
		// 转换前的字符数据
		var cells = [];
		// 转换后的数据
		// var pcls = [];
		var pcls = [];
		// 数据长度
		var Cl = [];
		// 清为空字符串
		for ( var k = 0; k < mydata.rows.length; k++) {
			cells[k] = "";
		}
		// 串字符串
		$.each(options.colModel, function(i, dt) {
			var dl = 0;
			var tl = 0;
			for ( var j = 0; j < mydata.rows.length; j++) {
				cells[j] += "" + dt.name + ":'" + mydata.rows[j].cell[i] + "'"
						+ ",";
				tl = lenReg("" + mydata.rows[j].cell[i]);
				if (tl > dl)
					dl = tl;
			}
			Cl[i] = dl;
		});
		// alert("("+("["+cells[0]+"]")+")");
		// 转换为json
		for ( var l = 0; l < cells.length; l++) {
			pcls[l] = eval("({" + cells[l] + "})");
		}
		// 根据数据长度修改列宽
		$.each(Cl, function(i, l) {
			options.colModel[i].width = Cl[i] + 50;
		});
		options.data = pcls;
		//
		$(obj).jqGd(options);
		
		$('.ui-jqgrid-btable').addClass('table-bordered');
		
		//分页
		var pagination = "<div class='pagination pagination-large pagination-right' id='pagination_" + $(obj).attr("id") + "'><ul>";
		pagination += "<li><a href='###' >数据总数：" + mydata.records + "</a></li>";  
		
		var currentPage = parseInt(mydata.page);
		var total = parseInt(mydata.total);
		if( total > 1 ){//只有总页数大于1的时候才生成分页
			//生成“上一页”
			if( currentPage === 1 )
				pagination += "<li class='disabled'><a href='###' >上一页</a></li>";
			else
				pagination += "<li><a href='###' onclick='$(\"#" + $(obj).attr("id") + "\").dbgridGetPage(" + (currentPage-1) + ")'>上一页</a></li>";
			//生成每一页
			var pageNum = 3;
			//生成currentPage之前的部分
			if( currentPage <= pageNum+1 ){
				for( i = 1; i<currentPage; i++ )
					pagination += "<li><a href='###' onclick='$(\"#" + $(obj).attr("id") + "\").dbgridGetPage(" + i + ")'>" + i + "</a></li>";
			}else{
				pagination += "<li><a href='###' onclick='$(\"#" + $(obj).attr("id") + "\").dbgridGetPage(1)'>1..</a></li>";
				for( i = currentPage-pageNum; i<currentPage; i++ )
					pagination += "<li><a href='###' onclick='$(\"#" + $(obj).attr("id") + "\").dbgridGetPage(" + i + ")'>" + i + "</a></li>";
			}
			//生成当前页
			pagination += "<li class='active'><a href='###'>" + currentPage + "</a></li>";
			
			//生成当前页之后的部分
			if( currentPage >= total-pageNum+1 ){
				for( i = currentPage+1; i<=total; i++ )
					pagination += "<li><a href='###' onclick='$(\"#" + $(obj).attr("id") + "\").dbgridGetPage(" + i + ")'>" + i + "</a></li>";
			}else{
				for( i = currentPage+1; i<=currentPage+pageNum; i++ )
					pagination += "<li><a href='###' onclick='$(\"#" + $(obj).attr("id") + "\").dbgridGetPage(" + i + ")'>" + i + "</a></li>";
				pagination += "<li><a href='###' onclick='$(\"#" + $(obj).attr("id") + "\").dbgridGetPage(" + total + ")'>.." + total + "</a></li>";
			}
			
			//生成“下一页”
			if( currentPage === parseInt(mydata.total) )
				pagination += "<li class='disabled'><a href='###' >下一页</a></li>";
			else
				pagination += "<li><a href='###' onclick='$(\"#" + $(obj).attr("id") + "\").dbgridGetPage(" + (currentPage+1) + ")'>下一页</a></li>";
			
		}
		
		pagination +="</ul></div>" ;
		$('#gbox_' + $(obj).attr("id")).after(pagination);
			
	});

};

$.fn.dbgridClear = function(){
	$(this).empty();
	var clone = $(this).clone();
	var tempDivId = "temp_" + this.attr("id");
	$('#gbox_' + $(this).attr("id")).before("<div id='" + tempDivId + "'></div>");
	$('#gbox_' + $(this).attr("id")).remove();
	$('#pagination_' + $(this).attr("id")).remove();
	$("#" + tempDivId).after(clone);
	$("#" + tempDivId).remove();
};

$.fn.dbgridReload = function(params) {
	$(this).dbgridClear();
	var oldOptions = $.fn.jqGd_optionsPool[$(this).attr("id")];
	if(typeof(oldOptions) === "undefined "){
		console.log('no dbgrid cache');
		return;
	};
	
	oldOptions.params = {};
	$.each(params, function(index, data){
		console.log( index + " " + data + ": " + params[data]);
		if( typeof params[index] !== "undefined" && params[index] !== "" ){
			oldOptions.params[index] = params[index];
		}
	});
	
	$.fn.jqGd_optionsPool[$(this).attr("id")] = oldOptions;
	
	$("#" + $(this).attr("id")).dbgrid(oldOptions);
	
};

$.fn.dbgridGetPage = function(page) {
	$(this).dbgridClear();
	
	var oldOptions = $.fn.jqGd_optionsPool[$(this).attr("id")];
	if(typeof(oldOptions) === "undefined "){
		console.log('no dbgrid cache');
		return;
	};
	if( typeof(oldOptions.params) === "undefined"){
		oldOptions.params = {};
	}
	oldOptions.params = $.extend(oldOptions.params, {'page':page});
	$("#" + $(this).attr("id")).dbgrid(oldOptions);
	
};
// 检测字符长度
function lenReg(str) {
	return str.replace(/[^\x00-\xFF]/g, '**').length;
};