package com.nbm.commons.db.meta;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class UnderlineCamelConverterTest
{

	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception
	{
	}

	@Before
	public void setUp() throws Exception
	{
	}

	@After
	public void tearDown() throws Exception
	{
	}

	@Test
	public void test()
	{
		assertEquals("somejavaname", UnderlineCamelConverter.INSTANCE.javaName2PackageName("someJavaName"));
		assertEquals("somejavaname", UnderlineCamelConverter.INSTANCE.javaName2PackageName("some_Java_Name"));
		assertEquals("somejavaname2", UnderlineCamelConverter.INSTANCE.javaName2PackageName("someJavaName2"));
		assertEquals("com.com2.com3.javaname.somename", UnderlineCamelConverter.INSTANCE.javaName2PackageName("com.com2.com3.javaName.someName"));
		
		
		
		assertEquals("some_java_name", UnderlineCamelConverter.INSTANCE.javaName2WebPath("someJavaName"));
		assertEquals("_some_java_name", UnderlineCamelConverter.INSTANCE.javaName2WebPath("_someJavaName"));
	}
	
	@Test
	public void testJavaPropertyName2JavaTypeName()
	{
	        assertEquals("ModelDriven", UnderlineCamelConverter.INSTANCE.javaPropertyName2JavaTypeName("modelDriven"));
	}
	
	

}
