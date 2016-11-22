package com.nbm.bhjs.core.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nbm.bhjs.core.org.Org;
import com.nbm.bhjs.core.org.OrgPool;
import com.nbm.commons.db.meta.DbNamingConverter;
import com.nbm.exception.NbmBaseRuntimeException;
import com.nbm.waf.core.sec.RuntimeUserUtils;
import com.opensymphony.xwork2.ActionContext;
import com.younker.waf.db.mybatis.SqlSessionProvider;

public abstract class GridDataProvider
{
        private final static Logger log = LoggerFactory.getLogger(GridDataProvider.class);

        public static GridDataProvider getProvider(final String providerName)
        {
                Class<? extends GridDataProvider> clazz;
                try
                {
                        clazz = (Class<? extends GridDataProvider>) Class.forName(providerName);
                        return clazz.newInstance();
                } catch (ClassNotFoundException e)
                {
                        log.debug("找不到provider[name={}]，使用默认实现代替", providerName);
                        return new GridDataProvider()
                        {

                                @Override
                                protected Map<String, Object> getRuntimeParams()
                                {
                                        return null;
                                }

                                @Override
                                protected String getSqlName()
                                {
                                        return providerName;
                                }
                        };
                } catch (InstantiationException | IllegalAccessException e)
                {
                        throw new NbmBaseRuntimeException("生成报表provider发生错误", e);
                }

        }

        /**
         * 前台传过来的参数
         */
        private final static String PARAM_KEY_PARAMS = "params";

        /**
         * application attribute
         */
        private final static String PARAM_KEY_APPLICATION = "application";

        /**
         * session attribute
         */
        private final static String PARAM_KEY_SESSION = "session";

        /**
         * request attribute
         */
        private final static String PARAM_KEY_REQUEST = "request";

        /**
         * 手动指定的参数
         */
        private final static String PARAM_KEY_CUSTOM = "custom";
        
        private Map<String, Object> customParams;

        /**
         * 此函数可以被重写
         * 
         * @return 前台grid中显示的数据，为二维表，map的key对应前台列的field
         */
        public List<Map<String, ?>> queryData()
        {
                Map<String, Object> arguments = new HashMap<>();
                arguments.put(PARAM_KEY_CUSTOM, getRuntimeParams());
                arguments.put(PARAM_KEY_PARAMS, getParamsMap(arguments));
                arguments.put(PARAM_KEY_SESSION, ActionContext.getContext().getSession());
                arguments.put(PARAM_KEY_APPLICATION, ActionContext.getContext().getApplication());

                List<Map<String, ?>> data = SqlSessionProvider.getSqlSession().selectList(
                                getSqlName(), arguments);
                List<Map<String, ?>> newdata = new ArrayList<>(data.size());
                for (Map<String, ?> map : data)
                {
                        Map<String, Object> newmap = new HashMap<>(map.size());
                        for (String key : map.keySet())
                        {
                                newmap.put(DbNamingConverter.DEFAULT_ONE
                                                .columnName2JavaPropertyName(key), map.get(key));
                        }

                        newdata.add(newmap);
                }

                return newdata;
        }

        public List<Map<String, ?>> queryFootData()
        {
                Map<String, Object> arguments = new HashMap<>();
                arguments.put(PARAM_KEY_CUSTOM, getRuntimeParams());
                arguments.put(PARAM_KEY_PARAMS, getParamsMap(arguments));
                arguments.put(PARAM_KEY_SESSION, ActionContext.getContext().getSession());
                arguments.put(PARAM_KEY_APPLICATION, ActionContext.getContext().getApplication());
                
                List<Map<String, ?>> data = SqlSessionProvider.getSqlSession()
                                .selectList(getSqlName() + "Footer", arguments);

                List<Map<String, ?>> newdata = new ArrayList<>(data.size());
                
                for (Map<String, ?> map : data)
                {
                        Map<String, Object> newmap = new HashMap<>(map.size());
                        for (String key : map.keySet())
                        {
                                newmap.put(DbNamingConverter.DEFAULT_ONE
                                                .columnName2JavaPropertyName(key), map.get(key));
                        }

                        newdata.add(newmap);
                }
 
                return newdata;
        }

        protected abstract Map<String, Object> getRuntimeParams();

        protected String getSqlName()
        {
                return this.getClass().getName();
        }

        private Map<String, Object> getParamsMap( Map<String, Object> arguments)
        {
                Map<String, Object> paramsMap = new HashMap<>();
                for( Entry<String, Object> entry : ActionContext.getContext().getParameters().entrySet())
                {
                        if( entry.getValue() instanceof Object[] )
                        {
                                Object[] value = ((Object[])entry.getValue());
                                if( value.length == 1 )
                                {
                                        paramsMap.put(entry.getKey(), value[0]);
                                }else
                                {
                                        paramsMap.put(entry.getKey(), value);
                                }
                        }else
                        {
                                paramsMap.put(entry.getKey(), entry.getValue());
                        }
                        
                        if( entry.getKey().equals("orgId"))
                        {
                        	
                        	//这部分代码依赖了org。
                        	//这样依赖的目的是，当前端传入orgId时，不必再取一遍orgid相关联的org对象，而是由框架自动获取。这样就可以在mybatis配置文件中直接用了。
                        	//如果想解决这部分依赖错误，要么就引入org依赖，要么就等开发完成commons到，这样每个类只要传入了id就可以获取该对象了。
                        	
                        	
                                Org org = OrgPool.INSTANCE.getOrgById(Long.parseLong(paramsMap.get("orgId").toString()));
                                
                                if(!RuntimeUserUtils.INSTANCE.getCurrentEmployee().getOrg().getProxy().isAncestorOfOrSelf(org))
                                {
                                        throw new NbmBaseRuntimeException("传入");
                                }
                                
                                arguments.put("org", org);
                        }
                }
                return paramsMap;
        }
}
