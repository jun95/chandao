package com.selfboot.chandao.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hwj on 2019/3/15.
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    private static Logger logger =  LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    public Map<String,Object> exceptionHandler(Exception e){
        //e.printStackTrace();
        logger.error("错误信息为：{}",e);
        Map<String,Object> map = new HashMap<String, Object>();
        if(e instanceof GlobalException) {
            GlobalException ex= (GlobalException)e;
            map.put("msg",(ex.getMsg()));
        } else if ( e instanceof BindException){
            BindException ex = (BindException) e  ;
            List<ObjectError> errors = ex.getAllErrors();
            ObjectError error = errors.get(0);
            String msg = error.getDefaultMessage();

            logger.error("错误信息为：{}",msg);
            map.put("msg","Session不存在或者已经失效");
        } else {
            map.put("msg","系统错误");
        }
        return map;
    }
}
