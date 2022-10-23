package com.example.csust_hot_wall.mapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.example.csust_hot_wall.entity.Follow;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.jeffreyning.mybatisplus.base.MppBaseMapper;
import org.springframework.stereotype.Repository;

/**
 * @Entity com.example.csust_hot_wall.entity.Follow
 */
@Repository
public interface FollowMapper extends MyBaseMapper<Follow> {
    /**
     * 通过用户id查询关注列表
     * @param userId
     * @return
     */
    List<Follow> selectByUserId(@Param("userId") Integer userId);

    /**
     * 通过用户id查询关注总数
     * @param userId
     * @return
     */
    int countByUserId(@Param("userId") Integer userId);

    /**
     * 通过关注用户id查询粉丝列表
     * @param followId
     * @return
     */
    List<Follow> selectAllByFollowId(@Param("followId") Integer followId);

    /**
     * 通过关注id查询粉丝总数
     * @param followId
     * @return
     */
    int countByFollowId(@Param("followId") Integer followId);


}




