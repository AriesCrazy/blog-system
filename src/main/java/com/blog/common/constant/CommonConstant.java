package com.blog.common.constant;

/**
 * 通用常量
 */
public class CommonConstant {
    /**
     * UTF-8编码
     */
    public static final String UTF8 = "UTF-8";

    /**
     * 成功标记
     */
    public static final Integer SUCCESS = 200;

    /**
     * 失败标记
     */
    public static final Integer FAIL = 400;

    /**
     * 状态：正常
     */
    public static final Integer STATUS_NORMAL = 1;

    /**
     * 状态：禁用
     */
    public static final Integer STATUS_DISABLE = 0;

    /**
     * 删除标记：未删除
     */
    public static final Integer NOT_DELETED = 0;

    /**
     * 删除标记：已删除
     */
    public static final Integer DELETED = 1;

    /**
     * 文章状态：草稿
     */
    public static final Integer ARTICLE_STATUS_DRAFT = 0;

    /**
     * 文章状态：已发布
     */
    public static final Integer ARTICLE_STATUS_PUBLISHED = 1;

    /**
     * 文章状态：已下架
     */
    public static final Integer ARTICLE_STATUS_OFFLINE = 2;

    /**
     * 是否置顶：否
     */
    public static final Integer NOT_TOP = 0;

    /**
     * 是否置顶：是
     */
    public static final Integer IS_TOP = 1;
}
