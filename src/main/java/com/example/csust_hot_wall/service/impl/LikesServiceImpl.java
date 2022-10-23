package com.example.csust_hot_wall.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.csust_hot_wall.configuration.ResultException;
import com.example.csust_hot_wall.entity.Article;
import com.example.csust_hot_wall.entity.Likes;
import com.example.csust_hot_wall.entity.User;
import com.example.csust_hot_wall.mapper.ArticleMapper;
import com.example.csust_hot_wall.mapper.UserMapper;
import com.example.csust_hot_wall.service.LikesService;
import com.example.csust_hot_wall.mapper.LikesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public class LikesServiceImpl extends BaseServiceImpl<LikesMapper, Likes>
    implements LikesService{

    @Autowired
    LikesMapper likesMapper;
    @Autowired
    ArticleMapper articleMapper;
    @Autowired
    UserMapper userMapper;

    @Override
    public boolean save(Likes entity) {
        // 不能点赞不存在的文章
        Article article = articleMapper.selectById(entity.getArticleId());
        if (article == null) throw new ResultException("文章不存在！");
        // 点赞用户必须是存在的
        User user = userMapper.selectById(entity.getUserId());
        if (user == null) throw new ResultException("未知用户！");
        if (super.save(entity)){
            // 同步增加文章属性中的点赞数
            articleMapper.autoIncrementLikesById(entity.getArticleId());
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteByMultiId(Likes entity) {
        // 同步减少文章属性中的点赞数
        Article article = articleMapper.selectById(entity.getArticleId());
        if (article == null) throw new ResultException("文章不存在！");
        if (super.deleteByMultiId(entity)){
            articleMapper.autoDecrementLikesById(article.getId());
            return true;
        }
        return false;
    }


    @Override
    public List<Likes> listByUserId(Integer userId) {
        return redundancy(likesMapper.selectByUserId(userId));
    }

    @Override
    public int countByUserId(Integer userId) {
        return likesMapper.countByUserId(userId);
    }

    @Override
    public List<Likes> listByArticleId(Integer articleId) {
        return redundancy(likesMapper.selectByArticleId(articleId));
    }

    @Override
    public int countByArticleId(Integer articleId) {
        return likesMapper.countByArticleId(articleId);
    }

    @Override
    protected Likes redundancy(Likes likes) {
        // 点赞信息增加用户名、点赞文章名、文章作者名
        // 用户名
        User user = userMapper.selectById(likes.getUserId());
        if (user == null) likes.setUser("未知用户");
        else likes.setUser(user.getNickname());
        // 文章名
        Article article = articleMapper.selectById(likes.getArticleId());
        if (article == null) likes.setArticle("未知文章");
        else likes.setArticle(article.getTitle());
        // 文章作者名
        if (article != null){
            User author = userMapper.selectById(article.getUserId());
            if (author == null) likes.setAuthor("未知作者");
            else likes.setAuthor(author.getNickname());
        }
        return likes;
    }

    /* 关键字查询 */

    static private class UserIdKey extends SearchKey<Likes>{
        /**
         * 通过文章id查询点赞用户列表 key:aid
         */
        private UserIdKey(){super("uid");}
        @Override
        public void search(LambdaQueryWrapper<Likes> wrapper, String value) {
            wrapper.eq(Likes::getUserId,value);
            wrapper.orderByDesc(Likes::getCreationTime);
        }
    }

    static private class ArticleIdKey extends SearchKey<Likes>{
        /**
         * 通过用户id查询点赞文章列表 key:uid
         */
        private ArticleIdKey() {
            super("aid");
        }
        @Override
        public void search(LambdaQueryWrapper<Likes> wrapper, String value) {
            wrapper.eq(Likes::getArticleId,value);
            wrapper.orderByDesc(Likes::getCreationTime);
        }
    }
}




