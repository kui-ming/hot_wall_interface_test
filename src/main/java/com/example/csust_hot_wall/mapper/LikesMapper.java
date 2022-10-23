package com.example.csust_hot_wall.mapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.example.csust_hot_wall.entity.Likes;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.jeffreyning.mybatisplus.base.MppBaseMapper;
import org.springframework.stereotype.Repository;

/**
 * @Entity com.example.csust_hot_wall.entity.Likes
 */
@Repository
public interface LikesMapper extends MyBaseMapper<Likes> {

    /**
     * 通过文章id查询点赞用户列表
     * @param articleId
     * @return
     */
    List<Likes> selectByArticleId(@Param("articleId") Integer articleId);

    /**
     * 通过用户id查询点赞文章列表
     * @param userId
     * @return
     */
    List<Likes> selectByUserId(@Param("userId") Integer userId);

    /**
     * 通过用户id查询点赞文章总数
     * @param userId
     * @return
     */
    int countByUserId(@Param("userId") Integer userId);

    /**
     * 通过文章id查询点赞总数
     * @param articleId
     * @return
     */
    int countByArticleId(@Param("articleId") Integer articleId);
}




