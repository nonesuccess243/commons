	var clz;
	var u;
	
	var example = new Vue({
		el : '#dbgrid',
		data : 
		{
			jsonlist : [],
			meta : [],
			title : ""
		}
	});
	
	$(document).ready(function(){
		u = GetQueryString("url");
		clz = GetQueryString("clz");
		getData(u);
	});
	
	function getData(u){
		var url = u+".jsonmeta";
		$.get(url,function(result){
			console.log(result);
			result = eval("("+result+")");
			var data = result.columns[0];
			for(k = 0;k<data.length;k++){
				example.meta.push(data[k]);
			}
			example.title = result.title+"";
			url = u+".jsonlist";
			$.getJSON(url,function(result){
				console.log(result);
				var data = result.rows;
				for(k=0;k<data.length;k++){
					example.jsonlist.push(data[k]);
				}
			}); 
			
		});
	}
	
	function deleteFt(id){
		var url = "/public/delete.do?clz="+clz+"&id="+id;
		$.get(url,function(data){
			getData(u);
			alertMsg("d");
		});
	}