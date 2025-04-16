package com.yupi.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.usercenter.constant.ErrorConstant;
import static com.yupi.usercenter.enums.UserEnum.*;
import com.yupi.usercenter.mapper.UserMapper;
import com.yupi.usercenter.model.User;
import com.yupi.usercenter.service.UserService;
import com.yupi.usercenter.utils.AESUtils;
import com.yupi.usercenter.utils.SpecialCharValidator;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 业务实现
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;

    /**
     * 用户注册
     *
     * @param userName 账户
     * @param userPassword 密码
     * @param checkPassword 确认密码
     * @return 新账户id
     */
    @Override
    public Long userRegister(String userName, String userPassword, String checkPassword) throws Exception {
        // 1. 信息校验
        // - 字段不能为空
        if (StringUtils.isAnyBlank(userName, userPassword, checkPassword)) {
            log.info(ErrorConstant.USER_HAVE_NULL_CHAR_MESSAGE);
            return null;
        }
        // - 长度限制
        if (userName.length() < 3 || userPassword.length() < 8 || checkPassword.length() < 8) {
            log.info("——！账户名称长度或密码长度太短！——");
            return null;
        }
        // - 账户名称不能包含特殊字符
        if (SpecialCharValidator.doValidate(userName)) {
            log.info(ErrorConstant.USER_HAVE_SPECIAL_CHAR_MESSAGE);
            return null;
        }
        // 密码和确认密码必须一致
        if (!userPassword.equals(checkPassword)) {
            log.info("——！两次密码输入不一致！——");
            return null;
        }
        // - 账户查重
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userName", userName);
        long count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            log.info(ErrorConstant.USER_ALREADY_EXIST_MESSAGE);
            return null;
        }

        // 2. 密码加密
        String encryptedPassword = AESUtils.doEncrypt(userPassword);
        if (encryptedPassword == null) {
            log.info(ErrorConstant.USER_LOSE_ACTION_MESSAGE);
            return null;
        }
        log.info("密码已加密>>>");

        // 3. 向数据库插入数据
        User user = new User();
        user.setUserName(userName);
        user.setUserPassword(encryptedPassword);
        boolean saveResult = this.save(user);
        if (!saveResult) {
            log.info(ErrorConstant.USER_LOSE_ACTION_MESSAGE);
            return null;
        }
        log.info("数据插入成功，注册完成>>>");

        // 4. 返回新账户id
        return user.getId();
    }

    /**
     * 用户登录
     *
     * @param userName 账号
     * @param userPassword 密码
     * @return 脱敏后的用户信息
     */
    @Override
    public User userLogin(String userName, String userPassword, HttpServletRequest request) throws Exception {
        // 1. 信息校验
        // - 字段不能为空
        if (StringUtils.isAnyBlank(userName, userPassword)) {
            log.info(ErrorConstant.USER_HAVE_NULL_CHAR_MESSAGE);
            return null;
        }
        // - 账户名称不能包含特殊字符
        if (SpecialCharValidator.doValidate(userName)) {
            log.info(ErrorConstant.USER_HAVE_SPECIAL_CHAR_MESSAGE);
            return null;
        }

        // 2. 密码加密
        String encryptedPassword = AESUtils.doEncrypt(userPassword);

        // 3. 查询用户是否存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userName", userName);
        queryWrapper.eq("userPassword", encryptedPassword);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            log.info(ErrorConstant.USER_NOT_EXIST_MESSAGE);
            return null;
        }

        // 4. 信息脱敏
        User safeUser = getSafeUser(user);

        // 5. 记录用户登录态（已脱敏）
        request.getSession().setAttribute(USER_LOGIN_STATE.getKey(), safeUser);
        return safeUser;
    }

    /**
     * 退出登录
     *
     * @param request http请求
     * @return 状态码
     */
    @Override
    public int userLogout(HttpServletRequest request) {
        // 移除登录态
        request.getSession().removeAttribute(USER_LOGIN_STATE.getKey());
        return 1;
    }

    /**
     * 用户查询（仅管理员）
     *
     * @param userName 用户名
     * @return 用户列表
     */
    @Override
    public List<User> searchUsers(String userName, HttpServletRequest request) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(userName)) {
            queryWrapper.like("userName", userName);   // 默认模糊查询
        }
        List<User> userList = this.list(queryWrapper);
        return userList.stream().map(user -> {
            user.setUserPassword("想要密码？就不告诉你！");   // 密码保护
            return user;
        }).collect(Collectors.toList());
    }

    /**
     * 用户封禁 / 解封（仅管理员）
     *
     * @param id 待封禁/解封用户id
     * @return 状态码
     */
    @Override
    public int userOrBan(@RequestBody Long id, HttpServletRequest request) {
        // 1. 查询用户是否存在
        User user = userMapper.selectById(id);
        if (user == null) {
            log.info(ErrorConstant.USER_NOT_EXIST_MESSAGE);
            return -1;
        }

        // 2. 切换用户状态
        int currentStatus = user.getUserStatus();
        int newStatus = (currentStatus == 0) ? 1 : 0;
        user.setUserStatus(newStatus);

        // 3. 更新数据库
        int updateResult = userMapper.updateById(user);
        if (updateResult <= 0) {
            log.info(ErrorConstant.USER_LOSE_ACTION_MESSAGE);
            return -1;
        }

        // 4. 返回操作结果
        if (newStatus != 0) {
            System.out.println("用户已封禁");
        } else {
            System.out.println("用户已解封");
        }
        return 0;
    }

    /**
     * 用户注销
     * 从数据库中彻底删除账户
     *
     * @param id 待注销用户id
     * @return 状态码
     */
    @Override
    public int userLogoff(Long id, HttpServletRequest request) {
        // 1. 查询用户是否存在
        User user = userMapper.selectById(id);
        if (user == null) {
            log.info(ErrorConstant.USER_NOT_EXIST_MESSAGE);
            return -1;
        }
        // 2. 从数据库中删除账户
        // 3. 返回状态码
        return 0;
    }

    /**
     * 用户脱敏
     * 使用掩码隐藏敏感信息，保障传输层安全
     *
     * @param user 脱敏前的账户
     * @return 脱敏后的账户信息
     */
    private static User getSafeUser(User user) {
        User safeUser = new User();
        safeUser.setUserName(user.getUserName());
        safeUser.setId(user.getId());
        safeUser.setRole(user.getRole());
        safeUser.setAvatarUrl(user.getAvatarUrl());
        safeUser.setGender(user.getGender());
        safeUser.setUserPassword("****************");
        safeUser.setAge(user.getAge());
        safeUser.setPhone("***********");
        safeUser.setEmail(user.getEmail());
        safeUser.setUserStatus(user.getUserStatus());
        safeUser.setCreateTime(user.getCreateTime());
        safeUser.setUpdateTime(new Date());
        return safeUser;
    }

}