package com.yupi.usercenter.enums;

import lombok.Getter;

/**
 * 自定义异常枚举
 */
@Getter
public enum ErrorCodeEnum {

    USER_NOT_EXIST("——！用户不存在！——", 400),
    USER_ALREADY_EXIST("——！用户已存在！——", 400),
    USER_NOT_LOGIN("——！用户未登录！——", 401),
    USER_NOT_AUTH("——！用户未授权！——", 403),
    USER_ALREADY_BAN("——！用户已被封禁！——", 400),
    USER_NOT_EXIST_OR_PASSWORD_ERROR_RETRY("——！用户不存在或密码错误，请重试！——", 401),
    USER_TOO_MANY_TIMES("——！请求超时！——", 429),
    USER_HAVE_NULL_CHAR("——！字段不能为空！——", 400),
    USER_HAVE_SPECIAL_CHAR("——！用户名或密码不能包含特殊字符！——", 400),
    USER_LOSE_ACTION("————！操作失败！————", 400);

    private final String message;

    private final int code;

    ErrorCodeEnum(String message, int code) {
        this.message = message;
        this.code = code;
    }
}