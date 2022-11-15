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

    @PostMapping("/add")
    public Map add(@RequestBody Likes t){
        // 普通用户无需设置user_id
        if ("user".equals(getRequest().getPower())){
            if (getRequest().getUserId() == null) return Message.err(Message.Text.NO_POWER_ERR);
            t.setUserId(getRequest().getUserId());
        }
        return super.add(t);
    }

    @DeleteMapping("/del")
    public Map delete(@RequestBody Map<String,Integer[]> map){
       return Message.err("无效接口！");
    }

    @DeleteMapping("/del_follow")
    public Map delete(@RequestBody Likes likes){
        // 普通用户无需设置user_id
        if ("user".equals(getRequest().getPower())){
            if (getRequest().getUserId() == null) return Message.err(Message.Text.NO_POWER_ERR);
            likes.setUserId(getRequest().getUserId());
        }
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
     * 查询当前用户是否点赞了某篇文章
     * @param articleId
     * @return
     */
    @GetMapping("info")
    public Map queryByid(@RequestParam("id") Integer articleId ){
        Integer uid = getRequest().getUserId();
        if(uid == null) return Message.err("查询当前用户失败！");
        Likes likes = new Likes();
        likes.setArticleId(articleId);
        likes.setUserId(uid);
        Likes result = likesService.selectByMultiId(likes);
        Object b = false;
        if (result == null) return Message.send("未点赞", false);
        return Message.send("已点赞", true);
    }

    /**
     * 查询指定用户的点赞文章列表
     * @param uid
     * @return
     */
    @GetMapping("/query/uid")
    public Map queryByUserId(@RequestParam(value = "id", required = false) Integer uid){
        // 登录用户可直接查询自己的点赞的文章列表
        if ("user".equals(getRequest().getPower())){
            if (uid == null && getRequest().getUserId() != null) uid = getRequest().getUserId();
        }
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
    public Map countByUserId(@RequestParam(value = "id", required = false) Integer uid){
        // 登录用户可直接查询自己的点赞的文章总数
        if ("user".equals(getRequest().getPower())){
            if (uid == null && getRequest().getUserId() != null) uid = getRequest().getUserId();
        }
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
