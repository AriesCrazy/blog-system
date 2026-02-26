package com.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.blog.common.exception.BusinessException;
import com.blog.common.result.ResultCode;
import com.blog.dto.ArticleDTO;
import com.blog.entity.mysql.Article;
import com.blog.entity.mysql.ArticleTag;
import com.blog.entity.mysql.Tag;
import com.blog.mapper.ArticleMapper;
import com.blog.mapper.ArticleTagMapper;
import com.blog.mapper.CategoryMapper;
import com.blog.mapper.TagMapper;
import com.blog.service.ArticleService;
import com.blog.vo.ArticleVO;
import com.blog.vo.TagVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 文章Service实现类
 */
@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleMapper articleMapper;
    private final ArticleTagMapper articleTagMapper;
    private final CategoryMapper categoryMapper;
    private final TagMapper tagMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void publishArticle(ArticleDTO articleDTO, Long authorId) {
        // 创建文章
        Article article = new Article();
        BeanUtils.copyProperties(articleDTO, article);
        article.setAuthorId(authorId);
        article.setViewCount(0L);
        article.setLikeCount(0L);
        article.setCommentCount(0L);

        // 如果状态是已发布，设置发布时间
        if (articleDTO.getStatus() != null && articleDTO.getStatus() == 1) {
            article.setPublishTime(LocalDateTime.now());
        }

        articleMapper.insert(article);

        // 保存文章标签关联
        if (articleDTO.getTagIds() != null && !articleDTO.getTagIds().isEmpty()) {
            saveArticleTags(article.getId(), articleDTO.getTagIds());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateArticle(ArticleDTO articleDTO, Long authorId) {
        Article article = articleMapper.selectById(articleDTO.getId());
        if (article == null) {
            throw new BusinessException(ResultCode.ARTICLE_NOT_EXIST);
        }

        // 验证是否是作者本人
        if (!article.getAuthorId().equals(authorId)) {
            throw new BusinessException(ResultCode.FORBIDDEN);
        }

        // 更新文章
        BeanUtils.copyProperties(articleDTO, article);

        // 如果从草稿变为发布，设置发布时间
        if (articleDTO.getStatus() != null && articleDTO.getStatus() == 1 && article.getPublishTime() == null) {
            article.setPublishTime(LocalDateTime.now());
        }

        articleMapper.updateById(article);

        // 更新文章标签关联
        if (articleDTO.getTagIds() != null) {
            articleTagMapper.deleteByArticleId(article.getId());
            if (!articleDTO.getTagIds().isEmpty()) {
                saveArticleTags(article.getId(), articleDTO.getTagIds());
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteArticle(Long articleId, Long userId) {
        Article article = articleMapper.selectById(articleId);
        if (article == null) {
            throw new BusinessException(ResultCode.ARTICLE_NOT_EXIST);
        }

        // 验证是否是作者本人
        if (!article.getAuthorId().equals(userId)) {
            throw new BusinessException(ResultCode.FORBIDDEN);
        }

        // 逻辑删除文章
        articleMapper.deleteById(articleId);

        // 删除文章标签关联
        articleTagMapper.deleteByArticleId(articleId);
    }

    @Override
    public ArticleVO getArticleById(Long articleId) {
        Article article = articleMapper.selectArticleDetail(articleId);
        if (article == null) {
            throw new BusinessException(ResultCode.ARTICLE_NOT_EXIST);
        }

        ArticleVO articleVO = convertToVO(article);

        // 查询文章标签
        List<Tag> tags = articleTagMapper.selectTagsByArticleId(articleId);
        List<TagVO> tagVOS = tags.stream().map(tag -> {
            TagVO tagVO = new TagVO();
            BeanUtils.copyProperties(tag, tagVO);
            return tagVO;
        }).collect(Collectors.toList());
        articleVO.setTags(tagVOS);

        return articleVO;
    }

    @Override
    public List<ArticleVO> getArticleList(Integer pageNum, Integer pageSize, Long categoryId, Long authorId, Integer status) {
        // 注意：这里暂时不使用分页插件，直接查询
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();

        if (categoryId != null) {
            wrapper.eq(Article::getCategoryId, categoryId);
        }
        if (authorId != null) {
            wrapper.eq(Article::getAuthorId, authorId);
        }
        if (status != null) {
            wrapper.eq(Article::getStatus, status);
        }

        wrapper.orderByDesc(Article::getIsTop, Article::getPublishTime);

        List<Article> articles = articleMapper.selectList(wrapper);
        return articles.stream().map(this::convertToVO).collect(Collectors.toList());
    }

    @Override
    public List<ArticleVO> getHotArticles(Integer limit) {
        List<Article> articles = articleMapper.selectHotArticles(limit);
        return articles.stream().map(this::convertToVO).collect(Collectors.toList());
    }

    @Override
    public List<ArticleVO> getArticlesByTagId(Long tagId) {
        List<Article> articles = articleMapper.selectArticlesByTagId(tagId);
        return articles.stream().map(this::convertToVO).collect(Collectors.toList());
    }

    @Override
    public void incrementViewCount(Long articleId) {
        articleMapper.incrementViewCount(articleId);
    }

    @Override
    public void likeArticle(Long articleId, Long userId) {
        // TODO: 后续可以记录点赞关系到Redis，避免重复点赞
        articleMapper.incrementLikeCount(articleId);
    }

    @Override
    public void unlikeArticle(Long articleId, Long userId) {
        articleMapper.decrementLikeCount(articleId);
    }

    /**
     * 保存文章标签关联
     */
    private void saveArticleTags(Long articleId, List<Long> tagIds) {
        List<ArticleTag> articleTags = new ArrayList<>();
        for (Long tagId : tagIds) {
            ArticleTag articleTag = new ArticleTag();
            articleTag.setArticleId(articleId);
            articleTag.setTagId(tagId);
            articleTags.add(articleTag);
        }
        articleTagMapper.batchInsert(articleTags);
    }

    /**
     * 转换为VO
     */
    private ArticleVO convertToVO(Article article) {
        ArticleVO articleVO = new ArticleVO();
        BeanUtils.copyProperties(article, articleVO);
        return articleVO;
    }
}