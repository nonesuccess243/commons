package com.nbm.commons.enums;


public class DisplayableUtils
{
        public static CodeEnumItem getInfo(Displayable displayable)
        {
                CodeEnumItem ce = null;
                try
                {
                        ce = displayable.getClass().getField(((Enum<?>)displayable).name()).getAnnotation(CodeEnumItem.class);
                } catch (NoSuchFieldException | SecurityException e)
                {
                        throw new RuntimeException(displayable + "获取注解发生未知异常");
                }
                if (ce == null)
                {
                        throw new RuntimeException(displayable + "字段没加CodeEnumItem");
                }

                return ce;
        }
}
