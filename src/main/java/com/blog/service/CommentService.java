package com.blog.service;

import com.blog.dto.CommentDTO;
import com.blog.vo.CommentVO;

import java.util.List;

/**
 * 评论Service接口
 */
public interface CommentService {

    /**
     * 发表评论
     */
    void addComment(CommentDTO commentDTO, Long userId);

    /**
     * 删除评论
     */
    void deleteComment(String commentId, Long userId);

    /**
     * 获取文章的评论列表（树形结构）
     */
    List<CommentVO> getCommentsByArticleId(Long articleId);

    /**
     * 获取用户的评论列表
     */
    List<CommentVO> getCommentsByUserId(Long userId);

    /**
     * 点赞评论
     */
    void likeComment(String commentId, Long userId);

    /**
     * 获取评论详情
     */
    CommentVO getCommentById(String commentId);
}