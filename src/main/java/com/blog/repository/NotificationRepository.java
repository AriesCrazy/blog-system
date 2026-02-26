package com.blog.repository;

import com.blog.entity.mongo.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 通知Repository
 */
@Repository
public interface NotificationRepository extends MongoRepository<Notification, String> {

    /**
     * 根据用户ID查询通知列表（按时间倒序）
     */
    List<Notification> findByUserIdOrderByCreateTimeDesc(Long userId);

    /**
     * 根据用户ID和是否已读查询通知
     */
    List<Notification> findByUserIdAndIsReadOrderByCreateTimeDesc(Long userId, Integer isRead);

    /**
     * 统计用户未读通知数
     */
    long countByUserIdAndIsRead(Long userId, Integer isRead);

    /**
     * 根据用户ID和通知类型查询
     */
    List<Notification> findByUserIdAndTypeOrderByCreateTimeDesc(Long userId, Integer type);

    /**
     * 删除用户的所有通知
     */
    void deleteByUserId(Long userId);
}