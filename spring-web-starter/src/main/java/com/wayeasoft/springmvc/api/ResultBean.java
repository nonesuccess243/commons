package com.wayeasoft.springmvc.api;

import java.io.Serializable;

/**
 * 只准springmvc的controller使用，不准向后传
 * 
 * @author niyuzhe
 *
 * @param <T>
 */
public class ResultBean<T> implements Serializable
{
        private static final long serialVersionUID = 1L;

        public static final int SUCCESS = 200;

        public static final int FAIL = 500;

        public static final int NO_PERMISSION = 403;
        
        /**
         * 仅作为调试使用，不给前端直接显示
         */
        private String msg = "success";

        
        /**
         * 返回码
         * 
         * 含义参考Http状态码
         * 
         * 2xxx代表请求成功，默认为200
         * 
         * 4xxx代表客户端提交数据错误
         * 
         * 5xxx代表服务端错误
         * 
         */
        private int code = SUCCESS;

        private T data;

        public ResultBean()
        {
                super();
        }

        public ResultBean(T data)
        {
                super();
                this.data = data;
        }

        public ResultBean(Throwable e)
        {
                super();
                this.msg = e.getMessage();
                this.code = FAIL;
        }
        
        /**
         * 用ApiException的子类创建ResultBean
         * 
         * @param exception
         */
        public ResultBean( ApiException exception )
        {
                super();
                this.code = exception.getErrorCode();
                this.msg = exception.getErrorMessage();
        }
        
        public String getMsg()
        {
                return msg;
        }

        public void setMsg(String msg)
        {
                this.msg = msg;
        }

        public int getCode()
        {
                return code;
        }

        public void setCode(int code)
        {
                this.code = code;
        }

        public T getData()
        {
                return data;
        }

        public void setData(T data)
        {
                this.data = data;
        }

}
