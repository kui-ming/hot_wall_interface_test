package com.example.csust_hot_wall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.csust_hot_wall.configuration.ResultException;
import com.example.csust_hot_wall.entity.Article;
import com.example.csust_hot_wall.entity.Category;
import com.example.csust_hot_wall.entity.User;
import com.example.csust_hot_wall.mapper.CategoryMapper;
import com.example.csust_hot_wall.mapper.UserMapper;
import com.example.csust_hot_wall.service.ArticleService;
import com.example.csust_hot_wall.mapper.ArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

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

    /**
     * 增加冗余字段
     * @param article 源对象，向此对象中增加
     * @return
     */
    @Override
    protected Article redundancy(Article article) {
        // 所有查询增加作者昵称、类别名
        // 作者
        User user = userMapper.selectById(article.getUserId());
        if (user == null) article.setAuthor("未知作者");
        else article.setAuthor(user.getNickname());
        // 类别名
        Category category = categoryMapper.selectById(article.getCategoryId());
        if (category == null) article.setCategory("未知类别");
        else article.setCategory(category.getTitle());

        return article;
    }
}




