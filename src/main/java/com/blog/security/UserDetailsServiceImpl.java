package com.blog.security;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.blog.common.result.ResultCode;
import com.blog.common.exception.BusinessException;
import com.blog.entity.mysql.Role;
import com.blog.entity.mysql.User;
import com.blog.mapper.UserMapper;
import com.blog.mapper.UserRoleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * UserDetailsService实现类
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserMapper userMapper;
    private final UserRoleMapper userRoleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 查询用户
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, username);
        User user = userMapper.selectOne(wrapper);

        if (user == null) {
            throw new BusinessException(ResultCode.USER_NOT_EXIST);
        }

        // 查询用户角色
        List<Role> roles = userRoleMapper.selectRolesByUserId(user.getId());
        List<String> roleKeys = roles.stream()
                .map(Role::getRoleKey)
                .collect(Collectors.toList());

        return new SecurityUser(user, roleKeys);
    }
}
