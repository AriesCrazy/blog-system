package com.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.entity.mysql.ArticleTag;
import com.blog.entity.mysql.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 文章标签关联Mapper接口
 */
@Mapper
public interface ArticleTagMapper extends BaseMapper<ArticleTag> {

    /**
     * 根据文章ID查询标签列表
     */
    @Select("SELECT t.* FROM tag t " +
            "INNER JOIN article_tag at ON t.id = at.tag_id " +
            "WHERE at.article_id = #{articleId} AND t.deleted = 0")
    List<Tag> selectTagsByArticleId(@Param("articleId") Long articleId);

    /**
     * 根据标签ID查询文章ID列表
     */
    @Select("SELECT article_id FROM article_tag WHERE tag_id = #{tagId}")
    List<Long> selectArticleIdsByTagId(@Param("tagId") Long tagId);
}
