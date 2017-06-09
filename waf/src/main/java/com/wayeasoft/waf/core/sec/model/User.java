package com.wayeasoft.waf.core.sec.model;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nbm.core.modeldriven.Db;
import com.nbm.core.modeldriven.Model;
import com.nbm.core.modeldriven.anno.DbIgnore;
import com.nbm.core.modeldriven.anno.Length;
import com.nbm.core.modeldriven.anno.ModelInfo;
import com.nbm.exception.NbmBaseRuntimeException;
import com.wayeasoft.waf.core.sec.model.enums.UserState;

@ModelInfo(tableName = "S_USER")
public class User extends Model
{

        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        private final static Logger log = LoggerFactory.getLogger(User.class);

        /**
         * 登录名
         */
        @Length(max = 50)
        private String loginname;

        /**
         * 员工id
         */
        private Long employeeId;

        /**
         * 密码
         */
        @Length(max = 128)
        private String password;

        /**
         * 最后一次登录时间
         */
        private Date lastdate;

        /**
         * 状态
         * 
         * 此字段原本用于表示用户封禁状态，在添加了用户注册功能之后，与用户是否能登录有关的所有状态都放在此字段中。
         */
        @Length(max = 100)
        private UserState forbided;

        /**
         * 注册时间
         */
        private Date registeredDate;

        /**
         * 审核人，值填写employee表的id
         */
        private long approvedId;

        /**
         * 审核单位
         */
        private long approvedUnitId;

        /**
         * 审核时间
         */
        private Date approvedDate;

        /*
         * 登录时间
         */
        @DbIgnore
        private Date runtimeLoginTime;

        /**
         * 备注
         */
        @Length(max = 100)
        private String remark;

        @DbIgnore
        private boolean superUser = false;

        public Date getRegisteredDate()
        {
                return registeredDate;
        }

        public long getApprovedId()
        {
                return approvedId;
        }

        public long getApprovedUnitId()
        {
                return approvedUnitId;
        }

        public Date getApprovedDate()
        {
                return approvedDate;
        }

        public void setRegisteredDate(Date registeredDate)
        {
                this.registeredDate = registeredDate;
        }

        public void setApprovedId(long approvedId)
        {
                this.approvedId = approvedId;
        }

        public void setApprovedUnitId(long approvedUnitId)
        {
                this.approvedUnitId = approvedUnitId;
        }

        public void setApprovedDate(Date approvedDate)
        {
                this.approvedDate = approvedDate;
        }

        public String getLoginname()
        {
                return loginname;
        }

        public void setLoginname(String loginname)
        {
                this.loginname = loginname == null ? null : loginname.trim();
        }

        public Long getEmployeeId()
        {
                return employeeId;
        }

        public void setEmployeeId(Long employeeId)
        {
                this.employeeId = employeeId;
        }

        public String getPassword()
        {
                return password;
        }

        public void setPassword(String password)
        {
                this.password = password == null ? null : password.trim();
        }

        public Date getLastdate()
        {
                return lastdate;
        }

        public void setLastdate(Date lastdate)
        {
                this.lastdate = lastdate;
        }

        public UserState getForbided()
        {
                return forbided;
        }

        public void setForbided(UserState forbided)
        {
                this.forbided = forbided;
        }

        public String getRemark()
        {
                return remark;
        }

        public void setRemark(String remark)
        {
                this.remark = remark == null ? null : remark.trim();
        }

        public Date getRuntimeLoginTime()
        {
                return runtimeLoginTime;
        }

        public void setRuntimeLoginTime(Date runtimeLoginTime)
        {
                this.runtimeLoginTime = runtimeLoginTime;
        }

        @DbIgnore
        private List<Role> roleList;

        public List<Role> getRoleList()
        {
                if (roleList == null)
                {
//                        roleList = SecUtils.INSTANCE.getRoleList(this);
                }

                return roleList;
        }

        @DbIgnore
        private List<Subsys> grantedSubsysList;

        /**
         * @return the grantedSubsysList
         */
        public List<Subsys> getGrantedSubsysList()
        {
                return grantedSubsysList;
        }

        public Subsys getGrantedSubsysByCode(String subsysCode)
        {
                for (Subsys subsys : grantedSubsysList)
                {
                        if (subsys.getCode().equalsIgnoreCase(subsysCode))
                                return subsys;
                }

                throw new NbmBaseRuntimeException("找不到子系统").set("subsysCode",
                                subsysCode);
        }

        /**
         * @param grantedSubsysList
         *                the grantedSubsysList to set
         */
        public void setGrantedSubsysList(List<Subsys> grantedSubsysList)
        {
                this.grantedSubsysList = grantedSubsysList;
        }

        /**
         * @return the superUser
         */
        @DbIgnore
        public boolean isSuperUser()
        {
                return superUser;
        }

        /**
         * @param superUser
         *                the superUser to set
         */
        public void setSuperUser(boolean superUser)
        {
                this.superUser = superUser;
        }

        public boolean hasPermit(String subsysCode, String moduleCode,
                        String moduleItemCode)
        {
                if (isSuperUser())
                {
                        return true;
                }

                Subsys subsys = null;
                try
                {
                        subsys = getGrantedSubsysByCode(subsysCode);
                } catch (NbmBaseRuntimeException  e)
                {
                        log.error("子系统不存在[loginname={}, subsysCode={}, moduleCode={}, moduleItemCode={}]",
                                        loginname, subsysCode, moduleCode,
                                        moduleItemCode);
                        return false;
                }

                Module module = subsys.getModuleByCode(moduleCode);
                if (module == null)
                {
                        log.error("一级模块不存在[loginname={}, subsysCode={}, moduleCode={}, moduleItemCode={}]",
                                        loginname, subsysCode, moduleCode,
                                        moduleItemCode);
                        return false;
                }

                ModuleItem moduleItem = module
                                .getModuleItemByCode(moduleItemCode);
                if (moduleItem == null)
                {
                        log.error("二级模块不存在[loginname={}, subsysCode={}, moduleCode={}, moduleItemCode={}]",
                                        loginname, subsysCode, moduleCode,
                                        moduleItemCode);
                        return false;
                }

                return true;
        }

        @Override
        public String toString()
        {
                return "User [loginname=" + loginname + ", employeeId="
                                + employeeId + ", password=" + password
                                + ", lastdate=" + lastdate + ", forbided="
                                + forbided + ", registeredDate="
                                + registeredDate + ", approvedId=" + approvedId
                                + ", approvedUnitId=" + approvedUnitId
                                + ", approvedDate=" + approvedDate
                                + ", runtimeLoginTime=" + runtimeLoginTime
                                + ", remark=" + remark + ", superUser="
                                + superUser + ", roleList=" + roleList
                                + ", grantedSubsysList=" + grantedSubsysList
                                + "]";
        }

        public static void main(String[] args)
        {
                Db.ORACLE.generate(User.class);
        }

}