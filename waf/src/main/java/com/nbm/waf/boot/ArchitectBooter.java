package com.nbm.waf.boot;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.nbm.commons.db.meta.UnderlineCamelConverter;
import com.younker.waf.utils.StringUtil;

/**
 * 根据一个简单的定义文件，创建项目子系统、一级目录、二级目录所有元素相关结构的工具类
 * @author niyuzhe
 *
 */
public enum ArchitectBooter
{
	INSTANCE;
	
	private final static Logger log = LoggerFactory.getLogger(ArchitectBooter.class);
	
	private final static String FILE_NAME = "webmeta.json";
	
	public void boot() throws Exception
	{
		Map<String, ?> resultMap = readAndGetMap();
		
		generateJavaPackage(resultMap);
		generateInitSqlFile(resultMap);
		
		
	}

	private void generateJavaPackage(Map<String, ?> resultMap) throws IOException
	{
		String packagePrefix = 
				UnderlineCamelConverter.INSTANCE.javaName2PackageName(resultMap.get("group").toString())
				+ "." + UnderlineCamelConverter.INSTANCE.javaName2PackageName(resultMap.get("name").toString());
		
		System.out.println(resultMap.get("subsys").getClass());
		
		for( Map subsys : (List<Map>)resultMap.get("subsys"))
		{
			String subsysCode = subsys.get("code").toString();
			for( Map module : (List<Map>)subsys.get("module"))
			{
				String moduleCode = module.get("code").toString();
				for( Map moduleItem : (List<Map>)module.get("moduleItem"))
				{
					String moduleItemCode = moduleItem.get("code").toString();
					
					//generate package folder
					Path packagePath = Paths.get("./src/main/java/" + packagePrefix.replace(".", "/") + "/"
							+ UnderlineCamelConverter.INSTANCE.javaName2PackageName(subsysCode) + "/"
							+ UnderlineCamelConverter.INSTANCE.javaName2PackageName(moduleCode) + "/"
							+ UnderlineCamelConverter.INSTANCE.javaName2PackageName(moduleItemCode) );
					log.debug(packagePath.toFile().getAbsolutePath());
					packagePath.toFile().mkdirs();
					
					String path = "./src/main/webapp/" 
							+ UnderlineCamelConverter.INSTANCE.javaName2WebPath(subsysCode) + "/"
							+ UnderlineCamelConverter.INSTANCE.javaName2WebPath(moduleCode) + "/"
							+ UnderlineCamelConverter.INSTANCE.javaName2WebPath(moduleItemCode) ;
					
					//generate web folder and web file
					
					
					Path webPath = Paths.get("./src/main/webapp/" 
							+ UnderlineCamelConverter.INSTANCE.javaName2WebPath(subsysCode) + "/"
							+ UnderlineCamelConverter.INSTANCE.javaName2WebPath(moduleCode) + "/"
							+ UnderlineCamelConverter.INSTANCE.javaName2WebPath(moduleItemCode) );
					
					log.debug("webpath={}", webPath.toFile().getAbsolutePath());
					webPath.toFile().mkdirs();
					
					
				}
			}
		}
	}
	private void generateInitSqlFile(Map<String, ?> resultMap) throws IOException
	{
		String packagePrefix = 
				UnderlineCamelConverter.INSTANCE.javaName2PackageName(resultMap.get("group").toString())
				+ "." + UnderlineCamelConverter.INSTANCE.javaName2PackageName(resultMap.get("name").toString());
		
		System.out.println(resultMap.get("subsys").getClass());
		
		for( Map subsys : (List<Map>)resultMap.get("subsys"))
		{
			String subsysCode = subsys.get("code").toString();
			Paths.get("./sql").toFile().mkdirs();
			
			Path sqlFile = Paths.get("./sql/init_module.sql");
			
			try(BufferedWriter writer = Files.newBufferedWriter(sqlFile, StandardCharsets.UTF_8, StandardOpenOption.CREATE))
			{
				writer.write(StringUtil.getString(
						"INSERT INTO S_SUBSYS(ID,CODE,NAME,ABBR,WELCOME_PAGE,ORDERBY) VALUES ({}, {}, {}, {}, {},{}) "
						, 1212
						,subsysCode,
						subsys.get("name"),
						subsys.get("name").toString().substring(0,1),
						"",
						""
						));
			}
			
			
			for( Map module : (List<Map>)subsys.get("module"))
			{
				String moduleCode = module.get("code").toString();
				for( Map moduleItem : (List<Map>)module.get("moduleItem"))
				{
					String moduleItemCode = moduleItem.get("code").toString();
					
					
					
					
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
}
