package com.example.csust_hot_wall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.csust_hot_wall.configuration.ResultException;
import com.example.csust_hot_wall.entity.Article;
import com.example.csust_hot_wall.entity.Collection;
import com.example.csust_hot_wall.entity.User;
import com.example.csust_hot_wall.mapper.ArticleMapper;
import com.example.csust_hot_wall.mapper.UserMapper;
import com.example.csust_hot_wall.service.CollectionService;
import com.example.csust_hot_wall.mapper.CollectionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public class CollectionServiceImpl extends BaseServiceImpl<CollectionMapper, Collection>
    implements CollectionService{

    @Autowired
    CollectionMapper collectionMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    ArticleMapper articleMapper;

    // 查询器
    private static Searcher<Collection> searcher;

    @Override
    public boolean save(Collection entity) {
        // 用户有效
        User user = userMapper.selectById(entity.getUserId());
        if (user == null) throw new ResultException("用户无效！");
        // 文章有效
        Article article = articleMapper.selectById(entity.getArticleId());
        if (article == null) throw new ResultException("文章无效！");
        // 联合主键（用户编号，文章编号）不能相同
        List<Collection> list = collectionMapper.selectByUserIdAndArticleId(entity.getUserId(), entity.getArticleId());
        if (list.size() > 0) throw new ResultException("收藏重复！");
        return super.save(entity);
    }

    @Override
    public List<Collection> listByUserId(Integer userId) {
        List<Collection> collectionList = collectionMapper.selectByUserId(userId);
        redundancy(collectionList); // 增加冗余字段
        return collectionList;
    }

    @Override
    public List<Collection> listByArticleId(Integer articleId) {
        List<Collection> collectionList = collectionMapper.selectByArticleId(articleId);
        redundancy(collectionList);
        return collectionList;
    }

    /* 关键字查询 */

    @Override
    public LambdaQueryWrapper<Collection> searchToWrapper(String key, String value) {
        if (searcher == null){
            synchronized (CollectionServiceImpl.class){
                if (searcher == null){
                    searcher = new Searcher<>();
                    searcher.add(new UserIdKey());
                    searcher.add(new ArticleIdKey());
                }
            }
        }
        return searcher.getWrapper(key,value);
    }

    static class UserIdKey extends SearchKey<Collection>{
        /**
         * 通过用户id查询
         */
        private UserIdKey(){super("uid");}
        @Override
        public LambdaQueryWrapper<Collection> search(String value) {
            Integer uid = Integer.parseInt(value);
            LambdaQueryWrapper<Collection> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Collection::getUserId,uid); // 通过用户id查询
            wrapper.orderByDesc(Collection::getCreationTime); // 时间倒序
            return wrapper;
        }
    }

    static class ArticleIdKey extends SearchKey<Collection>{
        private ArticleIdKey(){super("aid");}
        @Override
        public LambdaQueryWrapper<Collection> search(String value) {
            Integer aid = Integer.parseInt(value);
            LambdaQueryWrapper<Collection> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Collection::getArticleId,aid); // 通过文章id查询
            wrapper.orderByDesc(Collection::getCreationTime); // 时间倒序
            return wrapper;
        }
    }
}




