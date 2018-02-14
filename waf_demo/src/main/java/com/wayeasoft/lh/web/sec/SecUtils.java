package com.wayeasoft.lh.web.sec;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wayeasoft.core.modeldriven.dao.CommonDao;
import com.wayeasoft.core.modeldriven.dao.CommonExample;
import com.wayeasoft.waf.core.sec.model.Module;
import com.wayeasoft.waf.core.sec.model.ModuleItem;
import com.wayeasoft.waf.core.sec.model.Role;
import com.wayeasoft.waf.core.sec.model.RolePermit;
import com.wayeasoft.waf.core.sec.model.Subsys;
import com.wayeasoft.waf.core.sec.model.User;
import com.wayeasoft.waf.core.sec.model.UserRole;

/**
 *
 */
@Component
public class SecUtils
{
        private final static Logger log = LoggerFactory.getLogger(SecUtils.class);

        @Autowired
        private CommonDao dao;

        /**
         * 返回用户具有的所有角色列表
         * 
         * @param user
         * @return
         */
        public List<Role> getRoleList(User user)
        {
                List<UserRole> userRoles = dao.selectByExample(UserRole.class,
                                new CommonExample().createCriteria().andEqualTo("USER_ID", user.getId()).finish());

                List<Long> roleIds = new ArrayList<Long>(userRoles.size());
                for (UserRole userRole : userRoles)
                {
                        roleIds.add(userRole.getRoleId());
                }

                if (roleIds.isEmpty())
                        return new ArrayList<Role>(0);
                return dao.selectByExample(Role.class, new CommonExample().createCriteria().andIdIn(roleIds).finish());
        }

        public List<Subsys> getAllSubSyss()
        {
                return dao.selectByExample(Subsys.class, new CommonExample().orderBy("ORDERBY"));
        }

        public void fillGrantedSubsys(User user)
        {
                log.debug("开始读取用户的权限信息[loginname={}]", user.getLoginname());
        
                List<ModuleItem> moduleItems;
                if (user.isSuperUser())
                {
                        moduleItems = getAllModuleItems();
                } else
                {
                        moduleItems = getGrantedModuleItems(getRoleList(user));
                }
        
                log.debug("获取具有权限的二级模块[size={}]", moduleItems.size());
        
                Set<Long> moduleIds = new HashSet<Long>(moduleItems.size());
                for (ModuleItem moduleItem : moduleItems)
                {
                        moduleIds.add(moduleItem.getModuleId());
                }
        
                List<Module> grantedModule = new ArrayList<Module>(0);
        
                if (!moduleIds.isEmpty())
                {
                        grantedModule = dao.selectByExample(Module.class, new CommonExample().createCriteria()
                                        .andIdIn(new ArrayList<Long>(moduleIds)).finish().orderBy("ORDERBY"));
                }
        
                log.debug("根据二级模块，生成具有权限的一级模块[size={}]", grantedModule.size());
        
                for (Module module : grantedModule)
                {
                        List<ModuleItem> item = new ArrayList<ModuleItem>();
                        for (ModuleItem moduleItem : moduleItems)
                        {
                                if (moduleItem.getModuleId().equals(module.getId()))
                                {
                                        item.add(moduleItem);
                                }
                        }
        
                        module.setGrantedModuleItems(item);
                        log.debug("为一级模块[name={}, subsysId={}]添加了{}个二级模块", module.getName(), module.getSubsysId(),
                                        item.size());
                }
        
                List<Subsys> subsyss = getAllSubSyss();
        
                for (Subsys subsys : subsyss)
                {
                        List<Module> modules = new ArrayList<Module>();
                        for (Module module : grantedModule)
                        {
                                log.debug("moduleName={}, moduleCode={}, module_subsysId={}, subsysId={}",
                                                module.getName(), module.getCode(), module.getSubsysId(),
                                                subsys.getId());
                                if (module.getSubsysId().equals(subsys.getId()))
                                {
                                        log.debug("相等");
                                        modules.add(module);
                                }
                        }
        
                        subsys.setGrantedModules(modules);
        
                        log.debug("为子系统[name={}]添加了{}个一级模块", subsys.getName(), modules.size());
                }
        
                user.setGrantedSubsysList(subsyss);
        }

        private List<ModuleItem> getGrantedModuleItems(List<Role> roleList)
        {
                if (roleList.isEmpty())
                {
                        return new ArrayList<ModuleItem>(0);
                }

                List<Long> roleIds = new ArrayList<Long>(roleList.size());

                for (Role role : roleList)
                {
                        roleIds.add(role.getId());
                }

                List<RolePermit> rolePermitList = dao.selectByExample(RolePermit.class,
                                new CommonExample().createCriteria().andIn("ROLE_ID", roleIds).finish());

                Set<Long> moduleItemIds = new HashSet<Long>(rolePermitList.size());
                for (RolePermit rolePermit : rolePermitList)
                {
                        moduleItemIds.add(rolePermit.getModuleitemId());
                }

                List<ModuleItem> moduleItems = dao.selectByExample(ModuleItem.class,
                                new CommonExample().createCriteria().andIdIn(new ArrayList<Long>(moduleItemIds))
                                                .finish().or().andEqualTo("ISPUBLIC", "Y").finish().orderBy("ORDERBY"));

                return moduleItems;
        }

        private List<ModuleItem> getAllModuleItems()
        {
                return dao.selectByExample(ModuleItem.class, new CommonExample().orderBy("ORDERBY"));
        }

        /**
         * 修改用户密码
         * 
         * @param oldPassword
         *                旧密码
         * @param newPassword
         *                新密码
         * @throws OldPasswordNotCorrectException
         *                 旧密码与系统中保存的密码不符的时候抛出
         */
//        public void changePassword(String oldPassword, String newPassword) throws OldPasswordNotCorrectException
//        {

//                if (oldPassword == null)
//                {
//                        throw new NbmBaseRuntimeException("旧密码不能为null");
//                }
//
//                if (newPassword == null)
//                {
//                        throw new NbmBaseRuntimeException("新密码不能为null");
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
//                user.setPassword(newPassword);// 内存中的用户保存密码明文，一时想不起来哪用这个字段了——niyuzhe2014-03-20
//        }
}
