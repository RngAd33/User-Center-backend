package com.yupi.usercenter.enums;

import lombok.Getter;

/**
 * 用户状态枚举
 */
@Getter
public enum UserEnum {

    USER_LOGIN_STATE("用户态键", "userLoginState"),
    ADMIN_ROLE("管理员", 1),
    DEFAULT_ROLE("普通用户", 0),
    BAN_ROLE("封禁用户", -1);

    private final String status;

    private final Integer code;

    private final String key;

    UserEnum(String status, Integer code) {
        this.status = status;
        this.code = code;
        this.key = null;
    }

    UserEnum(String status, String key) {
        this.status =status;
        this.code = 0;
        this.key = key;
    }

}