package com.wayeasoft.modeldriven;

import java.util.List;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.nbm.commons.db.meta.DbNamingConverter;
import com.nbm.core.modeldriven.PureModel;
import com.nbm.core.modeldriven.data.ModelRegister;
import com.nbm.core.modeldriven.data.exception.ApiError;
import com.nbm.core.modeldriven.data.exception.ModelNotRegisterException;
import com.wayeasoft.core.modeldriven.dao.CommonExample;
import com.wayeasoft.waf.springmvc.BaseController;

@RestController
@RequestMapping("/data")
public class DataController extends BaseController
{
        
        @Autowired
        private ModelRegister modelRegister;
        
        @RequestMapping("/fetch/{modelName}/{id}")
        public PureModel fetch(@PathVariable("modelName") String modelName, @PathVariable("id") Long id) throws ModelNotRegisterException
        {
                        return dao.selectById(modelRegister.get(modelName), id);
        }
        
        /**
         * 
         * @param modelName
         * @param pageNo TODO 未实现
         * @return
         * @throws ModelNotRegisterException 
         */
        @RequestMapping("/list/{modelName}")
        public List<? extends PureModel> list(@PathVariable String modelName, @RequestParam(defaultValue="0") int pageNo, @RequestParam(defaultValue="10") int rows, HttpServletRequest req) throws ModelNotRegisterException
        {
                CommonExample example = new CommonExample();
                for( Entry<String, String[]> e : req.getParameterMap().entrySet() )
                {
                        example.createCriteria().andEqualTo(DbNamingConverter.DEFAULT_ONE.javaPropertyName2ColumnName(e.getKey()), e.getValue()[0]);
                }
                
                return dao.selectByExample(modelRegister.get(modelName), example);
        }
        
        @ExceptionHandler(ModelNotRegisterException.class)
        @ResponseStatus(HttpStatus.NOT_FOUND)
        public ApiError modelNotRegistered( ModelNotRegisterException e)
        {
                return new ApiError(0, e.getModelName() + "未注册");
        }

}
