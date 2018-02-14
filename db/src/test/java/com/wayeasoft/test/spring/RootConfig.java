package com.wayeasoft.test.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


/**
 * spring配置类 
 * @author niyuzhe
 *
 */
@Configuration
@ImportResource("classpath:spring-config.xml")
@ComponentScan(basePackages= {"com.wayeasoft", "com.nbm"})
@PropertySource("classpath:spring-config-test.properties")
@EnableWebMvc
public class RootConfig
{
        

}
