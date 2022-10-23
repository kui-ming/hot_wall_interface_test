package com.example.csust_hot_wall.mapper;
import java.io.Serializable;
import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.example.csust_hot_wall.entity.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * @Entity com.example.csust_hot_wall.entity.Article
 */
@Repository
public interface ArticleMapper extends MyBaseMapper<Article> {

    Article selectById(Serializable id);

    List<Article> selectByTitle(@Param("title") String title);

    List<Article> selectByCategoryId(@Param("categoryId") Integer categoryId);

    List<Article> selectAllByUserId(@Param("userId") Integer userId);

    int autoIncrementLikesById(@Param("id") Integer id);

    int autoDecrementLikesById(@Param("id") Integer id);
}




