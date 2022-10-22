package com.example.csust_hot_wall.controller;

import com.example.csust_hot_wall.entity.Follow;
import com.example.csust_hot_wall.service.FollowService;
import com.example.csust_hot_wall.tools.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 评论控制器
 */
@RestController
@RequestMapping("/follow")
public class FollowController{

    @Autowired
    FollowService followService;

    @DeleteMapping("/del")
    public Map delete(@RequestBody  Map<String,Integer[]> map){
        // 重写BaseController中的删除接口，因为是通过联合主键删除，防止出现未知的错误
        return Message.err("本接口无效！");
    }

    @DeleteMapping("/del_follow")
    public Map delete(@RequestBody Follow follow){
        // 关键属性检查，为假返回属性缺失信息
        if (!follow.securityCheck()) return Message.err(Message.Code.ERR_ATTRIBUTE_MISS);
        // 通过联合主键删除条目
        if (followService.deleteByMultiId(follow)) {
            return Message.send(Message.Text.REMOVE_SUCCESS);
        }else {
            return Message.err(Message.Text.REMOVE_ERR);
        }
    }
}
