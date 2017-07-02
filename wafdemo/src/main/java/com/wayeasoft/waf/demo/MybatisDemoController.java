package com.wayeasoft.waf.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.wayeasoft.waf.springmvc.controller.BaseController;

@RestController
@RequestMapping("/api/data")
public class MybatisDemoController extends BaseController
{
        private final static Logger log = LoggerFactory.getLogger(MybatisDemoController.class);
        
        @RequestMapping("/{packageName}/{modelName}/{id}")
        @ResponseBody
        public DemoModel queryById()
        { 
                return dao.selectById(DemoModel.class, 1l);
        }
        
}
