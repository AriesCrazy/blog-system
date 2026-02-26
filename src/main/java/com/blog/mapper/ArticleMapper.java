package com.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.entity.mysql.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

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
}
