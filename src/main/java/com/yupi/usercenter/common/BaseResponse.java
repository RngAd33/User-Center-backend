package com.yupi.usercenter.common;

import com.yupi.usercenter.enums.ErrorCodeEnum;
import java.io.Serializable;
import lombok.Data;

/**
 * 通用返回类
 */
@Data
public class BaseResponse<T> implements Serializable {

    /**
     * 状态码
     */
    private int code;

    /**
     * 泛型数据
     */
    private T data;

    /**
     * 消息
     */
    private String msg;

    public BaseResponse(int code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public BaseResponse(int code, T data) {
        this(code, data, "");
    }

    public BaseResponse(ErrorCodeEnum errorCode) {
        this(errorCode.getCode(), null, errorCode.getMessage());
    }
}