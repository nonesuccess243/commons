	var example = new Vue({
		el : '#module',
		data : 
		{
			module : []
		}
	});
	
	var subsyscode;
	
	$(function(){
		//熊伟写的不明白啥意思保留
		$(".jump").css("cursor","pointer");
		$(".jump").click(function(){
			$("#content").attr("src",$(this).attr("jump"));
			var text = $(this).parent().find("span").text();
			$("#left-up").text(text);
		});
		
		//获取菜单部分
		subsyscode = GetQueryString("subsyscode");
		getModuleBySubsyscode(subsyscode);
	});
	
	function getModuleBySubsyscode(subsyscode){
		var url = "/public/"+subsyscode+"/modules";
		$.get(url,function(result){
			console.log(result);
			var modules = result.grantedModules;
			for(var k=0;k<modules.length;k++){
				example.module.push(modules[k]);
			}
		})
	}