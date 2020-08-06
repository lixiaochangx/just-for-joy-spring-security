package com.xc.justforjoy.exception;

import com.xc.justforjoy.result.ResultEnum;
import lombok.Data;

/**
 * @author lxcecho
 * @since 2020/8/5
 * <p>
 * 自定义异常
 */
@Data
public class CustomException extends RuntimeException {

    /**
     * 异常状态码
     */
    private final String code;
    /**
     * 异常信息
     */
    private final String message;

    public CustomException(ResultEnum resultEnum) {
        this.code = resultEnum.getCode();
        this.message = resultEnum.getMessage();
    }

    public CustomException(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
