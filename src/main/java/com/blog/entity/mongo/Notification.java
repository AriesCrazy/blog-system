package com.blog.entity.mongo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * 通知文档实体类
 */
@Data
@Document(collection = "notifications")
public class Notification {

    /**
     * 通知ID
     */
    @Id
    private String id;

    /**
     * 接收通知的用户ID
     */
    private Long userId;

    /**
     * 通知类型：1-评论通知，2-点赞通知，3-系统通知
     */
    private Integer type;

    /**
     * 通知标题
     */
    private String title;

    /**
     * 通知内容
     */
    private String content;

    /**
     * 关联的文章ID（如果是文章相关通知）
     */
    private Long articleId;

    /**
     * 关联的评论ID（如果是评论相关通知）
     */
    private String commentId;

    /**
     * 触发通知的用户ID
     */
    private Long fromUserId;

    /**
     * 触发通知的用户昵称
     */
    private String fromNickname;

    /**
     * 触发通知的用户头像
     */
    private String fromAvatar;

    /**
     * 是否已读：0-未读，1-已读
     */
    private Integer isRead;

    /**
     * 跳转链接
     */
    private String link;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 阅读时间
     */
    private LocalDateTime readTime;
}
