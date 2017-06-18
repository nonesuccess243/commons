package com.nbm.core.modeldriven.test.model;

import com.nbm.core.modeldriven.PureModel;
import com.nbm.core.modeldriven.anno.Fk;

public class FkModel extends PureModel
{

        /**
         * 
         */
        private static final long serialVersionUID = 1L;
        
        private Long modelDrivenTestModelId;

        public Long getModelDrivenTestModelId()
        {
                return modelDrivenTestModelId;
        }

        public void setModelDrivenTestModelId(Long modelDrivenTestModelId)
        {
                this.modelDrivenTestModelId = modelDrivenTestModelId;
        }
        
        @Fk(foreign = ModelDrivenTestModel.class)
        private Long otherModelDrivenTestModelId;

        public Long getOtherModelDrivenTestModelId()
        {
                return otherModelDrivenTestModelId;
        }

        public void setOtherModelDrivenTestModelId(Long otherModelDrivenTestModelId)
        {
                this.otherModelDrivenTestModelId = otherModelDrivenTestModelId;
        }
        

}
