package com.nbm.core.modeldriven.test.model;

import com.nbm.core.modeldriven.Model;
import com.nbm.core.modeldriven.anno.DbIgnore;
import com.nbm.core.modeldriven.anno.ModelInfo;
import com.nbm.core.modeldriven.anno.NameCol;
import com.nbm.core.modeldriven.enums.YesOrNo;
import com.nbm.core.modeldriven.generator.CrudGenerator;

@ModelInfo(tableName="TEST_MODEL")
public class ModelDrivenTestModel extends Model
{
        
        @Override
        public String toString()
        {
                return "ModelDrivenTestModel [name=" + name + ", remark=" + remark + ", state="
                                + state + ", getId()=" + getId() + ", getModelLastUpdateTime()="
                                + getModelLastUpdateTime() + ", getModelCreateTime()="
                                + getModelCreateTime() + ", getModelIsDeleted()="
                                + getModelIsDeleted() + ", getModelDeleteTime()="
                                + getModelDeleteTime() + "]";
        }

        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        @NameCol
        private String name;
        
        private String remark;
        
        @DbIgnore
        private String state;
        
        private YesOrNo yesOrNo;

        

        public String getName()
        {
                return name;
        }

        public void setName(String name)
        {
                this.name = name;
        }

        public String getRemark()
        {
                return remark;
        }

        public void setRemark(String remark)
        {
                this.remark = remark;
        }
        
        public String getState()
        {
                return state;
        }

        public void setState(String state)
        {
                this.state = state;
        }
        
        public static void main(String[] args) throws Exception
        {
                CrudGenerator gen = new CrudGenerator(ModelDrivenTestModel.class);
                gen.generate();
        }

        public YesOrNo getYesOrNo()
        {
                return yesOrNo;
        }

        public void setYesOrNo(YesOrNo yesOrNo)
        {
                this.yesOrNo = yesOrNo;
        }
}
