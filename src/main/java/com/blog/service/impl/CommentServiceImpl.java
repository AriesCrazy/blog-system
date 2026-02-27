package com.blog.service.impl;

import com.blog.common.exception.BusinessException;
import com.blog.dto.CommentDTO;
import com.blog.entity.mongo.Comment;
import com.blog.entity.mysql.User;
import com.blog.mapper.ArticleMapper;
import com.blog.mapper.UserMapper;
import com.blog.repository.CommentRepository;
import com.blog.service.CommentService;
import com.blog.vo.CommentVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 评论Service实现类
 */
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserMapper userMapper;
    private final ArticleMapper articleMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addComment(CommentDTO commentDTO, Long userId) {
        // 获取用户信息
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        // 创建评论
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentDTO, comment);
        comment.setUserId(userId);
        comment.setNickname(user.getNickname());
        comment.setAvatar(user.getAvatar());
        comment.setLikeCount(0L);
        comment.setStatus(1); // 默认通过
        comment.setCreateTime(LocalDateTime.now());
        comment.setUpdateTime(LocalDateTime.now());

        // 保存评论
        commentRepository.save(comment);

        // 增加文章评论数
        articleMapper.incrementCommentCount(commentDTO.getArticleId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteComment(String commentId, Long userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new BusinessException("评论不存在"));

        // 验证是否是评论作者
        if (!comment.getUserId().equals(userId)) {
            throw new BusinessException("无权删除此评论");
        }

        // 删除评论（修改状态）
        comment.setStatus(2); // 已删除
        commentRepository.save(comment);

        // 减少文章评论数
        articleMapper.decrementCommentCount(comment.getArticleId());
    }

    @Override
    public List<CommentVO> getCommentsByArticleId(Long articleId) {
        // 查询所有评论
        List<Comment> allComments = commentRepository.findByArticleIdAndStatusOrderByCreateTimeDesc(articleId, 1);

        // 构建树形结构
        List<CommentVO> topLevelComments = new ArrayList<>();

        // 先找出所有顶级评论（parentId为null）
        for (Comment comment : allComments) {
            if (comment.getParentId() == null) {
                CommentVO commentVO = convertToVO(comment);
                commentVO.setChildren(new ArrayList<>());
                topLevelComments.add(commentVO);
            }
        }

        // 为每个顶级评论找子评论
        for (CommentVO topComment : topLevelComments) {
            List<CommentVO> children = allComments.stream()
                    .filter(c -> topComment.getId().equals(c.getParentId()))
                    .map(this::convertToVO)
                    .collect(Collectors.toList());
            topComment.setChildren(children);
        }

        return topLevelComments;
    }

    @Override
    public List<CommentVO> getCommentsByUserId(Long userId) {
        List<Comment> comments = commentRepository.findByUserIdOrderByCreateTimeDesc(userId);
        return comments.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    @Override
    public void likeComment(String commentId, Long userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new BusinessException("评论不存在"));

        // TODO: 后续可以记录点赞关系到Redis
        comment.setLikeCount(comment.getLikeCount() + 1);
        commentRepository.save(comment);
    }

    @Override
    public CommentVO getCommentById(String commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new BusinessException("评论不存在"));
        return convertToVO(comment);
    }

    /**
     * 转换为VO
     */
    private CommentVO convertToVO(Comment comment) {
        CommentVO commentVO = new CommentVO();
        BeanUtils.copyProperties(comment, commentVO);
        return commentVO;
    }
}