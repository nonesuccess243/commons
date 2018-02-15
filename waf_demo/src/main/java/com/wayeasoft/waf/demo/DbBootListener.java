package com.wayeasoft.waf.demo;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.nbm.waf.boot.DbBooter;
import com.younker.waf.db.DataSourceProvider;

@Component
public class DbBootListener 
{
        
        private final static Logger log = LoggerFactory.getLogger(DbBootListener.class);
        

        @Autowired
        private DbBooter dbBooter;

        @Autowired
        private DataSource dataSource;


        @EventListener({ContextRefreshedEvent.class})
        public void onApplicationEvent() throws Exception
        {
                
                DataSourceProvider.initInstance(dataSource);
                
                dbBooter.boot();
        }

}
