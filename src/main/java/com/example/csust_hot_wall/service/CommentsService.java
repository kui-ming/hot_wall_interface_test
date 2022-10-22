package com.example.csust_hot_wall.service;

import com.example.csust_hot_wall.entity.Comments;

import java.util.List;

/**
 *
 */
public interface CommentsService extends BaseService<Comments> {
    /**
     * 通过文章查询评论
     * @param articleId
     * @return
     */
    List<Comments> listByArticleId(Integer articleId);

    /**
     * 通过用户查询评论
     * @param userId
     * @return
     */
    List<Comments> listByUserId(Integer userId);

    /**
     * 通过父评论查询回复
     * @param replyId
     * @return
     */
    List<Comments> listByReplyId(Integer replyId);
}
