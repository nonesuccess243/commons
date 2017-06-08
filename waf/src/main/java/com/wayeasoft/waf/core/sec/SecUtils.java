//package com.wayeasoft.waf.core.sec;
///***********************************************
// * Title:       MenuUtils.java
// * Description: MenuUtils.java
// * Create Date: 2013-8-7
// * CopyRight:   CopyRight(c)@2013
// * Company:     NBM
// ***********************************************
// */
//package com.nbm.waf.core.sec;
//
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.nbm.commons.util.PasswordHash;
//import com.wayeasoft.core.modeldriven.dao.CommonExample;
//import com.wayeasoft.waf.core.sec.model.Role;
//import com.wayeasoft.waf.core.sec.model.User;
//import com.wayeasoft.waf.core.sec.model.UserRole;
//
///**
// *
// */
//public enum SecUtils
//{
//        INSTANCE;
//
//        private final static Logger log = LoggerFactory.getLogger(SecUtils.class);
//
//        /**
//         * 返回用户具有的所有角色列表
//         * 
//         * @param user
//         * @return
//         */
//        public List<Role> getRoleList(User user)
//        {
//                CommonExample example = new CommonExample();
//                example.createCriteria().andEqualTo("USER_ID", user.getId());
//                List<UserRole> userRoles = SqlSessionProvider.getSqlSession().getMapper(UserRoleMapper.class)
//                                .selectByExample(example);
//
//                List<Long> roleIds = new ArrayList<Long>(userRoles.size());
//                for (UserRole userRole : userRoles)
//                {
//                        roleIds.add(userRole.getRoleId());
//                }
//
//                if (roleIds.isEmpty())
//                        return new ArrayList<Role>(0);
//                RoleExample roleExample = new RoleExample();
//                roleExample.createCriteria().andIdIn(roleIds);
//                return SqlSessionProvider.getSqlSession().getMapper(RoleMapper.class).selectByExample(roleExample);
//        }
//
//        private List<ModuleItem> getGrantedModuleItems(List<Role> roleList)
//        {
//                if (roleList.isEmpty())
//                {
//                        return new ArrayList<ModuleItem>(0);
//                }
//
//                List<Long> roleIds = new ArrayList<Long>(roleList.size());
//                
//                for( Role role: roleList)
//                {
//                        roleIds.add(role.getId());
//                }
//                RolePermitExample example = new RolePermitExample();
//                example.createCriteria().andRoleIdIn(roleIds);
//                List<RolePermit> rolePermitList = SqlSessionProvider.getSqlSession().getMapper(RolePermitMapper.class)
//                                .selectByExample(example);
//
//                Set<Long> moduleItemIds = new HashSet<Long>(rolePermitList.size());
//                for (RolePermit rolePermit : rolePermitList)
//                {
//                        moduleItemIds.add(rolePermit.getModuleitemId());
//                }
//
//                ModuleItemExample moduleExample = new ModuleItemExample();
//                moduleExample.createCriteria().andIdIn(new ArrayList<Long>(moduleItemIds));
//                moduleExample.or( moduleExample.createCriteria().andIspublicEqualTo("Y") );
//                moduleExample.setOrderByClause("ORDERBY");
//                List<ModuleItem> moduleItems = SqlSessionProvider.getSqlSession().getMapper(ModuleItemMapper.class)
//                                .selectByExample(moduleExample);
//                
//                
//                //获取公共模块
//                moduleExample.clear();
//                
//                
//                return moduleItems;
//        }
//
//        private List<ModuleItem> getAllModuleItems()
//        {
//                ModuleItemExample moduleExample = new ModuleItemExample();
//                moduleExample.setOrderByClause("ORDERBY");
//                return SqlSessionProvider.getSqlSession().getMapper(ModuleItemMapper.class).selectByExample(moduleExample);
//        }
//
//        public List<Subsys> getAllSubSyss()
//        {
//                SubsysExample example = new SubsysExample();
//                example.setOrderByClause("ORDERBY");
//                return SqlSessionProvider.getSqlSession().getMapper(SubsysMapper.class).selectByExample(example);
//        }
//
//        public void fillGrantedSubsys(User user)
//        {
//                log.debug("开始读取用户的权限信息[loginname={}]", user.getLoginname());
//
//                List<ModuleItem> moduleItems;
//                if (user.isSuperUser())
//                {
//                        moduleItems = getAllModuleItems();
//                } else
//                {
//                        moduleItems = getGrantedModuleItems(getRoleList(user));
//                }
//
//                log.debug("获取具有权限的二级模块[size={}]", moduleItems.size());
//
//                Set<Long> moduleIds = new HashSet<Long>(moduleItems.size());
//                for (ModuleItem moduleItem : moduleItems)
//                {
//                        moduleIds.add(moduleItem.getModuleId());
//                }
//
//                List<Module> grantedModule = new ArrayList<Module>(0);
//
//                if (!moduleIds.isEmpty())
//                {
//                        ModuleExample example = new ModuleExample();
//                        example.createCriteria().andIdIn(new ArrayList<Long>(moduleIds));
//                        example.setOrderByClause("ORDERBY");
//                        grantedModule = SqlSessionProvider.getSqlSession().getMapper(ModuleMapper.class)
//                                        .selectByExample(example);
//                }
//
//                log.debug("根据二级模块，生成具有权限的一级模块[size={}]", grantedModule.size());
//
//                for (Module module : grantedModule)
//                {
//                        List<ModuleItem> item = new ArrayList<ModuleItem>();
//                        for (ModuleItem moduleItem : moduleItems)
//                        {
//                                if (moduleItem.getModuleId().equals(module.getId()))
//                                {
//                                        item.add(moduleItem);
//                                }
//                        }
//
//                        module.setGrantedModuleItems(item);
//                        log.debug("为一级模块[name={}, subsysId={}]添加了{}个二级模块", module.getName(), module.getSubsysId(), item.size());
//                }
//
//                List<Subsys> subsyss = getAllSubSyss();
//
//                for (Subsys subsys : subsyss)
//                {
//                        List<Module> modules = new ArrayList<Module>();
//                        for (Module module : grantedModule)
//                        {
//                                log.debug("moduleName={}, moduleCode={}, module_subsysId={}, subsysId={}",module.getName(), module.getCode(), module.getSubsysId(), subsys.getId());
//                                if (module.getSubsysId().equals(subsys.getId()))
//                                {
//                                        log.debug("相等");
//                                        modules.add(module);
//                                }
//                        }
//                        
//                        subsys.setGrantedModules(modules);
//                        
//                        log.debug("为子系统[name={}]添加了{}个一级模块", subsys.getName(), modules.size());
//                }
//
//                user.setGrantedSubsysList(subsyss);
//        }
//        
//        /**
//         * 修改用户密码
//         * @param oldPassword 旧密码
//         * @param newPassword 新密码
//         * @throws OldPasswordNotCorrectException 旧密码与系统中保存的密码不符的时候抛出
//         */
//        public void changePassword(String oldPassword, String newPassword) throws OldPasswordNotCorrectException
//        {
//                
//                if( oldPassword == null )
//                {
//                        throw new BhjsBasicRuntimeException("旧密码不能为null");
//                }
//                
//                if( newPassword == null )
//                {
//                        throw new BhjsBasicRuntimeException("新密码不能为null");
//                }
//                
//                User user = RuntimeUserUtils.INSTANCE.getCurrentUser();
//                log.debug("user password: {}", user.getPassword());
//                
//                if (!PasswordHash.validatePassword(oldPassword, user.getPassword()))
//                {
//                        throw new OldPasswordNotCorrectException(oldPassword, user);
//                }
//                
//                User temp = RuntimeUserUtils.INSTANCE.queryUser(user.getLoginname(), oldPassword);
//                temp.setPassword(PasswordHash.createHash(newPassword));
//                
//                SqlSessionProvider.getSqlSession().getMapper(UserMapper.class).updateByPrimaryKey(temp);
//                
//                user.setPassword(newPassword);//内存中的用户保存密码明文，一时想不起来哪用这个字段了——niyuzhe2014-03-20
//        }
//}
