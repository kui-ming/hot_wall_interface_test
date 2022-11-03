package com.example.csust_hot_wall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.csust_hot_wall.configuration.ResultException;
import com.example.csust_hot_wall.entity.*;
import com.example.csust_hot_wall.mapper.*;
import com.example.csust_hot_wall.service.UserService;
import com.example.csust_hot_wall.tools.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User>
    implements UserService{

    @Autowired
    UserMapper userMapper;
    @Autowired
    ArticleMapper articleMapper;
    @Autowired
    CollectionMapper collectionMapper;
    @Autowired
    FollowMapper followMapper;
    @Autowired
    CommentsMapper commentsMapper;
    @Autowired
    LikesMapper likesMapper;

    @Override
    public boolean save(User entity) {
        // openid不能相同
        List<User> userList = userMapper.selectByOpenid(entity.getOpenid());
        if (userList.size() > 0) throw new ResultException("相同用户无法添加！");
        return super.save(entity);
    }

    @Override
    public boolean updateById(User entity) {
        // 普通修改不能修改openid、创建时间和更新时间
        entity.setCreationTime(null);
        entity.setUpdateTime(null);
        entity.setOpenid(null);
        return super.updateById(entity);
    }

    @Override
    public boolean removeById(Serializable id) {
        // 存在文章的用户无法删除
        List<Article> articleList = articleMapper.selectAllByUserId((Integer) id);
        if (articleList.size() > 0) throw new ResultException("存在文章的用户无法删除！");
        // 存在关注关系的用户不能删除
        List<Follow> followList = followMapper.selectByUserId((Integer) id);
        if (followList.size() > 0) throw new ResultException("存在关注关系的用户无法删除！");
        // 存在评论关系的用户不能删除
        List<Comments> commentsList = commentsMapper.selectByUserId((Integer) id);
        if (commentsList.size() > 0) throw new ResultException("进行评论过的用户无法删除！");
        // 存在收藏的用户不能删除
        List<Collection> collectionList = collectionMapper.selectByUserId((Integer) id);
        if (collectionList.size() > 0) throw new ResultException("存在收藏的用户无法删除！");

        if (super.removeById(id)) {
            // 同时删除点赞记录
            likesMapper.deleteByUserId((Integer) id);
            return true;
        }
        return false;
    }

    @Override
    public User getByOpenid(String openid) {
        if (openid == null) throw new ResultException("属性缺失！");
        List<User> userList = userMapper.selectByOpenid(openid);
        if (userList.size()>0) {
            return redundancy(userList.get(0));
        }
        return null;
    }

    @Override
    public User login(String openid) {
        // 如果已存在openid则不变
        List<User> userList = userMapper.selectByOpenid(openid);
        User user = null;
        if (userList.size() < 1){
            // 如果不存在openid则增加
            user = new User();
            user.setOpenid(openid);
            user.setNickname("微信用户");
            user.setCreationTime(new Date());
            user.setUpdateTime(new Date());
            if (userMapper.insert(user) < 1) {
                throw new ResultException("登录时增加用户失败！");
            }
        }
        else user = userList.get(0);
        return user;
    }

    @Override
    protected User redundancy(User user) {
        // 查询增加文章数、收藏数、关注数、粉丝数、点赞数、评论数
        user.setArticleCount(articleMapper.countByUserId(user.getId()));
        user.setCollectionCount(collectionMapper.countByUserId(user.getId()));
        user.setFollowCount(followMapper.countByUserId(user.getId()));
        user.setFansCount(followMapper.countByFollowId(user.getId()));
        user.setLikesCount(likesMapper.countByUserId(user.getId()));
        user.setCommentsCount(commentsMapper.countByUserId(user.getId()));
        return user;
    }

    /* 关键字查询 */

    private static class NicknameKey extends SearchKey<User>{
        /**
         * 昵称查询 key:nickname
         */
        private NicknameKey(){super("nickname");}
        @Override
        public void search(LambdaQueryWrapper<User> wrapper, String value) {
            wrapper.like(User::getNickname,value);
            wrapper.orderByDesc(User::getUpdateTime);
        }
    }
}




