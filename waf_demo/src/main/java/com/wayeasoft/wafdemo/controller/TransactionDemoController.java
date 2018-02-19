package com.wayeasoft.wafdemo.controller;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wayeasoft.waf.core.sec.model.Subsys;
import com.wayeasoft.waf.springmvc.BaseController;

@RestController
public class TransactionDemoController extends BaseController
{
        @Transactional
        @RequestMapping("/testTransactionError")
        public Subsys testTransactionError()
        {
                Subsys subsys = new Subsys();
                
                subsys.setName("adaf");
                
                dao.insert(subsys);
                
                throw new RuntimeException("这里就是要出个错给你看看");//TODO 还无法实现事务管理，没有配置transactionManager
        }
        
        @Transactional
        @RequestMapping("/testTransaction")
        public Subsys testTransaction()
        {
                Subsys subsys = new Subsys();
                
                subsys.setName("adaf");
                
                dao.insert(subsys);
                return subsys;
        }

}
