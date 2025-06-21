package com.rngad33.usercenter.controller;

import com.rngad33.usercenter.exception.MyException;
import com.rngad33.usercenter.common.BaseResponse;
import com.rngad33.usercenter.model.enums.ErrorCodeEnum;
import com.rngad33.usercenter.manager.UserManager;
import com.rngad33.usercenter.model.entity.User;
import com.rngad33.usercenter.model.request.UserLoginRequest;
import com.rngad33.usercenter.model.request.UserManageRequest;
import com.rngad33.usercenter.model.request.UserRegisterRequest;
import com.rngad33.usercenter.service.UserService;
import com.rngad33.usercenter.utils.ResultUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户接口
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private UserManager userManager;

    /**
     * 用户注册
     *
     * @param userRegisterRequest 注册请求体
     * @return id
     * @throws Exception
     */
    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest)
            throws Exception {
        if (userRegisterRequest == null) {
            //  throw new RunException(ErrorCodeEnum.USER_LOSE_ACTION);
            throw new MyException(ErrorCodeEnum.USER_LOSE_ACTION);
        }
        String userName = userRegisterRequest.getUserName();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        String planetCode = userRegisterRequest.getPlanetCode();
        // 校验参数（倾向于对参数本身的校验，不涉及业务逻辑）
        if (StringUtils.isAnyBlank(userName, userPassword, checkPassword, planetCode)) {
            throw new MyException(ErrorCodeEnum.USER_LOSE_ACTION);
        }
        Long result = userService.userRegister(userName, userPassword, checkPassword, planetCode);
        return ResultUtils.success(result);
    }

    /**
     * 用户登录
     *
     * @param userLoginRequest 登录请求体
     * @return 脱敏后的账户信息
     * @throws Exception
     */
    @PostMapping("/login")
    public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest,
                                        HttpServletRequest request) throws Exception {
        if (userLoginRequest == null) {
            throw new MyException(ErrorCodeEnum.USER_LOSE_ACTION);
        }
        String userName = userLoginRequest.getUserName();
        String userPassword = userLoginRequest.getUserPassword();
        // 校验参数
        if (StringUtils.isAnyBlank(userName, userPassword)) {
            throw new MyException(ErrorCodeEnum.USER_LOSE_ACTION);
        }
        User user = userService.userLogin(userName, userPassword, request);
        return ResultUtils.success(user);
    }

    /**
     * 获取当前用户登录态
     *
     * @param request http请求
     * @return 登录态
     */
    @GetMapping("/current")
    public BaseResponse<User> getCurrentUser(HttpServletRequest request) {
        User user = userService.getCurrentUser(request);
        return ResultUtils.success(user);
    }

    /**
     * 退出登录
     *
     * @param request http请求
     * @return 状态码
     */
    @PostMapping("/logout")
    public BaseResponse<Integer> userLogout(HttpServletRequest request) {
        if (request == null) {
            throw new MyException(ErrorCodeEnum.USER_LOSE_ACTION);
        }
        Integer result = userService.userLogout(request);
        return ResultUtils.success(result);
    }

    /**
     * 用户查询（仅管理员）
     *
     * @param userName 用户名
     * @return 用户列表
     */
    @GetMapping("/admin/search")
    public BaseResponse<List<User>> searchUsers(String userName, HttpServletRequest request) {
        // 鉴权，仅管理员可操作
        if (userManager.isNotAdmin(request)) {
            // return ResultUtils.error(ErrorCodeEnum.USER_NOT_AUTH);
            throw new MyException(ErrorCodeEnum.USER_NOT_AUTH);
        }
        List<User> users = userService.searchUsers(userName, request);
        return ResultUtils.success(users);
    }

    /**
     * 用户封禁 / 解封（仅管理员）
     *
     * @param userManageRequest 用户管理请求体
     * @return 操作后用户状态
     */
    @PostMapping("/admin/ban")
    public BaseResponse<Integer> userOrBan(@RequestBody UserManageRequest userManageRequest,
                                           HttpServletRequest request) {
        // 鉴权，仅管理员可操作
        if (userManager.isNotAdmin(request)) {
            throw new MyException(ErrorCodeEnum.USER_NOT_AUTH);
        }
        Long id = userManager.getId(userManageRequest, request);
        if (id == null) {
            throw new MyException(ErrorCodeEnum.USER_LOSE_ACTION);
        }
        Integer result = userService.userOrBan(id, request);
        return ResultUtils.success(result);
    }

    /**
     * 用户删除（仅管理员，逻辑删除）
     *
     * @param userManageRequest 用户管理请求体
     * @return 删除成功与否
     */
    @PostMapping("/admin/delete")
    public BaseResponse<Boolean> userDelete(@RequestBody UserManageRequest userManageRequest,
                                            HttpServletRequest request) {
        // 鉴权，仅管理员可操作
        if (userManager.isNotAdmin(request)) {
            throw new MyException(ErrorCodeEnum.USER_NOT_AUTH);
        }
        Long id = userManager.getId(userManageRequest, request);
        if (id == null) {
            throw new MyException(ErrorCodeEnum.USER_LOSE_ACTION);
        }
        boolean result = userService.removeById(id);   // 无需业务层
        return ResultUtils.success(result);
    }

}