package com.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.blog.common.exception.BusinessException;
import com.blog.entity.mysql.Tag;
import com.blog.mapper.ArticleTagMapper;
import com.blog.mapper.TagMapper;
import com.blog.service.TagService;
import com.blog.vo.TagVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 标签Service实现类
 */
@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagMapper tagMapper;
    private final ArticleTagMapper articleTagMapper;

    @Override
    public void addTag(Tag tag) {
        // 检查标签名是否已存在
        LambdaQueryWrapper<Tag> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Tag::getTagName, tag.getTagName());
        if (tagMapper.selectCount(wrapper) > 0) {
            throw new BusinessException("标签名已存在");
        }

        tagMapper.insert(tag);
    }

    @Override
    public void updateTag(Tag tag) {
        Tag existTag = tagMapper.selectById(tag.getId());
        if (existTag == null) {
            throw new BusinessException("标签不存在");
        }

        tagMapper.updateById(tag);
    }

    @Override
    public void deleteTag(Long tagId) {
        // 检查是否有文章使用该标签
        List<Long> articleIds = articleTagMapper.selectArticleIdsByTagId(tagId);
        if (!articleIds.isEmpty()) {
            throw new BusinessException("该标签下有文章，无法删除");
        }

        tagMapper.deleteById(tagId);
    }

    @Override
    public Tag getTagById(Long tagId) {
        Tag tag = tagMapper.selectById(tagId);
        if (tag == null) {
            throw new BusinessException("标签不存在");
        }
        return tag;
    }

    @Override
    public List<TagVO> getTagList() {
        List<Tag> tags = tagMapper.selectList(null);

        return tags.stream().map(tag -> {
            TagVO tagVO = new TagVO();
            BeanUtils.copyProperties(tag, tagVO);

            // 统计使用该标签的文章数
            List<Long> articleIds = articleTagMapper.selectArticleIdsByTagId(tag.getId());
            tagVO.setArticleCount((long) articleIds.size());

            return tagVO;
        }).collect(Collectors.toList());
    }

    @Override
    public List<TagVO> getTagsByArticleId(Long articleId) {
        List<Tag> tags = articleTagMapper.selectTagsByArticleId(articleId);

        return tags.stream().map(tag -> {
            TagVO tagVO = new TagVO();
            BeanUtils.copyProperties(tag, tagVO);
            return tagVO;
        }).collect(Collectors.toList());
    }
}