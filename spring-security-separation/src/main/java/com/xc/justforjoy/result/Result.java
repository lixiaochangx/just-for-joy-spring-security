package com.xc.justforjoy.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lxcecho
 * @since 2020/8/5
 * <p>
 * HTTP 统一响应结果
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {

    /**
     * 响应码
     */
    private String code;

    /**
     * 提示信息
     */
    private String message;

    /**
     * 返回结果
     */
    private T result;

    public Result(ResultEnum resultEnum) {
        this.code = resultEnum.getCode();
        this.message = resultEnum.getMessage();
    }

    public Result(CommonResponse commonResponse) {
        this.code = commonResponse.getCode();
        this.message = commonResponse.getMessage();
    }

    /**
     * 成功响应，但不返回结果
     */
    public static <T> Result<T> success() {
        return new Result(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage(), null);
    }

    /**
     * 成功响应，并返回结果
     */
    public static <T> Result<T> success(T result) {
        return new Result(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage(), result);
    }

    /**
     * 失败，返回状态码和状态信息
     */
    public static Result<ResultEnum> error(ResultEnum resultEnum) {
        return new Result(resultEnum.getCode(), resultEnum.getMessage(), null);
    }

    /**
     * 失败，返回状态码和状态信息
     * 用于处理全局异常处理类CustomControllerAdvice
     */
    public static Result<ResultEnum> error(String code, String message) {
        return new Result(code, message, null);
    }

    /**
     * 禁止操作
     */
    public static Result forbidden() {
        return new Result(ResultEnum.FORBIDDEN_OPERATION.getCode(), ResultEnum.FORBIDDEN_OPERATION.getMessage(), null);
    }

}

