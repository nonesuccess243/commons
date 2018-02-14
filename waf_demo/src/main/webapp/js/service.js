//url传参获取
function GetQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if (r != null)
		return unescape(r[2]);
	return null;
}

//获取当前时间
function getTime() {
	var time = new Date();
	var m = time.getMonth() + 1;
	var t = time.getFullYear() + "年" + m + "月" + time.getDate() + "日"
			+ time.getHours() + ":" + time.getMinutes() + ":"
			+ time.getSeconds();
	return t;
}

function getTimeByStr(nS) {     
	   return new Date(parseInt(nS) * 1000).toLocaleString().replace(/:\d{1,2}$/,' ');     
}
