package com.blog.entity.mongo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * 操作日志文档实体类
 */
@Data
@Document(collection = "operation_logs")
public class OperationLog {

    /**
     * 日志ID
     */
    @Id
    private String id;

    /**
     * 操作用户ID
     */
    private Long userId;

    /**
     * 操作用户名
     */
    private String username;

    /**
     * 操作模块
     */
    private String module;

    /**
     * 操作类型：CREATE, UPDATE, DELETE, QUERY
     */
    private String operationType;

    /**
     * 操作描述
     */
    private String description;

    /**
     * 请求方法
     */
    private String method;

    /**
     * 请求参数
     */
    private String params;

    /**
     * 返回结果
     */
    private String result;

    /**
     * IP地址
     */
    private String ipAddress;

    /**
     * 请求URI
     */
    private String requestUri;

    /**
     * 执行时间（毫秒）
     */
    private Long executionTime;

    /**
     * 是否成功：0-失败，1-成功
     */
    private Integer success;

    /**
     * 错误信息
     */
    private String errorMsg;

    /**
     * 浏览器
     */
    private String browser;

    /**
     * 操作系统
     */
    private String os;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
