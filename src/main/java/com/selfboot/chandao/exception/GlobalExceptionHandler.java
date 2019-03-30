package com.selfboot.chandao.exception;

import com.selfboot.chandao.common.ResponseResult;
import com.selfboot.chandao.common.ResponseStatus;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

/**
 * Created by hwj on 2019/3/15.
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    private static Logger logger =  LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    public ResponseResult<String> exceptionHandler(Exception e){
        //e.printStackTrace();
        ResponseResult<String> result = new ResponseResult<>();
        result.setResponseStatus(ResponseStatus.ERROR);
        logger.error("错误信息为：{}",e.getMessage());
        if(e instanceof GlobalException) {
            GlobalException ex= (GlobalException)e;
            result.setMessage(ex.getMsg());
        } else if ( e instanceof BindException){
            BindException ex = (BindException) e  ;
            List<ObjectError> errors = ex.getAllErrors();
            ObjectError error = errors.get(0);
            String msg = error.getDefaultMessage();

            logger.error("错误信息为：{}",msg);
            result.setMessage(msg);
        } else if(e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException ex = (MethodArgumentNotValidException) e;
            BindingResult bindingResult = ex.getBindingResult();

            FieldError fieldError = bindingResult.getFieldErrors().get(0);
            result.setMessage(fieldError.getDefaultMessage());
        } else if (e instanceof ConstraintViolationException){
            ConstraintViolationException exs = (ConstraintViolationException) e;
            Set<ConstraintViolation<?>> violations = exs.getConstraintViolations();
            for (ConstraintViolation<?> item : violations) {
                result.setMessage(item.getMessage());
                break;
            }
        } else if (e instanceof AuthorizationException) {
            result.setMessage("你没有权限进行操作");
        } else {
            result.setMessage("系统错误");
        }

        return result;
    }
}
