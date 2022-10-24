package com.example.csust_hot_wall.mapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.example.csust_hot_wall.entity.Collection;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * @Entity com.example.csust_hot_wall.entity.Collection
 */
@Repository
public interface CollectionMapper extends MyBaseMapper<Collection> {

    List<Collection> selectByUserIdAndArticleId(@Param("userId") Integer userId, @Param("articleId") Integer articleId);

    List<Collection> selectByUserId(@Param("userId") Integer userId);

    List<Collection> selectByArticleId(@Param("articleId") Integer articleId);

    /**
     * 查询指定用户的收藏数
     * @param userId
     * @return
     */
    int countByUserId(@Param("userId") Integer userId);

    /**
     * 查询指定文章的被收藏数
     * @param articleId
     * @return
     */
    int countByArticleId(@Param("articleId") Integer articleId);
}




