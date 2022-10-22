package com.example.csust_hot_wall.service.impl;

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

/**
 *
 */
@Service
public class FollowServiceImpl extends MppServiceImpl<FollowMapper, Follow>
    implements FollowService{

    @Autowired
    UserMapper userMapper;

    @Override
    public boolean save(Follow entity) {
        User u1 = userMapper.selectById(entity.getUserId());
        if (u1 == null) throw new ResultException("用户不存在！");
        User u2 = userMapper.selectById(entity.getFollowId());
        if (u2 == null) throw new ResultException("关注用户不存在！");
        return super.save(entity);
    }

}




