package com.zs.minio.enums;


/**
 * @author word
 */

public enum ResultEnum {
    //未知错误

    //默认成功
    /**
     * SUCCESS    成功
     * <p>
     * ERROR_301		TOKEN失效，需要重新登录
     * ERROR_400  	请求无效
     * ERROR_401  	未授权访问
     * ERROR_404  	请求的网页/路径不存在
     * ERROR_500  	未知错误
     * SUCCESS_201	操作异常,需要进行验证
     */
    SUCCESS(200, "请求成功"),
    FAILED(500, "请求失败"),
    PARAM_ERROR(4000, "参数错误"),
    USER_PASSWORD_ERROR(4001, "用户名或者密码错误"),
    TOKEN_ERROR(4002, "token失效，请重新登录"),
    UNAUTHORIZED_ACCESS(4003, "未授权的访问"),
    PAGE_NOT_EXIST(4004, "请求的网页/路径不存在"),
    SYSTEM_ERROR(5000, "系统错误:请联系管理员");


    private final Integer code;
    private final String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
