package com.blog.service;

import com.blog.entity.mysql.Tag;
import com.blog.vo.TagVO;

import java.util.List;

/**
 * 标签Service接口
 */
public interface TagService {

    /**
     * 添加标签
     */
    void addTag(Tag tag);

    /**
     * 更新标签
     */
    void updateTag(Tag tag);

    /**
     * 删除标签
     */
    void deleteTag(Long tagId);

    /**
     * 根据ID获取标签
     */
    Tag getTagById(Long tagId);

    /**
     * 获取所有标签列表
     */
    List<TagVO> getTagList();

    /**
     * 根据文章ID获取标签列表
     */
    List<TagVO> getTagsByArticleId(Long articleId);
}