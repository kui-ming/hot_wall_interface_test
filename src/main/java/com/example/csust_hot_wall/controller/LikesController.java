package com.example.csust_hot_wall.controller;

import com.example.csust_hot_wall.entity.Likes;
import com.example.csust_hot_wall.service.LikesService;
import com.example.csust_hot_wall.tools.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 评论控制器
 */
@RestController
@RequestMapping("/likes")
public class LikesController extends BaseController<Likes, LikesService>{

    @Autowired
    LikesService likesService;

    @DeleteMapping("/del")
    public Map delete(@RequestBody Map<String,Integer[]> map){
       return Message.err("无效接口！");
    }

    @DeleteMapping("/del_follow")
    public Map delete(@RequestBody Likes likes){
        // 关键属性检查，为假返回属性缺失信息
        if (!likes.securityCheck()) return Message.err(Message.Code.ERR_ATTRIBUTE_MISS);
        // 通过联合主键删除条目
        if (likesService.deleteByMultiId(likes)) {
            return Message.send(Message.Text.REMOVE_SUCCESS);
        }else {
            return Message.err(Message.Text.REMOVE_ERR);
        }
    }

    /**
     * 查询指定用户的点赞文章列表
     * @param uid
     * @return
     */
    @GetMapping("/query/uid")
    public Map queryByUserId(@RequestParam("id") Integer uid){
        return Message.send(Message.Text.QUERY_SUCCESS,likesService.listByUserId(uid));
    }

    /**
     * 查询指定文章的点赞用户列表
     * @param aid
     * @return
     */
    @GetMapping("/query/aid")
    public Map queryByArticleId(@RequestParam("id") Integer aid){
        return Message.send(Message.Text.QUERY_SUCCESS,likesService.listByArticleId(aid));
    }

    /**
     * 查询指定用户的点赞文章总数
     * @param uid
     * @return
     */
    @GetMapping("/count/uid")
    public Map countByUserId(@RequestParam("id") Integer uid){
        return Message.send(Message.Text.QUERY_SUCCESS,likesService.countByUserId(uid));
    }

    /**
     * 查询指定文章的点赞总数
     * @param aid
     * @return
     */
    @GetMapping("/count/aid")
    public Map countByArticleId(@RequestParam("id") Integer aid){
        return Message.send(Message.Text.QUERY_SUCCESS,likesService.countByArticleId(aid));
    }
}
