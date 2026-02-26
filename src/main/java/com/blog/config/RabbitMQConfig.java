package com.blog.config;

import com.blog.common.constant.MQConstant;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ配置类
 */
@Configuration
public class RabbitMQConfig {

    /**
     * 声明交换机
     */
    @Bean
    public DirectExchange blogExchange() {
        return new DirectExchange(MQConstant.BLOG_EXCHANGE, true, false);
    }

    /**
     * 评论通知队列
     */
    @Bean
    public Queue commentQueue() {
        return new Queue(MQConstant.COMMENT_QUEUE, true);
    }

    /**
     * 点赞通知队列
     */
    @Bean
    public Queue likeQueue() {
        return new Queue(MQConstant.LIKE_QUEUE, true);
    }

    /**
     * 浏览量更新队列
     */
    @Bean
    public Queue viewQueue() {
        return new Queue(MQConstant.VIEW_QUEUE, true);
    }

    /**
     * 系统通知队列
     */
    @Bean
    public Queue systemQueue() {
        return new Queue(MQConstant.SYSTEM_QUEUE, true);
    }

    /**
     * 绑定评论队列到交换机
     */
    @Bean
    public Binding commentBinding() {
        return BindingBuilder
                .bind(commentQueue())
                .to(blogExchange())
                .with(MQConstant.COMMENT_ROUTING_KEY);
    }

    /**
     * 绑定点赞队列到交换机
     */
    @Bean
    public Binding likeBinding() {
        return BindingBuilder
                .bind(likeQueue())
                .to(blogExchange())
                .with(MQConstant.LIKE_ROUTING_KEY);
    }

    /**
     * 绑定浏览量队列到交换机
     */
    @Bean
    public Binding viewBinding() {
        return BindingBuilder
                .bind(viewQueue())
                .to(blogExchange())
                .with(MQConstant.VIEW_ROUTING_KEY);
    }

    /**
     * 绑定系统通知队列到交换机
     */
    @Bean
    public Binding systemBinding() {
        return BindingBuilder
                .bind(systemQueue())
                .to(blogExchange())
                .with(MQConstant.SYSTEM_ROUTING_KEY);
    }
}