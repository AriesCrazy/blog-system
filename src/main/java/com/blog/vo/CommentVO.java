package com.blog.vo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 评论视图对象
 */
@Data
public class CommentVO {

    /**
     * 评论ID
     */
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
     * 父评论ID
     */
    private String parentId;

    /**
     * 回复的用户昵称
     */
    private String replyToNickname;

    /**
     * 点赞数
     */
    private Long likeCount;

    /**
     * 子评论列表
     */
    private List<CommentVO> children;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}