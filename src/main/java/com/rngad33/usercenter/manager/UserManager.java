package com.rngad33.usercenter.manager;

import com.rngad33.usercenter.constant.ErrorConstant;
import com.rngad33.usercenter.constant.UserConstant;
import com.rngad33.usercenter.model.enums.UserRoleEnum;
import com.rngad33.usercenter.model.request.UserManageRequest;
import com.rngad33.usercenter.model.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 通用用户操作
 */
@Service
public class UserManager {

    /**
     * 正向鉴权（面向请求）
     *
     * @param request http请求
     * @return 是否（TF）为管理员
     */
    public boolean isAdmin(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        User user = (User) userObj;
        if (user == null || !Objects.equals(user.getRole(), UserRoleEnum.ADMIN_ROLE.getCode())) {
            System.out.println(ErrorConstant.USER_NOT_AUTH_MESSAGE);
            return false;
        }
        return true;
    }

    /**
     * 正向鉴权（面向用户）
     *
     * @param user 用户
     * @return 是否（TF）为管理员
     */
    public boolean isAdmin(User user) {
        if (user == null || !Objects.equals(user.getRole(), UserRoleEnum.ADMIN_ROLE.getCode())) {
            System.out.println(ErrorConstant.USER_NOT_AUTH_MESSAGE);
            return false;
        }
        return true;
    }

    /**
     * 反向鉴权（面向请求）
     *
     * @param request http请求
     * @return 是否（TF）为管理员
     */
    public boolean isNotAdmin(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        User user = (User) userObj;
        if (user == null || !Objects.equals(user.getRole(), UserRoleEnum.ADMIN_ROLE.getCode())) {
            System.out.println(ErrorConstant.USER_NOT_AUTH_MESSAGE);
            return true;
        }
        return false;
    }

    /**
     * 反向鉴权（面向用户）
     *
     * @param user 用户
     * @return 是否（TF）为管理员
     */
    public boolean isNotAdmin(User user) {
        if (user == null || !Objects.equals(user.getRole(), UserRoleEnum.ADMIN_ROLE.getCode())) {
            System.out.println(ErrorConstant.USER_NOT_AUTH_MESSAGE);
            return true;
        }
        return false;
    }

    /**
     * id传递
     *
     * @param userManageRequest 用户管理请求体
     * @param request 用户登录态
     * @return id
     */
    public Long getId(UserManageRequest userManageRequest, HttpServletRequest request) {
        if (isNotAdmin(request)) return null;   // 鉴权，仅管理员可操作
        if (userManageRequest == null) return null;   // 验证请求体
        Long id = userManageRequest.getId();
        if (id <= 0) return null;
        return id;
    }

}