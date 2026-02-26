package com.blog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 评论请求DTO
 */
@Data
public class CommentDTO {

    /**
     * 文章ID
     */
    @NotNull(message = "文章ID不能为空")
    private Long articleId;

    /**
     * 评论内容
     */
    @NotBlank(message = "评论内容不能为空")
    private String content;

    /**
     * 父评论ID（顶级评论为null）
     */
    private String parentId;

    /**
     * 根评论ID
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
}