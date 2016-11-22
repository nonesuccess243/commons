/***********************************************
 * Title:       DiplayableItem.java
 * Description: DiplayableItem.java
 * Create Date: 2013-6-14
 * CopyRight:   CopyRight(c)@2013
 * Company:     NBM
 ***********************************************
 */
package com.nbm.commons.enums;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CodeEnumItem
{
        public String displayName();

        public String remark() default "";
        
        public String showOrder() default "";

}
