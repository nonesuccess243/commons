package com.younker.waf.dbgrid;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.jar.JarFile;

import javax.servlet.ServletContext;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.xmlrules.DigesterLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nbm.exception.NbmBaseRuntimeException;

/**
 * 与读取xml并生成dbgrid对象的操作，主要封装apache degister包的相关技术。 采用Singleton模式实现。
 */
public class DBGridGenerator
{
	private final static Logger log = LoggerFactory
	                .getLogger(DBGridGenerator.class);

	// public static ServletContext ctx;

	/**
	 * 尝试将配置文件放到classpath下，不再需要servlet运行时的信息
	 * 
	 * @param ctx
	 */
	@Deprecated
	public static void setCtx(ServletContext ctx)
	{
		// DBGridGenerator.ctx = ctx;
	}

	private static DBGridGenerator _instance = new DBGridGenerator();

	public static DBGridGenerator getInstance()
	{
		return _instance;
	}

	private DBGridGenerator()
	{
	}

	/**
	 * 根据DBGridRule.xml得到apache degister的Digester对象。</br>
	 * 如果一个Degister对象正在处理配置文件过程中时，此Degister对象不能再对其他配置文件进行读取工作，会抛出异常FWK005
	 * parse may not be called while parsing。
	 * 所以封装此函数，根据rule文件得到Digester，在处理配置文件的import元素时
	 * ，每读取到一个import元素则重新生成一个Degister对象，读取相应的配置文件进行处理。</br>
	 * 
	 * 在调试阶段，DbgridRule.xml文件的位置在/WEB-INF/config/DBGridRule.xml，打包之后，
	 * 该文件将被打包在jar包根路径下。因此需要不同的配置文件读取方式。
	 * 先试图从jar包中获取rule文件，如果获取失败，则到WEB-INF/config/DBGridRule.xml读取配置文件。
	 * 
	 * @return
	 */
	private Digester getDigester()
	{
//		Digester digester = null;
//		String currentJarPath = DBGridServlet.class
//		                .getProtectionDomain().getCodeSource()
//		                .getLocation().getFile();
//		JarFile currentJar;
		/*
		 * try { currentJar = new JarFile(currentJarPath); JarEntry
		 * dbEntry = currentJar.getJarEntry("DBGridRule.xml");
		 * InputSource rule = new
		 * InputSource(currentJar.getInputStream(dbEntry)); digester =
		 * DigesterLoader.createDigester(rule); } catch
		 * (java.util.zip.ZipException e) {
		 */
		try
		{

			Digester digester = DigesterLoader.createDigester(this
			                .getClass().getClassLoader()
			                .getResource("DBGridRule.xml").toURI()
			                .toURL());
			return digester;
		} catch (Exception ex)
		{
			log.error("load or parse the dbgrid configuration file error",
			                ex);
			throw new NbmBaseRuntimeException("load or parse the dbgrid configuration file error",
			                ex);
		}
		/*
		 * } catch (IOException e) {
		 * log.error("generator digester error", e); }
		 */
		
	}

	/**
	 * 给定配置文件的路径，生成DBGrids对象。
	 * 
	 * @param filePath
	 * @return
	 */
	public DBGrids getDBGrids(String filePath)
	{
//		log.debug(filePath);
//		URL configFile = null;
//		try
//		{
//			configFile = ctx.getResource(filePath);
//		} catch (MalformedURLException e)
//		{
//			log.error("getResource[dbgrid config file] error[filePath="
//			                + filePath + "].", e);
//			return null;
//		}
//
//		if (configFile == null)
//		{
//			log.error("找不到配置文件[configFile=" + configFile + "].");
//			return null;
//		}
		
		
		URL resource = this
		                .getClass().getClassLoader()
		                .getResource(filePath);
		
		if( resource == null )
		{
			throw new NbmBaseRuntimeException("config file not exist").set("configFilePath", filePath);
		}
		
		try
		{
			
			
			
			String absolute = resource.toURI().toString();

			DBGrids dbGrids = null;
			dbGrids = (DBGrids) getDigester().parse(absolute);
			return dbGrids;
		} catch (Exception e)
		{
			log.error("parse dbgrid configuration file error[configFile="
			                + filePath + "].", e);
			
			throw new NbmBaseRuntimeException("parse dbgrid configuration file error[configFile="
			                + filePath + "].", e);
			
		}
		
	}
}
