package com.xc.justforjoy.result;

/**
 * @author lxcecho
 * @since 2020/8/5
 * <p>
 * 通用响应体
 */
public interface CommonResponse {

    /**
     * 获取状态码
     *
     * @return
     */
    String getCode();

    /**
     * 获取消息
     *
     * @return
     */
    String getMessage();

    /**
     * 响应结果
     *
     * @return
     */
    Object getResult();

}
