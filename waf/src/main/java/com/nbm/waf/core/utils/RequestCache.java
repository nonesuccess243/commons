package com.nbm.waf.core.utils;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;

public abstract class RequestCache<KEY, VALUE>
{

        public VALUE getValueFromCache(KEY key)
        {
                
                if( ActionContext.getContext() == null )//必须用此函数判断。如果直接用ServletActionContext.getRequest，在非servlet环境下会直接抛出异常
                {
                        return getValue(key);
                }
                
                HttpServletRequest request = ServletActionContext.getRequest();
                if (request == null)
                {
                        return getValue(key);
                }

                Map<KEY, VALUE> map = (Map<KEY, VALUE>) request.getAttribute(getRequestKey());
                if (map == null)
                {
                        map = new HashMap<KEY, VALUE>();
                        request.setAttribute(getRequestKey(), map);
                }

                VALUE v = map.get(key);
                if (v == null)
                {
                        v = getValue(key);
                        map.put(key, v);
                }

                return v;
        }

        private String getRequestKey()
        {
                return "REQUEST_CACHE_" + this.getClass().hashCode();
        }

        public void invalid(KEY key)
        {
                if( ActionContext.getContext() == null )
                {
                        return;
                }
                HttpServletRequest request = ServletActionContext.getRequest();
                if( request == null )
                        return;
                
                Map<KEY, VALUE> map = (Map<KEY, VALUE>) request.getAttribute(getRequestKey());
                if(map == null )
                        return;
                map.remove(key);
        }
        
        public VALUE getValueWithoutCache(KEY key)
        {
                return getValue(key);
        }

        protected abstract VALUE getValue(KEY key);

}
