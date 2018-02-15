package com.wayeasoft.waf.demo;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nbm.core.modeldriven.Db;
import com.nbm.core.modeldriven.generator.CrudGenerator;

@RestController
@RequestMapping("/api/data")
public class CommonDataController
{
        private final static Logger log = LoggerFactory.getLogger(CommonDataController.class);
        
        @Autowired
        private DataSource dataSource;
        
        
        @RequestMapping("/{model}/{id}")
        @ResponseBody
        public String queryById(@PathVariable String model, @PathVariable Long id)
        { 
                return "success";
        }

}
