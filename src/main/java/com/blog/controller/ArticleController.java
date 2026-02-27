package com.blog.controller;

import com.blog.common.result.Result;
import com.blog.dto.ArticleDTO;
import com.blog.security.SecurityUser;
import com.blog.service.ArticleService;
import com.blog.vo.ArticleVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 文章控制器
 */
@RestController
@RequestMapping("/article")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    /**
     * 发布文章
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public Result<?> publishArticle(
            @Valid @RequestBody ArticleDTO articleDTO,
            @AuthenticationPrincipal SecurityUser securityUser) {
        articleService.publishArticle(articleDTO, securityUser.getUserId());
        return Result.success("文章发布成功");
    }

    /**
     * 更新文章
     */
    @PutMapping
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public Result<?> updateArticle(
            @Valid @RequestBody ArticleDTO articleDTO,
            @AuthenticationPrincipal SecurityUser securityUser) {
        articleService.updateArticle(articleDTO, securityUser.getUserId());
        return Result.success("文章更新成功");
    }

    /**
     * 删除文章
     */
    @DeleteMapping("/{articleId}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public Result<?> deleteArticle(
            @PathVariable Long articleId,
            @AuthenticationPrincipal SecurityUser securityUser) {
        articleService.deleteArticle(articleId, securityUser.getUserId());
        return Result.success("文章删除成功");
    }

    /**
     * 获取文章详情
     */
    @GetMapping("/{articleId}")
    public Result<ArticleVO> getArticleById(@PathVariable Long articleId) {
        ArticleVO articleVO = articleService.getArticleById(articleId);
        // 增加浏览量
        articleService.incrementViewCount(articleId);
        return Result.success(articleVO);
    }

    /**
     * 分页查询文章列表
     */
    @GetMapping("/list")
    public Result<List<ArticleVO>> getArticleList(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Long authorId,
            @RequestParam(required = false) Integer status) {
        List<ArticleVO> articles = articleService.getArticleList(pageNum, pageSize, categoryId, authorId, status);
        return Result.success(articles);
    }

    /**
     * 获取热门文章
     */
    @GetMapping("/hot")
    public Result<List<ArticleVO>> getHotArticles(@RequestParam(defaultValue = "10") Integer limit) {
        List<ArticleVO> articles = articleService.getHotArticles(limit);
        return Result.success(articles);
    }

    /**
     * 根据标签查询文章
     */
    @GetMapping("/tag/{tagId}")
    public Result<List<ArticleVO>> getArticlesByTag(@PathVariable Long tagId) {
        List<ArticleVO> articles = articleService.getArticlesByTagId(tagId);
        return Result.success(articles);
    }

    /**
     * 点赞文章
     */
    @PostMapping("/{articleId}/like")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public Result<?> likeArticle(
            @PathVariable Long articleId,
            @AuthenticationPrincipal SecurityUser securityUser) {
        articleService.likeArticle(articleId, securityUser.getUserId());
        return Result.success("点赞成功");
    }

    /**
     * 取消点赞
     */
    @DeleteMapping("/{articleId}/like")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public Result<?> unlikeArticle(
            @PathVariable Long articleId,
            @AuthenticationPrincipal SecurityUser securityUser) {
        articleService.unlikeArticle(articleId, securityUser.getUserId());
        return Result.success("取消点赞成功");
    }
}