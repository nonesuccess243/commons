package com.wayeasoft.waf.springmvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.younker.waf.db.mybatis.CommonDao;


@Controller
public class BaseController
{
        @Autowired
        protected CommonDao dao;
}
