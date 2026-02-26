package com.blog.common.constant;

/**
 * RabbitMQ常量
 */
public class MQConstant {
    /**
     * 交换机名称
     */
    public static final String BLOG_EXCHANGE = "blog.exchange";

    /**
     * 评论通知队列
     */
    public static final String COMMENT_QUEUE = "blog.comment.queue";
    public static final String COMMENT_ROUTING_KEY = "blog.comment";

    /**
     * 点赞通知队列
     */
    public static final String LIKE_QUEUE = "blog.like.queue";
    public static final String LIKE_ROUTING_KEY = "blog.like";

    /**
     * 文章浏览量更新队列
     */
    public static final String VIEW_QUEUE = "blog.view.queue";
    public static final String VIEW_ROUTING_KEY = "blog.view";

    /**
     * 系统通知队列
     */
    public static final String SYSTEM_QUEUE = "blog.system.queue";
    public static final String SYSTEM_ROUTING_KEY = "blog.system";
}
