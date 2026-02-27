package com.blog.controller;

import com.blog.common.result.Result;
import com.blog.dto.CommentDTO;
import com.blog.security.SecurityUser;
import com.blog.service.CommentService;
import com.blog.vo.CommentVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 评论控制器
 */
@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    /**
     * 发表评论
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public Result<?> addComment(
            @Valid @RequestBody CommentDTO commentDTO,
            @AuthenticationPrincipal SecurityUser securityUser) {
        commentService.addComment(commentDTO, securityUser.getUserId());
        return Result.success("评论成功");
    }

    /**
     * 删除评论
     */
    @DeleteMapping("/{commentId}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public Result<?> deleteComment(
            @PathVariable String commentId,
            @AuthenticationPrincipal SecurityUser securityUser) {
        commentService.deleteComment(commentId, securityUser.getUserId());
        return Result.success("删除成功");
    }

    /**
     * 获取文章的评论列表
     */
    @GetMapping("/article/{articleId}")
    public Result<List<CommentVO>> getCommentsByArticleId(@PathVariable Long articleId) {
        List<CommentVO> comments = commentService.getCommentsByArticleId(articleId);
        return Result.success(comments);
    }

    /**
     * 获取用户的评论列表
     */
    @GetMapping("/user/{userId}")
    public Result<List<CommentVO>> getCommentsByUserId(@PathVariable Long userId) {
        List<CommentVO> comments = commentService.getCommentsByUserId(userId);
        return Result.success(comments);
    }

    /**
     * 点赞评论
     */
    @PostMapping("/{commentId}/like")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public Result<?> likeComment(
            @PathVariable String commentId,
            @AuthenticationPrincipal SecurityUser securityUser) {
        commentService.likeComment(commentId, securityUser.getUserId());
        return Result.success("点赞成功");
    }
}