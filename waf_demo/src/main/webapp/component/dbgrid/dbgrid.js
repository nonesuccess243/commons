var _sMinWidth=650;
var _sMinHeight=400;
var $clientSize = {};
var $mainContentWidth;
var $mainContentHeight;
var $dbgridWidth;
var $dbgridHeight;

function getClientWidth(){
	return Math.max(document.body.clientWidth,_sMinWidth);
}

function getClientHeight(){
	return Math.max(document.body.clientHeight,document.body.scrollHeight,document.documentElement.clientHeight,_sMinHeight);
}

