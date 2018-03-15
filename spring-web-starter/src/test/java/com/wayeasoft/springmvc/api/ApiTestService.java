package com.wayeasoft.springmvc.api;

import org.springframework.stereotype.Service;

import com.nbm.exception.NbmBaseRuntimeException;

@Service
public class ApiTestService
{
        public Integer getTestInteger( int input )
        {
                if( input == 0 )
                {
                        return input;
                }else 
                {
                        throw new NbmBaseRuntimeException("input error").set("input", input);
                }
        }

}
