/***********************************************
 * Title:       ListUtils.java
 * Description: ListUtils.java
 * Create Date: 2013-7-18
 * CopyRight:   CopyRight(c)@2013
 * Company:     NBM
 ***********************************************
 */
package com.nbm.commons.util;

import java.util.List;

import com.nbm.exception.NbmBaseRuntimeException;

/**
 *
 */
public enum ListUtils
{
        INSTANCE;
        
        public <T> T getInstanceFromSingleList(List<T> instances)
        {
                if( instances.isEmpty() )
                {
                        throw new NbmBaseRuntimeException("列表为空");
                }
                if( instances.size() > 1)
                {
                        throw new NbmBaseRuntimeException("列表的元素大于1个").set("list", instances);
                }
                return instances.get(0);
        }

}
