package com.blog.vo;

import lombok.Data;

/**
 * 标签视图对象
 */
@Data
public class TagVO {

    /**
     * 标签ID
     */
    private Long id;

    /**
     * 标签名称
     */
    private String tagName;

    /**
     * 标签颜色
     */
    private String color;

    /**
     * 文章数量
     */
    private Long articleCount;
}