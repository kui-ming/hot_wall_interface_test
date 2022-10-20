package com.example.csust_hot_wall.service;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.github.jeffreyning.mybatisplus.service.IMppService;

public interface BaseService<T> extends IMppService<T> {

    default boolean exists(T t){return false;}

    default LambdaQueryWrapper<T>  searchToWrapper(String key, String value){
        return new LambdaQueryWrapper<>();
    }
}
