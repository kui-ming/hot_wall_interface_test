package com.example.csust_hot_wall.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.csust_hot_wall.configuration.ResultException;
import com.example.csust_hot_wall.entity.Article;
import com.example.csust_hot_wall.entity.Category;
import com.example.csust_hot_wall.entity.User;
import com.example.csust_hot_wall.mapper.*;
import com.example.csust_hot_wall.service.ArticleService;
import com.example.csust_hot_wall.tools.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
@Service
public class ArticleServiceImpl extends BaseServiceImpl<ArticleMapper, Article>
    implements ArticleService{

    @Autowired
    ArticleMapper articleMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    CategoryMapper categoryMapper;
    @Autowired
    CollectionMapper collectionMapper;
    @Autowired
    CommentsMapper commentsMapper;


    public boolean save(Article entity) {
        // 判断文章标题是否重复
        List<Article> articleList = articleMapper.selectByTitle(entity.getTitle());
        if (articleList.size() > 0) throw new ResultException("标题名不可重复！");
        // 判断类别是否存
        Category category = categoryMapper.selectById(entity.getCategoryId());
        if (category == null) throw new ResultException("无效类别！");
        // 判断用户是否存在
        User user = userMapper.selectById(entity.getUserId());
        if (user == null) throw new ResultException("无效作者！");
        if (super.save(entity)){
            // 同时增加类别的文章数
            categoryMapper.autoIncrementNumsById(entity.getCategoryId());
            // 类别文章增加成功与否都不影响文章添加，但影响数据的一致性
            return true;
        }
        return false;
    }

    @Override
    public boolean updateById(Article entity) {
        entity.setUserId(null); // 无法修改作者
        entity.setCreationTime(null); // 无法修改创建时间
        entity.setLikes(null); // 无法修改点赞
        entity.setVisitors(null); // 无法修改浏览量
        entity.setUpdateTime(new Date()); // 更新时间自动设置为现在
        return super.updateById(entity);
    }

    @Override
    public boolean removeById(Serializable id) {
        // 查找目标文章是否存在
        Article article = articleMapper.selectById(id);
        if (article == null) throw new ResultException("删除失败，文章不存在！");
        if (super.removeById(id)){
            // 同时减少类别的文章数
            categoryMapper.autoDecrementNumsById(article.getCategoryId());
            // 类别文章增加成功与否都不影响文章删除，但影响数据的一致性
            return true;
        }
        return false;
    }

    @Override
    public Article getDetailsById(Integer id) {
        Article article = articleMapper.selectDetailsById(id);
        if (article == null) throw new ResultException(Message.Text.QUERY_ERR.toString());
        // 同时增加文章浏览量
        articleMapper.autoIncrementVisitorsById(article.getId());
        article.setVisitors(article.getVisitors()+1);
        return redundancy(article);
    }

    @Override
    public <E extends IPage<Article>> E page(E page, Wrapper<Article> queryWrapper) {
        // 如果查询的字段为content则忽略
        ((LambdaQueryWrapper<Article>)queryWrapper).select(Article.class,i->!i.getProperty().equals("content"));
        return super.page(page,queryWrapper);
    }

    @Override
    public List<Article> list(Wrapper<Article> queryWrapper) {
        // 如果查询的字段为content则忽略
        ((LambdaQueryWrapper<Article>)queryWrapper).select(Article.class,i->!i.getProperty().equals("content"));
        return super.list(queryWrapper);
    }

    /**
     * 增加冗余字段
     * @param article 源对象，向此对象中增加
     * @return
     */
    @Override
    protected Article redundancy(Article article) {
        // 所有查询增加作者昵称、类别名、被收藏数
        // 作者
        User user = userMapper.selectById(article.getUserId());
        if (user == null) article.setAuthor("未知作者");
        else {
            article.setAuthor(user.getNickname());
            article.setHead_img(user.getImgPath());
        }
        // 类别名
        Category category = categoryMapper.selectById(article.getCategoryId());
        if (category == null) article.setCategory("未知类别");
        else article.setCategory(category.getTitle());
        // 被收藏数
        article.setCollectionCount(collectionMapper.countByArticleId(article.getId()));
        // 评论数
        article.setCommentsCount(commentsMapper.countByArticleId(article.getId()));
        return article;
    }

    /*
        关键字查询
通过文章标题查询 key:name
通过文章ID查询 key:id
通过作者名查询 key:author
通过作者ID查询 key:aid
通过简介查询 key:intro
通过类别ID查询 key:cid
     */

    private static class NameKey extends SearchKey<Article>{
        /**
         * 通过文章标题模糊查询 key:name
         */
        private NameKey(){super("name");}
        @Override
        public void search(LambdaQueryWrapper<Article> wrapper, String value) {
            wrapper.like(Article::getTitle,value);
        }
    }

    private static class IdKey extends SearchKey<Article>{
        /**
         * 通过文章编号查询 key:id
         */
        private IdKey(){super("id");}
        @Override
        public void search(LambdaQueryWrapper<Article> wrapper, String value) {
            wrapper.eq(Article::getId,value);
        }
    }

    private static class AuthorKey extends SearchKey<Article>{
        /**
         * 通过作者查询 key:author
         */
        private AuthorKey(){super("author");}
        @Override
        public void search(LambdaQueryWrapper<Article> wrapper, String value) {
            // 获取到他的大哥，也就是这个实现类的对象
            ArticleServiceImpl service = (ArticleServiceImpl) getBoss();
            // 在用户表中对昵称字段模糊查询
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.like(User::getNickname,value);
            List<User> userList = service.userMapper.selectList(queryWrapper);
            // 拿出其中的id字段组成列表
            List<Integer> idList= new ArrayList<>();
            if (userList.size() > 0) idList = userList.stream().map(User::getId).collect(Collectors.toList());
            else idList.add(-1); // 如果in中没有数据的话会报错
            // 通过找到的用户id查找文章
            wrapper.in(Article::getUserId,idList);
        }
    }

    private static class UserIdKey extends SearchKey<Article>{
        /**
         * 通过作者ID查询 key:uid
         */
        private UserIdKey(){super("uid");}
        @Override
        public void search(LambdaQueryWrapper<Article> wrapper, String value) {
            wrapper.eq(Article::getUserId,value);
        }
    }

    private static class IntroKey extends SearchKey<Article>{
        /**
         * 通过简介查询 key:intro
         */
        private IntroKey(){super("intro");}
        @Override
        public void search(LambdaQueryWrapper<Article> wrapper, String value) {
            wrapper.like(Article::getIntro,value);
        }
    }

    private static class CategoryKey extends SearchKey<Article>{
        /**
         * 通过类别ID查询 key:cid
         */
        private CategoryKey(){super("cid");}
        @Override
        public void search(LambdaQueryWrapper<Article> wrapper, String value) {
            wrapper.eq(Article::getCategoryId,value);
        }
    }


}




