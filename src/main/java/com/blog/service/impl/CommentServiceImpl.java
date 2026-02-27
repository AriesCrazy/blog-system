package com.blog.service.impl;

import com.blog.common.exception.BusinessException;
import com.blog.common.result.ResultCode;
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
import java.util.Map;
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
            throw new BusinessException(ResultCode.USER_NOT_EXIST);
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

        // 如果是回复评论，设置rootId
        if (commentDTO.getParentId() != null && commentDTO.getRootId() == null) {
            comment.setRootId(commentDTO.getParentId());
        }

        commentRepository.save(comment);

        // 增加文章评论数
        articleMapper.incrementCommentCount(commentDTO.getArticleId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteComment(String commentId, Long userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new BusinessException(ResultCode.COMMENT_NOT_EXIST));

        // 验证是否是评论作者本人
        if (!comment.getUserId().equals(userId)) {
            throw new BusinessException(ResultCode.FORBIDDEN);
        }

        // 删除评论
        commentRepository.deleteById(commentId);

        // 减少文章评论数
        articleMapper.decrementCommentCount(comment.getArticleId());
    }

    @Override
    public List<CommentVO> getCommentsByArticleId(Long articleId) {
        // 查询所有已通过的评论
        List<Comment> comments = commentRepository.findByArticleIdAndStatusOrderByCreateTimeDesc(articleId, 1);

        // 转换为树形结构
        return buildCommentTree(comments);
    }

    @Override
    public List<CommentVO> getCommentsByUserId(Long userId) {
        List<Comment> comments = commentRepository.findByUserIdOrderByCreateTimeDesc(userId);
        return comments.stream().map(this::convertToVO).collect(Collectors.toList());
    }

    @Override
    public void likeComment(String commentId, Long userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new BusinessException(ResultCode.COMMENT_NOT_EXIST));

        // TODO: 后续可以记录点赞关系，避免重复点赞
        comment.setLikeCount(comment.getLikeCount() + 1);
        commentRepository.save(comment);
    }

    @Override
    public long getCommentCount(Long articleId) {
        return commentRepository.countByArticleIdAndStatus(articleId, 1);
    }

    /**
     * 构建评论树
     */
    private List<CommentVO> buildCommentTree(List<Comment> comments) {
        // 转换为VO
        List<CommentVO> commentVOS = comments.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());

        // 按ID分组
        Map<String, CommentVO> commentMap = commentVOS.stream()
                .collect(Collectors.toMap(CommentVO::getId, vo -> vo));

        // 构建树形结构
        List<CommentVO> rootComments = new ArrayList<>();
        for (CommentVO commentVO : commentVOS) {
            if (commentVO.getParentId() == null) {
                // 顶级评论
                rootComments.add(commentVO);
            } else {
                // 子评论，添加到父评论的children中
                CommentVO parent = commentMap.get(commentVO.getParentId());
                if (parent != null) {
                    if (parent.getChildren() == null) {
                        parent.setChildren(new ArrayList<>());
                    }
                    parent.getChildren().add(commentVO);
                }
            }
        }

        return rootComments;
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