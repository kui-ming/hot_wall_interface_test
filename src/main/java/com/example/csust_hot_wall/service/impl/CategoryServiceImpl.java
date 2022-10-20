package com.example.csust_hot_wall.service.impl;

import com.example.csust_hot_wall.configuration.ResultException;
import com.example.csust_hot_wall.entity.Article;
import com.example.csust_hot_wall.entity.Category;
import com.example.csust_hot_wall.mapper.ArticleMapper;
import com.example.csust_hot_wall.service.CategoryService;
import com.example.csust_hot_wall.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 *
 */
@Service
public class CategoryServiceImpl extends BaseServiceImpl<CategoryMapper, Category>
    implements CategoryService{

    @Autowired
    CategoryMapper categoryMapper;
    @Autowired
    ArticleMapper articleMapper;

    @Override
    public boolean save(Category entity) {
        //名称不能相同
        List<Category> categoryList = categoryMapper.selectByTitle(entity.getTitle());
        if (categoryList.size() > 0) throw new ResultException("名称不能相同！");
        return super.save(entity);
    }

    @Override
    public boolean removeById(Serializable id) {
        // 还存在内容的无法被删除
        List<Article> articleList = articleMapper.selectByCategoryId((Integer) id);
        if (articleList.size() > 0) throw new ResultException("该类别存在内容！");
        if (!super.removeById(id)) throw new ResultException("删除失败！");
        return true;
    }

    @Override
    public boolean updateById(Category entity) {
        // 普通修改无法修改创建日期和文章数
        entity.setCreationTime(null);
        entity.setNums(null);
        return super.updateById(entity);
    }

}




