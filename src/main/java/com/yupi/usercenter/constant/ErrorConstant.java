package com.yupi.usercenter.constant;

/**
 * 错误信息常量类
 */
public interface ErrorConstant {

    String USER_NOT_EXIST_MESSAGE = "——！用户不存在！——";

    String USER_NAME_ALREADY_EXIST_MESSAGE = "——！用户名已存在！——";

    String PLANET_CODE_ALREADY_EXIST_MESSAGE = "——！编号已存在！——";

    String USER_NOT_LOGIN_MESSAGE = "——！用户未登录！——";

    String USER_NOT_AUTH_MESSAGE = "——！用户未授权！——";

    String USER_ALREADY_BAN_MESSAGE = "——！用户已被封禁！——";

    String USER_NOT_EXIST_OR_PASSWORD_ERROR_RETRY_MESSAGE = "——！用户不存在或密码错误，请重试！——";

    String USER_TOO_MANY_TIMES_MESSAGE = "——！请求超时！——";

    String USER_HAVE_NULL_CHAR_MESSAGE = "——！字段不能为空！——";

    String USER_HAVE_SPECIAL_CHAR_MESSAGE = "——！用户名或密码不能包含特殊字符！——";

    String USER_LOSE_ACTION_MESSAGE = "————！操作失败！————";

}