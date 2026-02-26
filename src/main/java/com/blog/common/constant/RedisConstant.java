package com.blog.common.constant;

/**
 * Redis key常量
 */
public class RedisConstant {

    /**
     * token前缀
     */
    public static final String TOKEN_PREFIX="token:";

    /**
     * 文章浏览量前缀
     */
    public static final String ARTICLE_VIEW_COUNT = "article:view:";

    /**
     * 文章详情缓存前缀
     */
    public static final String ARTICLE_DETAIL = "article:detail:";

    /**
     * 热门文章列表
     */
    public static final String HOT_ARTICLES = "article:hot";

    /**
     * 用户信息缓存前缀
     */
    public static final String USER_INFO = "user:info:";

    /**
     * 验证码前缀
     */
    public static final String VERIFY_CODE = "verify:code:";

    /**
     * 缓存过期时间（秒）
     */
    public static final long CACHE_EXPIRE_TIME = 3600; // 1小时

    /**
     * Token过期时间（秒）
     */
    public static final long TOKEN_EXPIRE_TIME = 86400; // 24小时
}
