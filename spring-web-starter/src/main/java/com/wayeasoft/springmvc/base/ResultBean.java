package com.wayeasoft.springmvc.base;

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

        private String msg = "success";

        
        /**
         * 返回码
         * 
         * 含义参考Http状态码
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
                this.msg = e.toString();
                this.code = FAIL;
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
