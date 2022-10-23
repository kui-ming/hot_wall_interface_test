package com.example.csust_hot_wall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.csust_hot_wall.configuration.ResultException;
import com.example.csust_hot_wall.entity.Follow;
import com.example.csust_hot_wall.entity.User;
import com.example.csust_hot_wall.mapper.UserMapper;
import com.example.csust_hot_wall.service.FollowService;
import com.example.csust_hot_wall.mapper.FollowMapper;
import com.github.jeffreyning.mybatisplus.service.MppServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 *
 */
@Service
public class FollowServiceImpl extends BaseServiceImpl<FollowMapper, Follow>
    implements FollowService{

    @Autowired
    UserMapper userMapper;
    @Autowired
    FollowMapper followMapper;


    @Override
    public boolean save(Follow entity) {
        User u1 = userMapper.selectById(entity.getUserId());
        if (u1 == null) throw new ResultException("用户不存在！");
        User u2 = userMapper.selectById(entity.getFollowId());
        if (u2 == null) throw new ResultException("关注用户不存在！");
        return super.save(entity);
    }

    @Override
    public List<Follow> listByUserId(Integer userId) {
        return redundancy(followMapper.selectByUserId(userId));
    }

    @Override
    public int countByUserId(Integer userId) {
        return followMapper.countByUserId(userId);
    }

    @Override
    public List<Follow> listByFollowId(Integer followId) {
        return redundancy(followMapper.selectAllByFollowId(followId));
    }

    @Override
    public int countByFollowId(Integer followId) {
        return followMapper.countByFollowId(followId);
    }

    @Override
    protected Follow redundancy(Follow follow) {
        // 所有查询关注信息增加关注者用户名、被关注者用户名
        // 关注人用户
        User user = userMapper.selectById(follow.getUserId());
        if (user == null) follow.setUser("无效用户");
        else follow.setUser(user.getNickname());
        //被关注者用户
        User follow_user = userMapper.selectById(follow.getFollowId());
        if (follow_user == null) follow.setFollow("无效用户");
        else follow.setFollow(follow_user.getNickname());

        return follow;
    }

    /* 关键字查询 */

    static private class UserIdKey extends SearchKey<Follow>{
        /**
         * 用户id查询
         */
        private UserIdKey(){super("uid");}
        @Override
        public void search(LambdaQueryWrapper<Follow> wrapper, String value) {
            wrapper.eq(Follow::getUserId,value);
            wrapper.orderByDesc(Follow::getCreationTime);
        }
    }

    static private class FollowIdKey extends SearchKey<Follow>{
        /**
         * 关注用户id查询
         */
        private FollowIdKey(){super("fid");}
        @Override
        public void search(LambdaQueryWrapper<Follow> wrapper, String value) {
            wrapper.eq(Follow::getFollowId,value);
            wrapper.orderByDesc(Follow::getCreationTime);
        }
    }

}




