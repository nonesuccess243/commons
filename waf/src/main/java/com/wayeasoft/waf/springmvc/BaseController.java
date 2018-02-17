package com.wayeasoft.waf.springmvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.wayeasoft.core.modeldriven.dao.CommonDao;


@Controller
public class BaseController
{
        @Autowired
        protected CommonDao dao;
}
