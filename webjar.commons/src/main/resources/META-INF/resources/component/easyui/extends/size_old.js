//定义可见区域大小的工具函数

var _sMinWidth=650;
var _sMinHeight=400;

function getClientWidth(){
	return Math.max(document.body.clientWidth,_sMinWidth);
}

function getClientHeight(){
	return Math.max(document.body.clientHeight,document.body.scrollHeight,document.documentElement.clientHeight,_sMinHeight);
}
var clientWidth = getClientWidth()-2;
var clientHeight = getClientHeight();
