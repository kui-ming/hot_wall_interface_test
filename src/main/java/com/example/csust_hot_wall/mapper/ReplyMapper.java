package com.example.csust_hot_wall.mapper;
import org.apache.ibatis.annotations.Param;

import com.example.csust_hot_wall.entity.Reply;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * @Entity com.example.csust_hot_wall.entity.Reply
 */
@Repository
public interface ReplyMapper extends MyBaseMapper<Reply> {

    int countByCommentId(@Param("commentId") Integer commentId);

}




