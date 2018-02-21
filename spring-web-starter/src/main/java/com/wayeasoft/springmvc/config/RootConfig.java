package com.wayeasoft.springmvc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan(basePackages= {"com.wayeasoft","com.nbm"}, 
        excludeFilters={@Filter(type=FilterType.ANNOTATION, value=EnableWebMvc.class)})
//@PropertySource("classpath:spring-config.properties")
public class RootConfig
{
        @Bean public static PropertySourcesPlaceholderConfigurer placeholderConfigurer()
        {
                return new PropertySourcesPlaceholderConfigurer();
        }

}
