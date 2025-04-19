package com.yupi.usercenter.enums;

import lombok.Getter;

/**
 * 自定义异常枚举
 */
@Getter
public enum ErrorCodeEnum {

    USER_NOT_EXIST_OR_PASSWORD_ERROR_RETRY("——！用户不存在或密码错误，请重试！——", 401),
    USER_TOO_MANY_TIMES("——！请求超时！——", 429),
    USER_LOSE_ACTION("————！操作失败！————", 400);

    private final String message;

    private final int code;

    ErrorCodeEnum(String message, int code) {
        this.message = message;
        this.code = code;
    }
}