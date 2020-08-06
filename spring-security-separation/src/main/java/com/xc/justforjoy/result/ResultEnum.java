package com.xc.justforjoy.result;

/**
 * @author lxcecho
 * @since 2020/8/5
 * <p>
 * 返回结果的状态码及提示信息
 */
public enum ResultEnum implements CommonResponse {

    /**
     * 200 成功请求
     */
    SUCCESS("200", "SUCCESS"),

    /**
     * 401 权限不足
     */
    FORBIDDEN_OPERATION("401", "FORBIDDEN"),

    /**
     * 500 系统异常
     */
    SYSTEM_ERROR("500", "系统异常"),

    /**
     * 自定义相应结果为 500+
     */
    ;

    private String code;

    private String message;

    ResultEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Object getResult() {
        return null;
    }

}
