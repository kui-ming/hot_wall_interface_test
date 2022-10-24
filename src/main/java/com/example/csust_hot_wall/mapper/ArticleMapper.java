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

    /**
     * 查询属于指定用户的文章数
     * @param userId
     * @return
     */
    int countByUserId(@Param("userId") Integer userId);

    /**
     * 查询指定类别的文章数
     * @param categoryId
     * @return
     */
    int countByCategoryId(@Param("categoryId") Integer categoryId);

    int autoIncrementLikesById(@Param("id") Integer id);

    int autoDecrementLikesById(@Param("id") Integer id);


}




