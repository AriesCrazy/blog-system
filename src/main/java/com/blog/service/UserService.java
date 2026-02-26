package com.blog.service;

import com.blog.dto.LoginDTO;
import com.blog.dto.RegisterDTO;
import com.blog.entity.mysql.User;
import com.blog.vo.UserVO;

import java.util.Map;

/**
 * 用户Service接口
 */
public interface UserService {

    /**
     * 用户注册
     */
    void register(RegisterDTO registerDTO);

    /**
     * 用户登录
     */
    Map<String, Object> login(LoginDTO loginDTO);

    /**
     * 根据ID获取用户信息
     */
    UserVO getUserById(Long userId);

    /**
     * 根据用户名获取用户
     */
    User getUserByUsername(String username);

    /**
     * 更新用户信息
     */
    void updateUser(User user);

    /**
     * 修改密码
     */
    void updatePassword(Long userId, String oldPassword, String newPassword);

    /**
     * 更新用户头像
     */
    void updateAvatar(Long userId, String avatar);
}