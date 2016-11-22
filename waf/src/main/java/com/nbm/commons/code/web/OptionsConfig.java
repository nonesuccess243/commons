package com.nbm.commons.code.web;

import java.util.MissingResourceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OptionsConfig
{

        private static Logger log = LoggerFactory.getLogger(OptionsConfig.class);

        public static String getString(String key)
        {
                try
                {

                        return CodeSQLConfig.getInstance().getString(key);
                } catch (MissingResourceException e)
                {
                        return '!' + key + '!';
                }
        }
}
