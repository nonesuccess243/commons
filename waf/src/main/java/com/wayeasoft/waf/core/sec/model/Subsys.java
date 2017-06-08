package com.wayeasoft.waf.core.sec.model;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nbm.core.modeldriven.Db;
import com.nbm.core.modeldriven.Model;
import com.nbm.core.modeldriven.anno.DbIgnore;
import com.nbm.core.modeldriven.anno.ModelInfo;
import com.nbm.core.modeldriven.generator.CrudGenerator;


@ModelInfo(tableName="S_SUBSYS")
public class Subsys extends Model
{
        
        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        private final static Logger log = LoggerFactory.getLogger(Subsys.class);

        private String code;

        private String name;

        private String abbr;
        
        private String welcomePage;

        private Integer orderby;


        public String getCode()
        {
                return code;
        }

        public void setCode(String code)
        {
                this.code = code == null ? null : code.trim();
        }

        public String getName()
        {
                return name;
        }

        public void setName(String name)
        {
                this.name = name == null ? null : name.trim();
        }

        public String getAbbr()
        {
                return abbr;
        }

        public void setAbbr(String abbr)
        {
                this.abbr = abbr == null ? null : abbr.trim();
        }

        public Integer getOrderby()
        {
                return orderby;
        }

        public void setOrderby(Integer orderby)
        {
                this.orderby = orderby;
        }
        
        @DbIgnore
        private List<Module> grantedModules;

        

        /**
         * @return the grantedModules
         */
        public List<Module> getGrantedModules()
        {
                return grantedModules;
        }

        /**
         * @param grantedModules the grantedModules to set
         */
        public void setGrantedModules(List<Module> grantedModules)
        {
                this.grantedModules = grantedModules;
        }
        
        public Module getModuleByCode(String moduleCode)
        {
                for( Module module : grantedModules )
                {
                        if( module.getCode().equalsIgnoreCase(moduleCode))
                        {
                                return module;
                        }
                }
                
                return null;
        }

        @Override
        public String toString()
        {
                return "Subsys ["
                                + (code != null ? "code=" + code + ", " : "")
                                + (name != null ? "name=" + name + ", " : "")
                                + (abbr != null ? "abbr=" + abbr + ", " : "")
                                + (welcomePage != null ? "welcomePage=" + welcomePage + ", " : "")
                                + (orderby != null ? "orderby=" + orderby + ", " : "")
                                + (grantedModules != null ? "grantedModules=" + grantedModules + ", " : "")
                                + (getId() != null ? "getId()=" + getId() + ", " : "")
                                + (getModelLastUpdateTime() != null ? "getModelLastUpdateTime()="
                                                + getModelLastUpdateTime() + ", " : "")
                                + (getModelCreateTime() != null ? "getModelCreateTime()=" + getModelCreateTime() + ", "
                                                : "")
                                + (getModelIsDeleted() != null ? "getModelIsDeleted()=" + getModelIsDeleted() + ", "
                                                : "")
                                + (getModelDeleteTime() != null ? "getModelDeleteTime()=" + getModelDeleteTime() : "")
                                + "]";
        }

        public String getWelcomePage()
        {
                return welcomePage;
        }

        public void setWelcomePage(String welcomePage)
        {
                this.welcomePage = welcomePage;
        }
        
        public static void main(String[] args)
        {
                Db.ORACLE.generate(Subsys.class);
        }
        
        
        
        
}