package com.wayeasoft.webdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@EnableAutoConfiguration
@ComponentScan({"com.wayeasoft", "com.nbm"})
public class Application
{
        public static void main(String[] args) throws Exception
        {
                SpringApplication.run(Application.class, args);
        }

}
