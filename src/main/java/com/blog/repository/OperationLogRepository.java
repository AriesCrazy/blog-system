package com.blog.repository;

import com.blog.entity.mongo.OperationLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 操作日志Repository
 */
@Repository
public interface OperationLogRepository extends MongoRepository<OperationLog, String> {

    /**
     * 根据用户ID查询操作日志
     */
    List<OperationLog> findByUserIdOrderByCreateTimeDesc(Long userId);

    /**
     * 根据模块查询操作日志
     */
    List<OperationLog> findByModuleOrderByCreateTimeDesc(String module);

    /**
     * 根据时间范围查询日志
     */
    List<OperationLog> findByCreateTimeBetweenOrderByCreateTimeDesc(
            LocalDateTime startTime,
            LocalDateTime endTime
    );

    /**
     * 根据是否成功查询日志
     */
    List<OperationLog> findBySuccessOrderByCreateTimeDesc(Integer success);
}