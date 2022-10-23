package com.example.csust_hot_wall.service;

import com.example.csust_hot_wall.entity.Follow;
import com.github.jeffreyning.mybatisplus.service.IMppService;

import java.util.List;

/**
 *
 */
public interface FollowService extends BaseService<Follow> {
    /**
     * 通过用户id查询关注列表
     * @param userId
     * @return
     */
    List<Follow> listByUserId(Integer userId);

    /**
     * 通过用户id查询关注总数
     * @param userId
     * @return
     */
    int countByUserId(Integer userId);

    /**
     * 通过关注用户id查询粉丝列表
     * @param followId
     * @return
     */
    List<Follow> listByFollowId(Integer followId);

    /**
     * 通过关注id查询粉丝总数
     * @param followId
     * @return
     */
    int countByFollowId(Integer followId);

}
