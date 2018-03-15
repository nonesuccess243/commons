package com.wayeasoft.springmvc.api;

public class InputEvenException extends ApiException
{

        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        public InputEvenException()
        {
                super(ApiTestController.INPUT_EVEN_ERROR, "input cannot be even");
        }

}
