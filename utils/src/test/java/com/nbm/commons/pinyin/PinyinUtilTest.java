package com.nbm.commons.pinyin;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PinyinUtilTest
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

//	@Test
//	public void test()
//	{
//		fail("Not yet implemented");
//	}

	@Test
	public void testHanziToPinyinString()
	{
		String pinyin = PinyinUtil.hanziToPinyin("刘刚", "");
		assertEquals("liugang", pinyin);
	}

	@Test
	public void testGetHeadByStringLower()
	{
		String[] pinyin = PinyinUtil.getHeadByString("刘刚", false);
		assertEquals("lg", PinyinUtil.stringArrayToString(pinyin));
	}

}
