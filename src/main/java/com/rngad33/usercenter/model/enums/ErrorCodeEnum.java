package com.rngad33.usercenter.model.enums;

import lombok.Getter;

/**
 * 自定义异常枚举
 */
@Getter
public enum ErrorCodeEnum {

    SUCCESS("OK >>>", 0),
    USER_NOT_EXIST_OR_PASSWORD_ERROR_RETRY("——！用户不存在或密码错误，请重试！——", 4042),
    USER_TOO_MANY_TIMES("——！请求超时！——", 5002),
    USER_NOT_LOGIN_MESSAGE("——！请先登录！——", 4011),
    USER_NOT_AUTH( "——！用户未授权！——", 4012),
    USER_LOSE_ACTION("————！！操作失败！！————", 4048),
    PARAMS_ERROR("——！参数不合法！——", 4023),
    NO_PARAMS("——！参数不能为空！——", 4024),
    SYSTEM_ERROR("————！系统内部异常！————", 5000);

    private final String msg;

    private final int code;

    ErrorCodeEnum(String msg, int code) {
        this.msg = msg;
        this.code = code;
    }
}