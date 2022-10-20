package com.example.csust_hot_wall.mapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.example.csust_hot_wall.entity.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * @Entity com.example.csust_hot_wall.entity.Category
 */
@Repository
public interface CategoryMapper extends MyBaseMapper<Category> {
    List<Category> selectByTitle(@Param("title") String title);

    /**
     * 指定类别文章数加一
     * @param id
     * @return
     */
    int autoIncrementNumsById(@Param("id") Integer id);

    /**
     * 指定类别文章数减一
     * @param id
     * @return
     */
    int autoDecrementNumsById(@Param("id") Integer id);
}




