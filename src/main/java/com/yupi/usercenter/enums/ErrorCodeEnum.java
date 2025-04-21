package com.yupi.usercenter.enums;

import lombok.Getter;

/**
 * 自定义异常枚举
 */
@Getter
public enum ErrorCodeEnum {

    USER_NOT_EXIST_OR_PASSWORD_ERROR_RETRY("——！用户不存在或密码错误，请重试！——", 4042),
    USER_TOO_MANY_TIMES("——！请求超时！——", 5000),
    USER_LOSE_ACTION("————！操作失败！————", 4048),
    USER_NOT_AUTH( "——！用户未授权！——", 4001);

    private final String message;

    private final int code;

    ErrorCodeEnum(String message, int code) {
        this.message = message;
        this.code = code;
    }
}