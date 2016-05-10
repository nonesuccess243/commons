package com.nbm.commons.config;

public class NoSuchConfigInfoException extends java.lang.Exception
{

        /**
         * 
         */
        private static final long serialVersionUID = 2794992195347770369L;

        private String key;

        public NoSuchConfigInfoException(String key)
        {
                super("No such config info[key=" + key + "].");
                this.key = key;
        }

        /**
         * @return the key
         */
        public String getKey()
        {
                return key;
        }

        /**
         * @param key
         *                the key to set
         */
        public void setKey(String key)
        {
                this.key = key;
        }

}
