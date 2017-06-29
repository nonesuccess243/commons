package com.wayeasoft.test.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;


/**
 * spring配置类 
 * @author niyuzhe
 *
 */
@Configuration
@ImportResource("classpath:spring-config.xml")
@ComponentScan(basePackages="com.wayeasoft")
public class RootConfig
{
        

}
