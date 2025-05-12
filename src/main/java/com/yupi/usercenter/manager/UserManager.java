package com.yupi.usercenter.manager;

import com.yupi.usercenter.constant.ErrorConstant;
import com.yupi.usercenter.enums.UserRoleEnum;
import com.yupi.usercenter.model.User;
import com.yupi.usercenter.model.request.UserManageRequest;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Objects;

/**
 * 通用用户操作
 */
public class UserManager {

    /**
     * 鉴权（反向）
     *
     * @param request http请求
     * @return 是否（TF）为管理员
     */
    public boolean isNotAdmin(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(UserRoleEnum.USER_LOGIN_STATE.getKey());
        User user = (User) userObj;
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