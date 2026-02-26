package com.blog.service;

import com.blog.dto.ArticleDTO;
import com.blog.vo.ArticleVO;

import java.util.List;

/**
 * 文章Service接口
 */
public interface ArticleService {

    /**
     * 发布文章
     */
    void publishArticle(ArticleDTO articleDTO, Long authorId);

    /**
     * 更新文章
     */
    void updateArticle(ArticleDTO articleDTO, Long authorId);

    /**
     * 删除文章
     */
    void deleteArticle(Long articleId, Long userId);

    /**
     * 根据ID获取文章详情
     */
    ArticleVO getArticleById(Long articleId);

    /**
     * 分页查询文章列表
     */
    List<ArticleVO> getArticleList(Integer pageNum, Integer pageSize, Long categoryId, Long authorId, Integer status);

    /**
     * 获取热门文章
     */
    List<ArticleVO> getHotArticles(Integer limit);

    /**
     * 根据标签ID查询文章列表
     */
    List<ArticleVO> getArticlesByTagId(Long tagId);

    /**
     * 增加文章浏览量
     */
    void incrementViewCount(Long articleId);

    /**
     * 点赞文章
     */
    void likeArticle(Long articleId, Long userId);

    /**
     * 取消点赞
     */
    void unlikeArticle(Long articleId, Long userId);
}