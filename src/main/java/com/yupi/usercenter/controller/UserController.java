package com.yupi.usercenter.controller;

import com.yupi.usercenter.constant.ErrorConstant;
import com.yupi.usercenter.enums.UserRoleEnum;
import com.yupi.usercenter.model.User;
import com.yupi.usercenter.model.request.UserLoginRequest;
import com.yupi.usercenter.model.request.UserManageRequest;
import com.yupi.usercenter.model.request.UserRegisterRequest;
import com.yupi.usercenter.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * 用户接口
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 用户注册
     *
     * @param userRegisterRequest 注册请求体
     * @return id
     * @throws Exception
     */
    @PostMapping("/register")
    public Long userRegister(@RequestBody UserRegisterRequest userRegisterRequest) throws Exception {
        if (userRegisterRequest == null) {
            return null;
        }
        String userName = userRegisterRequest.getUserName();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        String planetCode = userRegisterRequest.getPlanetCode();
        // 校验参数（倾向于对参数本身的校验，不涉及业务逻辑）
        if (StringUtils.isAnyBlank(userName, userPassword, checkPassword, planetCode)) {
            return null;
        }
        return userService.userRegister(userName, userPassword, checkPassword, planetCode);
    }

    /**
     * 用户登录
     *
     * @param userLoginRequest 登录请求体
     * @return 脱敏后的账户信息
     * @throws Exception
     */
    @PostMapping("/login")
    public User userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) throws Exception {
        if (userLoginRequest == null) {
            return null;
        }
        String userName = userLoginRequest.getUserName();
        String userPassword = userLoginRequest.getUserPassword();
        String planetCode = userLoginRequest.getPlanetCode();
        // 校验参数（倾向于对参数本身的校验，不涉及业务逻辑）
        if (StringUtils.isAnyBlank(userName, userPassword, planetCode)) {
            return null;
        }
        return userService.userLogin(userName, userPassword, planetCode, request);
    }

    /**
     * 获取当前用户状态
     *
     * @param request http请求
     * @return 登录态
     */
    @GetMapping("/current")
    public User getCurrentUser(HttpServletRequest request) {
        return userService.getCurrentUser(request);
    }

    /**
     * 退出登录
     *
     * @param request http请求
     * @return 状态码
     */
    @PostMapping("/logout")
    public Integer userLogout(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        return userService.userLogout(request);
    }

    /**
     * 用户查询（仅管理员）
     *
     * @param userName 用户名
     * @return 用户列表
     */
    @GetMapping("/admin/search")
    public List<User> searchUsers(String userName, HttpServletRequest request) {
        // 鉴权，仅管理员可操作
        if (isNotAdmin(request)) {
            return new ArrayList<>();
        }
        return userService.searchUsers(userName, request);
    }

    /**
     * 用户删除（仅管理员，逻辑删除）
     *
     * @param userManageRequest 用户管理请求体
     * @return 删除成功与否
     */
    @PostMapping("/admin/delete")
    public boolean userDelete(@RequestBody UserManageRequest userManageRequest, HttpServletRequest request) {
        Long id = getId(userManageRequest, request);
        if (id == null) {
            return false;
        }
        return userService.removeById(id);   // 无需业务层
    }

    /**
     * 用户封禁 / 解封（仅管理员）
     *
     * @param userManageRequest 用户管理请求体
     * @return 操作后用户状态
     */
    @PostMapping("/admin/ban")
    public Integer userOrBan(@RequestBody UserManageRequest userManageRequest, HttpServletRequest request) {
        Long id = getId(userManageRequest, request);
        if (id == null) {
            return -1;
        }
        return userService.userOrBan(id, request);
    }

    /**
     * 用户注销（暂时仅管理员）
     *
     * @param userManageRequest 用户管理请求体
     * @return 状态码
     */
    @PostMapping("/admin/logoff")
    public Integer userLogoff(@RequestBody UserManageRequest userManageRequest, HttpServletRequest request) {
        Long id = getId(userManageRequest, request);
        if (id == null) {
            return -1;
        }
        return userService.userLogoff(id, request);
    }

    /**
     * 鉴权
     *
     * @param request http请求
     * @return 是否（TF）为管理员
     */
    private static boolean isNotAdmin(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(UserRoleEnum.USER_LOGIN_STATE.getKey());
        User user = (User) userObj;
        if (user == null || !Objects.equals(user.getRole(), UserRoleEnum.ADMIN_ROLE.getCode())) {
            System.out.println(ErrorConstant.USER_NOT_AUTH_MESSAGE);
            return true;
        }
        return false;
    }

    /**
     * 用户id传递
     *
     * @param userManageRequest 用户管理请求体
     * @param request 用户登录态
     * @return id
     */
    private static Long getId(UserManageRequest userManageRequest, HttpServletRequest request) {
        if (isNotAdmin(request)) return null;   // 鉴权，仅管理员可操作
        if (userManageRequest == null) return null;   // 验证请求体
        Long id = userManageRequest.getId();
        if (id <= 0) return null;
        return id;
    }

}