package com.wayeasoft.core.configuration;

import static org.junit.Assert.*;

import org.apache.commons.configuration2.ex.ConversionException;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CfgTest
{
        private final static Logger log = LoggerFactory.getLogger(CfgTest.class);

        
        @Test
        public void testGet()
        {
                //具体内容见本项目测试包下的appconfig.properties
                assertEquals("string_value", Cfg.I.get("stringkey", String.class, "string_default_value"));
                assertEquals("string_default_value_not_exist", Cfg.I.get("stringkeynotexist", String.class, "string_default_value_not_exist"));
                assertArrayEquals(new String[] {"a","b","c"}, Cfg.I.get("stringarraykey", String[].class));
                
                assertEquals(Integer.valueOf(1), Cfg.I.get("intkey", Integer.class));
                
                try
                {
                        assertEquals(Integer.valueOf(1), Cfg.I.get("stringkey", Integer.class, Integer.valueOf(1)));
                }catch(ConversionException e)
                {
                        //no code here 
                }
                
        }
        @Test
        public void testConfigFileNotExist()
        {
                Cfg.I.warmUp("not_exist.properties");
                assertEquals("string_default_value", Cfg.I.get("stringkey", String.class, "string_default_value"));
                assertEquals("string_default_value_not_exist", Cfg.I.get("stringkeynotexist", String.class, "string_default_value_not_exist"));
                assertArrayEquals(new String[] {"a","b","c"}, Cfg.I.get("stringarraykey", String[].class,new String[] {"a","b","c"}));
                
        }
        
        @Test
        public void testGetAll()
        {
                log.debug(Cfg.I.getAllDeclared().toString());
        }

}
