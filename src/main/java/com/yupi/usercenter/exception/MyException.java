package com.yupi.usercenter.exception;

import com.yupi.usercenter.enums.ErrorCodeEnum;
import lombok.Getter;

/**
 * 全局异常处理器
 */
@Getter
public class MyException extends RuntimeException {

    /**
     * 错误码
     */
    private final int code;

    /**
     * 报错信息
     */
    private final String msg;


    /**
     * @param msg
     * @param code
     */
    public MyException(String msg, int code) {
        this.msg = msg;
        this.code = code;
    }

    /**
     * @param errorCode
     */
    public MyException(ErrorCodeEnum errorCode) {
        this.msg = errorCode.getMsg();
        this.code = errorCode.getCode();
    }

}