package com.wayeasoft.waf.core.sec.model.enums;

import com.nbm.commons.enums.CodeEnum;
import com.nbm.commons.enums.CodeEnumItem;
import com.nbm.commons.enums.Displayable;

@CodeEnum(name="用户状态")
public enum UserState implements Displayable
{
        
        /**
         * 只有此状态的用户才能登录成功
         */
        @CodeEnumItem(displayName = "活动中", showOrder = "1")
        ACTIVITY,
        
        @CodeEnumItem(displayName = "禁止使用",showOrder = "2")
        FORBIDED,
        
        @CodeEnumItem(displayName = "审核中",showOrder = "3")
        APPROVED,
        
        @CodeEnumItem(displayName = "审核拒绝",showOrder = "4")
        REFUSE;
        
        
        
        
}
