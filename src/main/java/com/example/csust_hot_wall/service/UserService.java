package com.example.csust_hot_wall.service;

import com.example.csust_hot_wall.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 *
 */
public interface UserService extends BaseService<User> {
    User getByOpenid(String openid);

    User login(String openid);
}
