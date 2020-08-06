package com.xc.justforjoy.exception;

import com.xc.justforjoy.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author lxcecho
 * @since 2020/8/5
 * <p>
 * 全局异常处理
 */
@ControllerAdvice
@Slf4j
public class CustomControllerAdvice {

    /**
     * 捕捉自定义异常 CustomException
     *
     * @param ce
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = CustomException.class)
    public Result customErrorHandler(CustomException ce) {
        log.error(ce.getMessage());
        ce.printStackTrace();
        return Result.error(ce.getCode(), ce.getMessage());
    }

    /**
     * 捕捉全局异常 Exception
     *
     * @param ce
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Result errorHandler(Exception ce) {
        log.error(ce.getMessage());
        ce.printStackTrace();
        return Result.error("-1", ce.getMessage());
    }

}
