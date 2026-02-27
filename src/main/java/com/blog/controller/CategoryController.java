package com.blog.controller;

import com.blog.common.result.Result;
import com.blog.entity.mysql.Category;
import com.blog.service.CategoryService;
import com.blog.vo.CategoryVO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 分类控制器
 */
@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    /**
     * 添加分类（管理员）
     */
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Result<?> addCategory(@RequestBody Category category) {
        categoryService.addCategory(category);
        return Result.success("分类添加成功");
    }

    /**
     * 更新分类（管理员）
     */
    @PutMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Result<?> updateCategory(@RequestBody Category category) {
        categoryService.updateCategory(category);
        return Result.success("分类更新成功");
    }

    /**
     * 删除分类（管理员）
     */
    @DeleteMapping("/{categoryId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Result<?> deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return Result.success("分类删除成功");
    }

    /**
     * 获取所有分类列表（公开）
     */
    @GetMapping("/list")
    public Result<List<CategoryVO>> getAllCategories() {
        List<CategoryVO> categories = categoryService.getAllCategories();
        return Result.success(categories);
    }

    /**
     * 获取分类详情
     */
    @GetMapping("/{categoryId}")
    public Result<Category> getCategoryById(@PathVariable Long categoryId) {
        Category category = categoryService.getCategoryById(categoryId);
        return Result.success(category);
    }
}