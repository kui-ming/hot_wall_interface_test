package com.example.csust_hot_wall.service;

import com.example.csust_hot_wall.entity.Collection;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *
 */
public interface CollectionService extends BaseService<Collection> {

    boolean removeByUserIdAndArticleId(Integer uid,Integer aid);

    // 通过文章id查询指定用户是否收藏此文章
    boolean isCollect(Integer uid, Integer aid);

    List<Collection> listByUserId(Integer userId);

    List<Collection> listByArticleId(Integer articleId);

}
