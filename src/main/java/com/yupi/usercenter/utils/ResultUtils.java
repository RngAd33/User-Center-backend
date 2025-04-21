package com.yupi.usercenter.utils;

import com.yupi.usercenter.common.BaseResponse;
import com.yupi.usercenter.enums.ErrorCodeEnum;

/**
 * 返回工具类
 */
public class ResultUtils {

    /**
     * 成功
     *
     * @param data 数据
     * @param <T> 泛型
     * @return
     */
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(0, data, "OK >>>");
    }

    /**
     * 失败
     *
     * @param code 自传状态码
     * @param message 自传信息
     * @return
     */
    public static BaseResponse error(int code, String message) {
        return new BaseResponse(code, null, message);
    }

    /**
     * 失败
     *
     * @param errorCode 错误码枚举
     * @param message 自传消息
     * @return
     */
    public static BaseResponse error(ErrorCodeEnum errorCode, String message) {
        return new BaseResponse(errorCode.getCode(), null, message);
    }

    /**
     * 失败
     *
     * @param errorCode 错误码枚举
     * @return
     */
    public static BaseResponse error(ErrorCodeEnum errorCode) {
        return new BaseResponse(errorCode.getCode(), null, errorCode.getMessage());
    }

}