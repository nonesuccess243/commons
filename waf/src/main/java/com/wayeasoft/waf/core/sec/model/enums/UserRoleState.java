package com.wayeasoft.waf.core.sec.model.enums;

import com.nbm.commons.enums.CodeEnum;
import com.nbm.commons.enums.CodeEnumItem;
import com.nbm.commons.enums.Displayable;

@CodeEnum(name="用户权限状态")
public enum UserRoleState implements Displayable
{
        @CodeEnumItem(displayName="可使用",showOrder = "1")
        USED,
        
        
        @CodeEnumItem(displayName = "审核中",showOrder = "2")
        APPROVED,
        
        @CodeEnumItem(displayName = "审核拒绝",showOrder = "3")
        REFUSE;
        

}
