package com.nbm.core.modeldriven.data.exception;

public class ApiError
{
        private int code;
        private String message;

        public int getCode()
        {
                return code;
        }

        public String getMessage()
        {
                return message;
        }

        public ApiError(int code, String message)
        {
                super();
                this.code = code;
                this.message = message;
        }

}
