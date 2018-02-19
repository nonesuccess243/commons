//package com.wayeasoft.webdemo.sec;
//
//
//import java.util.List;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.wayeasoft.waf.core.sec.model.Subsys;
//import com.wayeasoft.waf.core.sec.model.User;
//import com.wayeasoft.waf.springmvc.BaseController;
//
//@RestController
//public class ModuleController extends BaseController
//{
//        @Autowired
//        private SecUtils secUtils;
//        
//        
//        /**
//         * 返回某子系统的信息及当前登录用户具有权限的所有模块信息
//         * 
//         * TODO 当前还是只返回该子系统下所有模块的信息
//         * @param subsysCode
//         * @return
//         */
//        @RequestMapping("/public/{subsysCode}/modules")
//        public Subsys modules(@PathVariable("subsysCode") String subsysCode )
//        {
//                
//                User user = new User();
//                user.setSuperUser(true);
//                
//                secUtils.fillGrantedSubsys(user);
//                
//                return user.getGrantedSubsysByCode(subsysCode);
//        }
//        
//        @RequestMapping("/public/subsyss")
//        public List<Subsys> subsyss()
//        {
//                return secUtils.getAllSubSyss();
//        }
//
//}
