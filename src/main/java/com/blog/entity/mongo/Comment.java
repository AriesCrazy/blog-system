package com.blog.entity.mongo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 评论文档实体类
 */
@Data
@Document(collection = "comments")
public class Comment {

    /**
     * 评论ID
     */
    @Id
    private String id;

    /**
     * 文章ID
     */
    private Long articleId;

    /**
     * 评论用户ID
     */
    private Long userId;

    /**
     * 评论用户昵称
     */
    private String nickname;

    /**
     * 评论用户头像
     */
    private String avatar;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 父评论ID（顶级评论为null）
     */
    private String parentId;

    /**
     * 根评论ID（用于楼层显示）
     */
    private String rootId;

    /**
     * 回复的评论ID
     */
    private String replyToId;

    /**
     * 回复的用户ID
     */
    private Long replyToUserId;

    /**
     * 回复的用户昵称
     */
    private String replyToNickname;

    /**
     * 点赞数
     */
    private Long likeCount;

    /**
     * 评论状态：0-待审核，1-已通过，2-已删除
     */
    private Integer status;

    /**
     * IP地址
     */
    private String ipAddress;

    /**
     * 用户代理
     */
    private String userAgent;

    /**
     * 子评论列表（用于前端展示）
     */
    private List<Comment> children;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
