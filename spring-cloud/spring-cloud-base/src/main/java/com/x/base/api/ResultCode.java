package com.x.base.api;

/**
 * @ClassName ResultCode
 * @Description 枚举了一些常用API操作码
 * SuppressWarnings("AlibabaEnumConstantsMustHaveComment") 去除枚举类型字段必须要有注释说明的警告
 * @Author X
 * @Date 2022/7/23 15:33
 * @Version 1.0
 **/
@SuppressWarnings("AlibabaEnumConstantsMustHaveComment")
public enum ResultCode implements IErrorCode {
    SUCCESS(200, "操作成功!"),
    //参数缺失或者为空或者长度为0时候可用这个异常
    PARAMS_FORMAT_ERROR(300, "参数异常，请联系管理员"),
    //可用于一般后台不符合逻辑的异常
    VALIDATE_FAILED(400, "接口异常，请联系管理员"),
    UNAUTHORIZED(401, "没有相关权限"),
    //系统自带的异常，比如执行sql，空指针等异常
    FAILED(500, "系统异常，请联系管理员!");
    private final long code;
    private final String message;

    ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * valueOf 转换为字符串类型
     */
    @Override
    public String getCode() {
        return String.valueOf(code);
    }

    @Override
    public String getMessage() {
        return message;
    }
}