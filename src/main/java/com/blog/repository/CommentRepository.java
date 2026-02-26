package com.blog.repository;

import com.blog.entity.mongo.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 评论Repository
 */
@Repository
public interface CommentRepository extends MongoRepository<Comment, String> {

    /**
     * 根据文章ID查询评论列表
     */
    List<Comment> findByArticleIdAndStatusOrderByCreateTimeDesc(Long articleId, Integer status);

    /**
     * 根据文章ID和父评论ID查询子评论
     */
    List<Comment> findByArticleIdAndParentIdOrderByCreateTimeAsc(Long articleId, String parentId);

    /**
     * 根据用户ID查询评论列表
     */
    List<Comment> findByUserIdOrderByCreateTimeDesc(Long userId);

    /**
     * 统计文章的评论数
     */
    long countByArticleIdAndStatus(Long articleId, Integer status);

    /**
     * 根据文章ID删除所有评论
     */
    void deleteByArticleId(Long articleId);
}