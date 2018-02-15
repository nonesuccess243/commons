package com.wayeasoft.core.spring.test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;


/**
 * spring配置类 
 * @author niyuzhe
 *
 */
@Configuration
//@ImportResource("classpath:spring-config.xml")
@ComponentScan(basePackages= {"com.wayeasoft", "com.nbm"})
//@PropertySource("classpath:spring-config-test.properties")
public class RootConfig
{
        @Bean public static PropertySourcesPlaceholderConfigurer placeholderConfigurer()
        {
                return new PropertySourcesPlaceholderConfigurer();
        }
        

}
