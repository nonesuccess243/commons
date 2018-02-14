package com.wayeasoft.springmvc.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import com.younker.waf.db.DataSourceProvider;

@Service
public class WebInitService  implements ApplicationListener<ContextRefreshedEvent>
{
        @Autowired
        private DataSource dataSource;

        @Override
        public void onApplicationEvent(ContextRefreshedEvent event)
        {
                if( dataSource == null )
                {
                        throw new RuntimeException();
                }
                
                DataSourceProvider.initInstance(dataSource);
                
        }

}
