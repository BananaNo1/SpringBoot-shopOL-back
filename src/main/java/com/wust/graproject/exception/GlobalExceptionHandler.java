package com.wust.graproject.exception;

import com.wust.graproject.global.ResultDataDto;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @ClassName GlobalExceptionHandler
 * @Description TODO
 * @Author leis
 * @Date 2019/1/25 17:07
 * @Version 1.0
 **/
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler(value = BindException.class)
    public ResultDataDto bindException(BindException e){
        List<ObjectError> allErrors = e.getAllErrors();
        ObjectError objectError = allErrors.get(0);
        String msg = objectError.getDefaultMessage();
        return ResultDataDto.operationErrorByMessage(msg);
    }
}
