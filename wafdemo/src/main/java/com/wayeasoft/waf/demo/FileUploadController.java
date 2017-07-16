package com.wayeasoft.waf.demo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/fileupload/")
public class FileUploadController
{
        private final static Logger log = LoggerFactory.getLogger(FileUploadController.class);

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

        @RequestMapping("upload.do")
        public String echoParam(@RequestParam("name") String name, @RequestPart("file1") MultipartFile file1)
        { 
                log.debug(file1.toString());
                
                log.debug("param name={}", name);
                
                return "/fileupload";
        
        }
        
        
}
