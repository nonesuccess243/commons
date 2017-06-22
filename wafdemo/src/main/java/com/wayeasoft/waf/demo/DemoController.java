package com.wayeasoft.waf.demo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo/")
public class DemoController
{
        private final static Logger log = LoggerFactory.getLogger(DemoController.class);

        /**
         * 
         * @param binder
         */
        @InitBinder
        public void initBinder(WebDataBinder binder)
        {
                // 设置日期类型的字段，前端按照yyyy-MM-dd显示 or 解析？忘了
                binder.registerCustomEditor(Date.class,
                                new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true, 10));
        }

        @RequestMapping("echoParam.do")
        @ResponseBody
        public DemoModel echoParam(DemoModel demoModel)
        { 
                if( demoModel.getRemarks() == null )
                {
                        demoModel.setRemarks(new String[0]);
                }
                
                String[] remarks = new String[demoModel.getRemarks().length + 3];
                for( int i = 0; i<demoModel.getRemarks().length; i++ )
                {
                        remarks[i] = demoModel.getRemarks()[i];
                }
                
                remarks[demoModel.getRemarks().length] = "remarks1";
                remarks[demoModel.getRemarks().length + 1] = "remarks2";
                remarks[demoModel.getRemarks().length + 2] = "remarks3";
                demoModel.setRemarks(remarks);
                
                return demoModel;
        
        }
        
        @RequestMapping("echo.do")
        @ResponseBody
        public Map<String, String> echo(@RequestParam Map<String, String> params)
        { 
                for( String key : params.keySet())
                {
                        try
                        {
                                params.put(key, "" + Integer.parseInt(params.get(key)) * 2);
                        } catch (NumberFormatException e)
                        {
                                params.put(key, params.get(key).toUpperCase());
                        }
                }
                return params;
        }
        
}
