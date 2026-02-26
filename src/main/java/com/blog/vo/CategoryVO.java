package com.blog.vo;

import lombok.Data;

/**
 * 分类视图对象
 */
@Data
public class CategoryVO {

    /**
     * 分类ID
     */
    private Long id;

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 分类描述
     */
    private String description;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 文章数量
     */
    private Long articleCount;
}