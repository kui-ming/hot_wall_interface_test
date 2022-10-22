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
}




