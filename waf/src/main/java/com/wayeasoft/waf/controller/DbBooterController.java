package com.wayeasoft.waf.controller;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nbm.waf.boot.DbBooter;
import com.wayeasoft.waf.springmvc.BaseController;

@RestController
public class DbBooterController extends BaseController
{
        private static String token_generate = null;

        private final static Logger log = LoggerFactory.getLogger(DbBooterController.class);

        @RequestMapping("/starter/dbboot/boot.do")
        public String boot(String bootToken)
        {
                if (token_generate == null)
                {
                        token_generate = RandomStringUtils.randomAlphanumeric(20);
                        log.info("token is: [{}]", token_generate);
                        return "find in log and give me the token by token=?";
                } else
                {
                        if (token_generate.equals(bootToken))
                        {
                                DbBooter.I.boot();
                                return "success";
                        } else
                        {
                                return "tokne not correct";
                        }
                }

        }

}
