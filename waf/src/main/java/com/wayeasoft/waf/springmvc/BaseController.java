package com.wayeasoft.waf.springmvc;

import org.springframework.stereotype.Controller;

import com.younker.waf.db.mybatis.CommonDao;


@Controller
public class BaseController
{
        protected CommonDao dao = CommonDao.get();
}
