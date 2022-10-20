package com.example.csust_hot_wall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.csust_hot_wall.configuration.ResultException;
import com.example.csust_hot_wall.entity.Article;
import com.example.csust_hot_wall.service.ArticleService;
import com.example.csust_hot_wall.mapper.ArticleMapper;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class ArticleServiceImpl extends BaseServiceImpl<ArticleMapper, Article>
    implements ArticleService{

    public boolean save(Article entity) {
        throw new ResultException("测试异常");
        //super.save(entity);
        //return false;
    }
}




