package com.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.entity.mysql.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户Mapper接口
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    /**
     * 根据用户名查询用户（包含角色信息）
     */
    User selectUserWithRoles(@Param("username") String username);

    /**
     * 分页查询用户列表
     */
    IPage<User> selectUserPage(Page<User> page,
                               @Param("username") String username,
                               @Param("email") String email,
                               @Param("status") Integer status);
}
