package com.example.csust_hot_wall.service;

import com.example.csust_hot_wall.entity.Collection;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *
 */
public interface CollectionService extends BaseService<Collection> {

    List<Collection> listByUserId(Integer userId);

    List<Collection> listByArticleId(Integer articleId);

}
