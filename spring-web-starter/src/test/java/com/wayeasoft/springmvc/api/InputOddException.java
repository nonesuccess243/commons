package com.wayeasoft.springmvc.api;

public class InputOddException extends ApiException
{

        public InputOddException()
        {
                super(ApiTestController.INPUT_ODD_ERROR, "input cannot be odd");
        }

}
