package com.nbm.commons.code;

import java.util.List;

import javax.sql.DataSource;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.impl.NutDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.younker.waf.db.DataSourceProvider;
import com.younker.waf.utils.StringUtil;

/**
 * 关于Code操作的工具类，采用单态模式实现，使用时需要调用CodeUtils.getInstance()。</br>
 * 对应数据库表为S_CODE和S_CODETYPE
 */
public class CodeUtils
{

        private final static Logger log = LoggerFactory.getLogger(CodeUtils.class);

        private Dao dao;

        private CodeUtils(DataSource ds)
        {
                dao = new NutDao(ds);
                log.info("CodeUtils初始化完成");
        }

        private static CodeUtils _instance = null;

        public final static void initDefault()
        {
                _instance = new CodeUtils(DataSourceProvider.instance().getDataSource());
                log.info("codeUtils默认实例初始化完成");
        }

        /**
         * 返回默认的CodeUtils实例，使用DateSourceProvier类提供的DataSource实例作为数据源
         */
        public final static CodeUtils getInstance()
        {
                if (_instance == null)
                {
                        initDefault();
                }
                return _instance;
        }

        /**
         * 传入DataSource，构造CodeUtils实例
         */
        public final static CodeUtils getInstance(DataSource ds)
        {
                return new CodeUtils(ds);
        }

        /**
         * 根据typeCode查询code，返回code实例的列表
         */
        public List<Code> getCodesByTypeCode(String typeCode)
        {
                List<Code> result = dao.query(Code.class, Cnd.wrap(StringUtil.getString(
                                "TYPECODE='{}' ORDER BY SHOWORDER, CODE", typeCode)));

                return result;
        }

        /**
         * 根据typeCode和code查询唯一的Code实例
         */
        public Code getCodeByCode(String typeCode, String code)
        {
                if (code == null)
                        throw new IllegalArgumentException("code参数不能为空");

                Code result = dao.fetch(Code.class,
                                Cnd.where("typecode", "=", typeCode).and("CODE", "=", code));

                return result;
        }

        /**
         * 根据typeCode和name查询唯一的Code实例
         */
        public Code getCodeByName(String typeCode, String name)
        {
                if (name == null)
                {
                        throw new IllegalArgumentException("name参数不能为空");
                }

                Code result = dao.fetch(Code.class,
                                Cnd.where("typecode", "=", typeCode).and("NAME", "=", name));

                return result;

        }

        /**
         * 根据typeCode和code获取code的名字
         */
        public String getCodeName(String typeCode, String code)
        {
                return getCodeByCode(typeCode, code).getName();
        }

        /**
         * 根据typeCode和name，返回查询到的Code的代码
         */
        public String getCodeCode(String typeCode, String name)
        {
                return getCodeByName(typeCode, name).getCode();
        }

}
