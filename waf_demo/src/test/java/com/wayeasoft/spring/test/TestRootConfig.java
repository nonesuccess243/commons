package com.wayeasoft.spring.test;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan(basePackages="com.wayeasoft", 
        excludeFilters={@Filter(type=FilterType.ANNOTATION, value=EnableWebMvc.class)})
@PropertySource("classpath:spring-config-test.properties")
public class TestRootConfig
{

}
