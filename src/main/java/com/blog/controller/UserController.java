package com.blog.controller;

import com.blog.common.result.Result;
import com.blog.security.SecurityUser;
import com.blog.service.UserService;
import com.blog.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/**
 * 用户控制器
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 获取当前登录用户信息
     */
    @GetMapping("/info")
    public Result<UserVO> getUserInfo(@AuthenticationPrincipal SecurityUser securityUser) {
        UserVO userVO = userService.getUserById(securityUser.getUserId());
        return Result.success(userVO);
    }

    /**
     * 根据ID获取用户信息
     */
    @GetMapping("/{userId}")
    public Result<UserVO> getUserById(@PathVariable Long userId) {
        UserVO userVO = userService.getUserById(userId);
        return Result.success(userVO);
    }

    /**
     * 修改密码
     */
    @PutMapping("/password")
    public Result<?> updatePassword(
            @AuthenticationPrincipal SecurityUser securityUser,
            @RequestParam String oldPassword,
            @RequestParam String newPassword) {
        userService.updatePassword(securityUser.getUserId(), oldPassword, newPassword);
        return Result.success("密码修改成功");
    }

    /**
     * 更新头像
     */
    @PutMapping("/avatar")
    public Result<?> updateAvatar(
            @AuthenticationPrincipal SecurityUser securityUser,
            @RequestParam String avatar) {
        userService.updateAvatar(securityUser.getUserId(), avatar);
        return Result.success("头像更新成功");
    }
}