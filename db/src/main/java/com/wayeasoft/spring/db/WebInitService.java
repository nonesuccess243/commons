//package com.wayeasoft.spring.db;
//
//import javax.sql.DataSource;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationListener;
//import org.springframework.context.event.ContextRefreshedEvent;
//import org.springframework.stereotype.Service;
//
//import com.younker.waf.db.DataSourceProvider;
//
///**
// * 利用spring的机制，在应用启动时执行，用于初始化传统的DataSourceProvider类
// * 
// * @see DataSourceProvider
// * @author niyuzhe
// *
// */
//@Service
//public class WebInitService implements ApplicationListener<ContextRefreshedEvent>
//{
//        @Autowired
//        private DataSource dataSource;
//
//        @Override
//        public void onApplicationEvent(ContextRefreshedEvent event)
//        {
//                if( dataSource == null )
//                {
//                        throw new RuntimeException();
//                }
//                
//                DataSourceProvider.initInstance(dataSource);
//                
//        }
//
//}
