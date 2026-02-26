package com.blog.common.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 返回状态码枚举
 */
@Getter
@AllArgsConstructor
public enum ResultCode {

    // 成功
    SUCCESS(200, "操作成功"),

    // 失败
    FAIL(400, "操作失败"),

    // 未认证
    UNAUTHORIZED(401, "未认证，请先登录"),

    // 无权限
    FORBIDDEN(403, "无权限访问"),

    // 未找到
    NOT_FOUND(404, "资源不存在"),

    // 服务器错误
    INTERNAL_SERVER_ERROR(500, "服务器内部错误"),

    // 参数错误
    PARAM_ERROR(400, "参数错误"),

    // 用户相关
    USER_NOT_EXIST(1001, "用户不存在"),
    USER_ALREADY_EXIST(1002, "用户已存在"),
    USER_PASSWORD_ERROR(1003, "用户名或密码错误"),
    USER_DISABLED(1004, "用户已被禁用"),

    // Token相关
    TOKEN_INVALID(2001, "Token无效"),
    TOKEN_EXPIRED(2002, "Token已过期"),

    // 文章相关
    ARTICLE_NOT_EXIST(3001, "文章不存在"),

    // 评论相关
    COMMENT_NOT_EXIST(4001, "评论不存在");

    private final Integer code;
    private final String message;
}
