    /** Dauphin - Premium Responsive Bootstrap Admin Template **/

    "use strict"

    //Shrink/enlarge left navigation menu if width < 1024px 
    if ($(window).width() < 768) {
        $("body").removeClass("left-nav-minimized");
    } else if ($(window).width() < 1024) {
        $("body").addClass("left-nav-minimized");
    } else {
        $("body").removeClass("left-nav-minimized");
    }

    //Document ready functions
    $(document).ready(function () {

        // Additional optional toggle (only for preview version)
        $('.fa-gear', '.add-opt').on('click', function(){
            $('.add-opt').toggleClass('expanded');
            $(this).toggleClass('fa-spin');
        });

        $('.color-option', '.add-opt').on('click', function(){
            var dataToggle = $(this).attr('data-toggle'),
                dataColor  = $(this).attr('data-color'),
                dataColorSec = $(this).attr('data-colorSec'),
                dataTheme = $(this).attr('data-theme');

            $(this).addClass('active');
            $(this).siblings().removeClass('active');

            $('head link.theme').attr('href', 'styles/' + dataTheme);
            $('.body-class', '.add-opt').html(dataTheme);
            $('.switch', '.add-opt').attr('class', 'switch switch-sm ' + dataColorSec + '');

            if ($(this).attr('data-color') == 'dark' || $(this).attr('data-color') == 'white-dark') {
                $('.body-class', '.add-opt').css('color', '#b3c6d7');
            } else {
                $('.body-class', '.add-opt').css('color', dataToggle);
            }

            $('.add-opt').css('background-color', dataToggle);
        });

        $('input', '#navbgAdd').on('click', function() {
            if ($(this).is(':checked')) {
                $('body').removeClass('no-bg-img');
            } else {
                $('body').addClass('no-bg-img');
            }
        });

        $('input', '#timeDate').on('click', function() {
            if ($(this).is(':checked')) {
                $('.time', '.navigation').show();
            } else {
                $('.time', '.navigation').hide();
            }
        });

        $('input', '#scrollUp').on('click', function() {
            if ($(this).is(':checked')) {
                $('.scroll-up').show();
            } else {
                $('.scroll-up').hide();
            }
        });

        $('input', '#footerAdd').on('click', function() {
            if ($(this).is(':checked')) {
                $('.footer').show();
            } else {
                $('.footer').hide();
            }
        });

        //Shrink/enlarge left navigation menu if width < 1024px (on resize)
        $(window).on('resize', function() {
            if ($(window).width() < 768) {
                $("body").removeClass("left-nav-minimized");
            } else if ($(window).width() < 1024) {
                $("body").addClass("left-nav-minimized");
            } else {
                $("body").removeClass("left-nav-minimized");
            }
        });

        // Remove transition hold (fix IE rendering bug)
        $('body').removeClass('hold-transition');


        // Breadcrumb slide in effect
        function breadcrumb(){
            $('.breadcrumb li:last-child a', '.navbar').css({'opacity' : '1', 'margin-left' : "0px"});
        }; 

        // Call breadcrumb slide in effect
        $('body').delay(200).queue(function(){
            breadcrumb();
            $(this).dequeue();
        });

        // Toggle left navigation menu
        $('.left-nav-toggle', '.navigation').on('click', function(event){
            event.preventDefault();
            $("body").toggleClass("left-nav-minimized");
        });

        // Set left navigation menu item active on page load/refresh
        var checkActive = function(){
            var path = window.location.href,
                path = decodeURI(path),
                firstIndex = path.lastIndexOf('/'),
                path = path.substring(firstIndex, path.length).slice(1)

            if ( window.location.pathname.indexOf(path) > -1 ) {
                $('.navigation .nav li a[href="'+path+'"]').parent().addClass('active');
                $('.navigation .nav li a:not([href="'+path+'"])').parent().removeClass('active');
            }
        };

	    checkActive();   

        // Keep left navigation menu item expanded and active if child is active;
        var primaryLi = function() {
	        if ($('.primary li', '.navigation').hasClass('active')) {
	        	$('.primary li.active', '.navigation').parentsUntil('.primary').addClass('in');
	        }
	    };

		primaryLi();

        // Sidebar time
        if ($('.time', '.navigation').length > 0) {
            var datetime = null,
            	datetime1 = null,
                date = null;
            var update = function () {
                var d = new Date(),
                date = moment(new Date());
                datetime.html(date.format('YYYY-MM-DD'));
            };
            datetime = $('.current-time')
            update();
            setInterval(update, 60000);

            var update = function () {
                var date = moment(new Date());
                datetime1.html(date.format('HH:mm:ss'));
            };
            datetime1 = $('.current-time2')
            update();
            setInterval(update, 1000);

            $('.time', '.navigation').on('click', function(){
                $(this).toggleClass('non-visible');
            });
        }


        //Scroll up button
        var scrollUp = $('.scroll-up');

        if (scrollUp.length > 0) {
            $(document).scroll(function(){
                if($('body').scrollTop() > 200) {
                    scrollUp.addClass('active');
                    scrollUp.on('click', function(){
                        $('body').stop().animate({scrollTop:0}, 150, 'swing');
                    });
                } else {
                    scrollUp.removeClass('active');
                }
            });
        }


        // Panels drag, collapse and close; 
        // Messages categories toggle active, New message create and close 
        var panelActions = function(){

	        // Toggle Panel
	        $('.panel-toggle').on('click', function(event){
	            event.preventDefault();
	            var panel = $(event.target).closest('div.panel');
	            var icons = $(event.target).closest('i');
	            var body = panel.find('div.panel-body');
	            var footer = panel.find('div.panel-footer');
	            body.slideToggle(200);
	            footer.slideToggle(100);

	            // Toggle icon from up to down
	            icons.toggleClass('fa-chevron-up').toggleClass('fa-chevron-down');
	            panel.toggleClass('').toggleClass('panel-collapse');
	            setTimeout(function () {
	                panel.resize();
	            }, 15);
	        });

	        // Close Panel
	        $('.panel-close').on('click', function(event){
	            event.preventDefault();
	            var panel = $(event.target).closest('div.panel');
	            panel.remove();
	        });

	        // Draggable panels

	        var element = ".draggable";
	        var handle = ".panel-heading";
	        var connect = ".draggable";
	        $(element).sortable(
	                {
	                    handle: handle,
	                    connectWith: connect,
	                    tolerance: 'pointer',
	                    forcePlaceholderSize: true,
	                    opacity: 0.8,
	                    placeholder: 'sortable-placeholder',
			            start: function(e, ui){
				                if($(this).children().hasClass('panel')){
				                	$('.sortable-placeholder').css('margin-bottom', '20px');
				                } else {
				                    $('.sortable-placeholder').html($(this).children());
				                }
			            	}
			            })
	                .disableSelection();

            // Messages categories toggle
            $(".inbox-categories div", '.messages-panel').on('click', function(){
                $(this).addClass('active');
                $(this).siblings().removeClass('active');
            });

            // New message show and close 
            $('.new-message', '.messages-panel').on('click', function(){
                $(this).next('.new-message-wrapper').show();
            });

            $('.close-new-message', '.messages-panel').on('click', function(){
                event.preventDefault();
                var panel = $(event.target).closest('.new-message-wrapper');
                panel.hide();
	        });
        };

        panelActions();

        //Timeline collapse hide/show middle line
        $('.timeline-content.collapse:not(.in)').closest('.timeline').addClass('no-line');

        $('.timeline-heading', '.timeline').on('click', function(){
            if ($(this).closest('.timeline').hasClass('no-line')){
                $(this).closest('.timeline').removeClass('no-line');
            } else {
                $(this).closest('.timeline').addClass('no-line');
            }
        });

        //Button to previous page
        $('.previous-page').click(function(){
            parent.history.back();
            return false;
        });
    });
