//package com.nbm.commons;
//
//import static org.junit.Assert.*;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.Set;
//
//import org.junit.After;
//import org.junit.AfterClass;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.google.common.reflect.ClassPath;
//
//public class PackageUtilsTest
//{
//	
//	private final static Logger log = LoggerFactory.getLogger(PackageUtilsTest.class);
//	
//
//	@BeforeClass
//	public static void setUpBeforeClass() throws Exception
//	{
//	}
//
//	@AfterClass
//	public static void tearDownAfterClass() throws Exception
//	{
//	}
//
//	@Before
//	public void setUp() throws Exception
//	{
//	}
//
//	@After
//	public void tearDown() throws Exception
//	{
//	}
//
//	@Test
//	public void testGetClassName()
//	{
//		List<String> result = PackageUtils.getClassName("com.nbm.commons.packagetest");
//		
//		PackageUtils.getClassName(".", true);
//		
//		PackageUtils.getClassName(".", false);
//		
//	}
//
//	@Test
//	public void testGetClassNameStringBoolean()
//	{
//	}
//
//	@Test
//	public void testGetClassesStringBoolean()
//	{
//		List<Class<?>> result = PackageUtils.getClasses("com.nbm.commons.packagetest");
//		
//		assertEquals(6, result.size());
//		
//		result = PackageUtils.getClasses("com.nbm.commons.packagetest", true);
//		
//		assertEquals(6,  result.size());
//		
//		
//		result = PackageUtils.getClasses("com.nbm.commons.packagetest", false);
//		assertEquals("3", result.size());
//		
//		
//	}
//
//	@Test
//	public void testGetClassesString() throws IOException
//	{
//		ClassLoader cl = getClass().getClassLoader();
//		    Set<ClassPath.ClassInfo> classesInPackage = ClassPath.from(cl).getTopLevelClassesRecursive("com.nbm.commons");
//		    
//		    log.debug("" + classesInPackage.size());
//		    
//		    for( Object o : classesInPackage)
//		    {
//			    log.debug(o.toString());
//		    }
//		    
//	}
//
//}
