package com.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.entity.mysql.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 文章Mapper接口
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

    /**
     * 增加文章浏览量
     */
    @Update("UPDATE article SET view_count = view_count + 1 WHERE id = #{articleId}")
    int incrementViewCount(@Param("articleId") Long articleId);

    /**
     * 增加文章点赞数
     */
    @Update("UPDATE article SET like_count = like_count + 1 WHERE id = #{articleId}")
    int incrementLikeCount(@Param("articleId") Long articleId);

    /**
     * 减少文章点赞数
     */
    @Update("UPDATE article SET like_count = like_count - 1 WHERE id = #{articleId} AND like_count > 0")
    int decrementLikeCount(@Param("articleId") Long articleId);

    /**
     * 增加文章评论数
     */
    @Update("UPDATE article SET comment_count = comment_count + 1 WHERE id = #{articleId}")
    int incrementCommentCount(@Param("articleId") Long articleId);

    /**
     * 减少文章评论数
     */
    @Update("UPDATE article SET comment_count = comment_count - 1 WHERE id = #{articleId} AND comment_count > 0")
    int decrementCommentCount(@Param("articleId") Long articleId);

    /**
     * 查询文章详情（包含作者、分类信息）
     */
    Article selectArticleDetail(@Param("articleId") Long articleId);

    /**
     * 分页查询文章列表
     */
    IPage<Article> selectArticlePage(Page<Article> page,
                                     @Param("title") String title,
                                     @Param("categoryId") Long categoryId,
                                     @Param("authorId") Long authorId,
                                     @Param("status") Integer status);

    /**
     * 查询热门文章
     */
    List<Article> selectHotArticles(@Param("limit") Integer limit);

    /**
     * 根据标签ID查询文章列表
     */
    List<Article> selectArticlesByTagId(@Param("tagId") Long tagId);
}
