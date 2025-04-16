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
     * @param <T> 数据类型
     * @return
     */
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(0, data, "ok");
    }

    /**
     * 失败
     *
     * @param errorCode 错误码
     * @return
     */
    public static BaseResponse error(ErrorCodeEnum errorCode) {
        return new BaseResponse<>(errorCode);
    }

    /**
     * 失败
     *
     * @param code 状态码
     * @param message 信息
     * @param description 描述
     * @return
     */
    public static BaseResponse error(int code, String message, String description) {
        return new BaseResponse(code, null, message, description);
    }

    /**
     * 失败
     *
     * @param errorCode 错误码
     * @return
     */
    public static BaseResponse error(ErrorCodeEnum errorCode, String message, String description) {
        return new BaseResponse(errorCode.getCode(), null, message, description);
    }

    // https://space.bilibili.com/12890453/

    /**
     * 失败
     *
     * @param errorCode 错误码
     * @return
     */
    public static BaseResponse error(ErrorCodeEnum errorCode, String description) {
        return new BaseResponse(errorCode.getCode(), errorCode.getMessage(), description);
    }

}