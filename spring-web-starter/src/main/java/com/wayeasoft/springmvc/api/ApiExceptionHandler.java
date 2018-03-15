package com.wayeasoft.springmvc.api;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ApiExceptionHandler
{
        @ResponseBody
        @ExceptionHandler(ApiException.class)
        public ResponseEntity<String> handleApiException(ApiException e)
        {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                String result = "{\"message\":\"" +e.getMessage() + "\",\"code\":\"" + e.getErrorCode() + "\"}"; 
                return new ResponseEntity<>(result, headers, HttpStatus.BAD_REQUEST);
        }

}
