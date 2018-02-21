package com.wayeasoft.springmvc.config;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.wayeasoft.core.configuration.Cfg;

@Configuration
@EnableWebMvc
@ComponentScan({"com.wayeasoft","com.nbm"})
public class WebConfig extends WebMvcConfigurerAdapter
{
        @Bean
        public ViewResolver viewResolver()
        {
                InternalResourceViewResolver resolver = new InternalResourceViewResolver();
                resolver.setPrefix(Cfg.I.get("spring.mvc.view_prefix", String.class,"/WEB-INF/views/"));
                resolver.setSuffix(Cfg.I.get("spring.mvc.view_suffix", String.class,".jsp"));
                resolver.setExposeContextBeansAsAttributes(true);
                return resolver;
        }
        
        @InitBinder
        public void initBinder(WebDataBinder binder)
        {
                // 设置日期类型的字段，前端按照yyyy-MM-dd显示 or 解析？忘了
                binder.registerCustomEditor(Date.class,
                                new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true, 10));
        }
        
        
        
        @Override
        public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer)
        {
                configurer.enable();
        }

}
