//package com.wayeasoft.webdemo.sec;
//
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//
//import com.wayeasoft.core.modeldriven.dao.CommonDao;
//import com.wayeasoft.core.modeldriven.dao.CommonExample;
//import com.wayeasoft.waf.core.sec.model.User;
//
//public class UserService implements UserDetailsService
//{
//        @Autowired
//        private CommonDao dao;
//
//        @Override
//        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
//        {
//                List<User> users = dao.selectByExample(User.class, 
//                                new CommonExample()
//                                        .createCriteria().andEqualTo("LOGINNAME", username).finish());
//                
//                return new org.springframework.security.core.userdetails.User(username, users.get(0).getPassword(), null);
//                
//        }
//
//}
