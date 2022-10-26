package com.example.csust_hot_wall.mapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.example.csust_hot_wall.entity.Comments;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * @Entity com.example.csust_hot_wall.entity.Comments
 */
@Repository
public interface CommentsMapper extends MyBaseMapper<Comments> {

    /**
     * 通过文章查询评论
     * @param articleId
     * @return
     */
    List<Comments> selectByArticleId(@Param("articleId") Integer articleId);

    /**
     * 通过用户查询评论
     * @param userId
     * @return
     */
    List<Comments> selectByUserId(@Param("userId") Integer userId);

    /**
     * 通过父评论查询回复
     * @param replyId
     * @return
     */
    List<Comments> selectByReplyId(@Param("replyId") Integer replyId);

    Comments selectOneByArticleIdOrderByCreationTime(@Param("articleId") Integer articleId);

    /**
     * 查询指定用户的评论与回复数
     * @param userId
     * @return
     */
    int countByUserId(@Param("userId") Integer userId);

    /**
     * 查询指定文章的评论与回复数
     * @param articleId
     * @return
     */
    int countByArticleId(@Param("articleId") Integer articleId);
}




