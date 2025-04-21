package com.yupi.usercenter.exception;

import com.yupi.usercenter.enums.ErrorCodeEnum;
import lombok.Getter;

/**
 * 全局异常处理器
 */
@Getter
public class MyException extends RuntimeException{

    /**
     * 错误码
     */
    private final int code;

    /**
     * 报错信息
     */
    private final String msg;

    /**
     * @param code
     * @param msg
     */
    public MyException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    /**
     * @param errorCode
     */
    public MyException(ErrorCodeEnum errorCode) {
        this.code = errorCode.getCode();
        this.msg = errorCode.getMessage();
    }

}