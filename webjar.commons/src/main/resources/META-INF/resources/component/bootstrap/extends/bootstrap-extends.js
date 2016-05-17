//对bootstrap的扩展，主要目的是简化前台开发代码量，统一设置前台样式
//依赖jquery

$(function() {

	$('body').addClass("container");

	// 生成每个info-section的样式和标题
	$(".info-section").each(
			function(index, data) {
				if ($(data).data("title") != ""
						&& typeof ($(data).data("title")) != "undefined") {
					$(data).prepend(
							"<h2 class='info-section-title'>"
									+ $(data).data("title") + "</h2");
				}
				$(data).append("<hr/>");

			});

	// 处理field-group
	$(".field-group").each(
			function(index, data) {
				var htmlContent = data.innerHTML;
				data.innerHTML = "";
				var div = $("<div class='field-group-border'></div>");
				$(data).prepend(div);
				$(div).prepend(htmlContent);
				if ($(data).data("title") != ""
						&& typeof ($(data).data("title")) != "undefined") {
					$(data).prepend(
							"<div class='field-group-title'>"
									+ $(data).data("title") + "</div");
				}

			});

	// 在body定义了属性navigator值为true的情况下生成导航条
	if ($("body").attr("navigator") == "true") {
		$('body').css("padding-top", "50px");
		$('body').css("position", "relative");
		var html = '<div class="navbar navbar-fixed-top"><div class="navbar-inner"><div class="container"><a class="brand" href="#">'
				+ $('title').text()
				+ '</a><div class="nav-collapse"><ul class="nav">';
		$("section").each(
				function(index, data) {
					html += '<li class=""><a href="#' + data.id + '">'
							+ $(data).data("title") + '</a></li>';
				});
		html += '</ul></div></div></div></div>';
		$('body').prepend(html);
	}

	// 设置info-table的class。info-table用于大量信息显示、需要用表格布局的页面
	$('.info-table').addClass("table table-striped table-bordered");

	$('.grid-search').addClass("table");

	$(".tooltipable").each(function(index, data) {
		$(this).tooltip({
			html : true
		});
	});

	if (window !== window.top) {
		// 将内层的click传到top，以支持dropdown-menu
		$(document).click(function() {
			$(window.top.document.body).click();
		});
	}

});
