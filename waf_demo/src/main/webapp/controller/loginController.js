	var example = new Vue({
		el : '#subsys',
		data : 
		{
			subsys : []
		}
	});
	
	var subsyscode;
	
	$(function(){
		getSubsys();
	});
	
	function getSubsys(){
		var url = "/public/subsyss";
		$.get(url,function(result){
			console.log(result);
			for(var k=0;k<result.length;k++){
				example.subsys.push(result[k]);
			}
		})
	}
	
	function login(){
		var code = $("#subsys").val();
		location.href="index.html?subsyscode="+code;
	}