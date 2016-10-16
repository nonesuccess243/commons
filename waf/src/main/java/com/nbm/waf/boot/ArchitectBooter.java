package com.nbm.waf.boot;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

/**
 * 根据一个简单的定义文件，创建项目子系统、一级目录、二级目录所有元素相关结构的工具类
 * @author niyuzhe
 *
 */
public enum ArchitectBooter
{
	INSTANCE;
	
	private final static String FILE_NAME = "webmeta.json";
	
	public void boot() throws Exception
	{
		Map<String, ?> resultMap = readAndGetMap();
		
		String packagePrefix = resultMap.get("group") + "." + resultMap.get("name");
		System.out.println(packagePrefix);
		
		System.out.println(resultMap.get("subsys").getClass());
		
		for( Map subsys : (List<Map>)resultMap.get("subsys"))
		{
			System.out.println(subsys);
			
			for( Map module : (List<Map>)subsys.get("module"))
			{
				for( Map moduleItem : (List<Map>)module.get("moduleItem"))
				{
					System.out.println(moduleItem);
				}
			}
		}
		
		
	}

	private Map<String, ?> readAndGetMap() throws IOException, URISyntaxException
	{
		String result = new String(Files.readAllBytes
				(Paths.get(
						this.getClass().getClassLoader().getResource(FILE_NAME).toURI())));
		
		Gson gson = new Gson();
		Map<String, ?> resultMap = gson.fromJson(result, Map.class);
		return resultMap;
	}
	
	public static void main( String []args ) throws Exception
	{
		INSTANCE.boot();
	
	}

}
