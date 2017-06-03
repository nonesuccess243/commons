package com.wayeasoft.test.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.ComponentScan.Filter;

@Configuration
@ImportResource("classpath:spring-config.xml")
@ComponentScan(basePackages="com.wayeasoft")
public class RootConfig
{
        

}
