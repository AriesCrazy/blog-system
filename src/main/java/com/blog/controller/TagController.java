package com.blog.controller;

import com.blog.common.result.Result;
import com.blog.entity.mysql.Tag;
import com.blog.service.TagService;
import com.blog.vo.TagVO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 标签控制器
 */
@RestController
@RequestMapping("/tag")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    /**
     * 添加标签（管理员）
     */
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Result<?> addTag(@RequestBody Tag tag) {
        tagService.addTag(tag);
        return Result.success("标签添加成功");
    }

    /**
     * 更新标签（管理员）
     */
    @PutMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Result<?> updateTag(@RequestBody Tag tag) {
        tagService.updateTag(tag);
        return Result.success("标签更新成功");
    }

    /**
     * 删除标签（管理员）
     */
    @DeleteMapping("/{tagId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Result<?> deleteTag(@PathVariable Long tagId) {
        tagService.deleteTag(tagId);
        return Result.success("标签删除成功");
    }

    /**
     * 获取所有标签列表（公开）
     */
    @GetMapping("/list")
    public Result<List<TagVO>> getAllTags() {
        List<TagVO> tags = tagService.getAllTags();
        return Result.success(tags);
    }

    /**
     * 获取标签详情
     */
    @GetMapping("/{tagId}")
    public Result<Tag> getTagById(@PathVariable Long tagId) {
        Tag tag = tagService.getTagById(tagId);
        return Result.success(tag);
    }
}