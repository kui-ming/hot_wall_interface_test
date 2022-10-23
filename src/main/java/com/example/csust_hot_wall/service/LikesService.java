package com.example.csust_hot_wall.service;

import com.example.csust_hot_wall.entity.Likes;

import java.util.List;

/**
 *
 */
public interface LikesService extends BaseService<Likes> {
    /**
     * 通过用户id查询点赞文章列表
     * @param userId
     * @return
     */
    List<Likes> listByUserId(Integer userId);

    /**
     * 通过用户id查询点赞文章总数
     * @param userId
     * @return
     */
    int countByUserId(Integer userId);

    /**
     * 通过文章id查询点赞用户列表
     * @param articleId
     * @return
     */
    List<Likes> listByArticleId(Integer articleId);

    /**
     * 通过文章id查询点赞总数
     * @param articleId
     * @return
     */
    int countByArticleId(Integer articleId);
}
