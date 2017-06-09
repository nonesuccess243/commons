package com.wayeasoft.waf.core.sec.model;

import com.nbm.core.modeldriven.Model;
import com.wayeasoft.waf.core.sec.model.enums.UserRoleState;

public class UserRole extends Model
{
        private Long userId;

        private Long roleId;
        
        private UserRoleState state;

        public Long getUserId()
        {
                return userId;
        }

        public void setUserId(Long userId)
        {
                this.userId = userId;
        }

        public Long getRoleId()
        {
                return roleId;
        }

        public void setRoleId(Long roleId)
        {
                this.roleId = roleId;
        }

        public UserRoleState getState()
        {
                return state;
        }

        public void setState(UserRoleState state)
        {
                this.state = state;
        }

      
}