package com.blog.service;

import com.blog.entity.mysql.Category;
import com.blog.vo.CategoryVO;

import java.util.List;

/**
 * 分类Service接口
 */
public interface CategoryService {

    /**
     * 添加分类
     */
    void addCategory(Category category);

    /**
     * 更新分类
     */
    void updateCategory(Category category);

    /**
     * 删除分类
     */
    void deleteCategory(Long categoryId);

    /**
     * 根据ID获取分类
     */
    Category getCategoryById(Long categoryId);

    /**
     * 获取所有分类列表
     */
    List<CategoryVO> getCategoryList();
}