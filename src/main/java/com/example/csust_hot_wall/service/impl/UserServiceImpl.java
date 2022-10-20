package com.example.csust_hot_wall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.csust_hot_wall.configuration.ResultException;
import com.example.csust_hot_wall.entity.Article;
import com.example.csust_hot_wall.entity.User;
import com.example.csust_hot_wall.mapper.ArticleMapper;
import com.example.csust_hot_wall.service.UserService;
import com.example.csust_hot_wall.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

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

    @Override
    public boolean save(User entity) {
        // openid不能相同
        List<User> userList = userMapper.selectByOpenid(entity.getOpenid());
        if (userList.size() > 0) throw new ResultException("相同用户无法添加！");
        return super.save(entity);
    }

    @Override
    public boolean updateById(User entity) {
        // 普通修改不能修改创建时间和更新时间
        entity.setCreationTime(null);
        entity.setUpdateTime(null);
        return updateById(entity);
    }

    @Override
    public boolean removeById(Serializable id) {
        // 存在文章的用户无法删除
        List<Article> articleList = articleMapper.selectAllByUserId((Integer) id);
        if (articleList.size() > 0) throw new ResultException("存在文章的用户无法删除！");
        return super.removeById(id);
    }

    @Override
    public User getByOpenid(String openid) {
        if (openid == null) throw new ResultException("属性缺失！");
        List<User> userList = userMapper.selectByOpenid(openid);
        return userList.size()>0?userList.get(0):null;
    }
}




