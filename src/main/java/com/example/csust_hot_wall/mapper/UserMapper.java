package com.example.csust_hot_wall.mapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.example.csust_hot_wall.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * @Entity com.example.csust_hot_wall.entity.User
 */
@Repository
public interface UserMapper extends MyBaseMapper<User> {

    List<User> selectByOpenid(@Param("openid") String openid);

    /**
     * 通过昵称查询用户列表
     * @param nickname
     * @return
     */
    List<User> selectByNickname(@Param("nickname") String nickname);

}




