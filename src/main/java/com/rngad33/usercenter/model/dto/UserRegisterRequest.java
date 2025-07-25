package com.rngad33.usercenter.model.dto;

import lombok.Data;
import java.io.Serializable;

/**
 * 用户注册请求体
 */
@Data
public class UserRegisterRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    private String userName, userPassword, checkPassword, planetCode;

}