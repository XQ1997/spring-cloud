package com.x.base.api;

/**
 * @ClassName IErrorCode
 * @Description 封装API的错误码
 * @Author X
 * @Date 2022/7/23 15:33
 * @Version 1.0
 **/
public interface IErrorCode {

    /**获取错误编码code
     * @return 错误码
     */
    String getCode();

    /**获取错误信息
     * @return 错误信息
     */
    String getMessage();
}