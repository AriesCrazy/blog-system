package com.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.blog.common.exception.BusinessException;
import com.blog.entity.mysql.Article;
import com.blog.entity.mysql.Category;
import com.blog.mapper.ArticleMapper;
import com.blog.mapper.CategoryMapper;
import com.blog.service.CategoryService;
import com.blog.vo.CategoryVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 分类Service实现类
 */
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;
    private final ArticleMapper articleMapper;

    @Override
    public void addCategory(Category category) {
        // 检查分类名是否已存在
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Category::getCategoryName, category.getCategoryName());
        if (categoryMapper.selectCount(wrapper) > 0) {
            throw new BusinessException("分类名称已存在");
        }

        categoryMapper.insert(category);
    }

    @Override
    public void updateCategory(Category category) {
        Category existCategory = categoryMapper.selectById(category.getId());
        if (existCategory == null) {
            throw new BusinessException("分类不存在");
        }

        categoryMapper.updateById(category);
    }

    @Override
    public void deleteCategory(Long categoryId) {
        // 检查是否有文章使用该分类
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Article::getCategoryId, categoryId);
        long count = articleMapper.selectCount(wrapper);

        if (count > 0) {
            throw new BusinessException("该分类下有文章，无法删除");
        }

        categoryMapper.deleteById(categoryId);
    }

    @Override
    public Category getCategoryById(Long categoryId) {
        Category category = categoryMapper.selectById(categoryId);
        if (category == null) {
            throw new BusinessException("分类不存在");
        }
        return category;
    }

    @Override
    public List<CategoryVO> getAllCategories() {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(Category::getSort);
        List<Category> categories = categoryMapper.selectList(wrapper);

        return categories.stream().map(category -> {
            CategoryVO categoryVO = new CategoryVO();
            BeanUtils.copyProperties(category, categoryVO);

            // 统计该分类下的文章数量
            LambdaQueryWrapper<Article> articleWrapper = new LambdaQueryWrapper<>();
            articleWrapper.eq(Article::getCategoryId, category.getId());
            articleWrapper.eq(Article::getStatus, 1); // 只统计已发布的
            long articleCount = articleMapper.selectCount(articleWrapper);
            categoryVO.setArticleCount(articleCount);

            return categoryVO;
        }).collect(Collectors.toList());
    }
}