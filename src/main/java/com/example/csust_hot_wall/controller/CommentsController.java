package com.example.csust_hot_wall.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.csust_hot_wall.entity.Collection;
import com.example.csust_hot_wall.entity.Comments;
import com.example.csust_hot_wall.service.CommentsService;
import com.example.csust_hot_wall.tools.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 评论控制器
 */
@RestController
@RequestMapping("/comments")
public class CommentsController extends BaseController<Comments, CommentsService>{

    @Autowired
    CommentsService commentsService;

    @PostMapping("/add")
    public Map add(@RequestBody Comments t){
        // 普通用户无需传入user_id
        if ("user".equals(getRequest().getPower())){
            if (getRequest().getUserId() == null) return Message.err();
            t.setUserId(getRequest().getUserId());
        }
        return super.add(t);
    }

    @PutMapping("/update")
    public Map alter(@RequestBody Comments t){
        return Message.err("不能修改评论！");
    }

    @DeleteMapping("/del")
    public Map delete(@RequestBody Map<String,Integer[]> map){
        Integer[] ids = map.getOrDefault("ids",new Integer[]{});
        // 登录用户只能删除自己的评论
        if ("user".equals(getRequest().getPower())){
            if (getRequest().getUserId() == null) return Message.err();
            List<Comments> commentsList = commentsService.listByIds(Arrays.asList(ids));
            for (Comments comments : commentsList) {
                // 出现非登录用户的评论则报错
                if (!comments.getUserId().equals(getRequest().getUserId())) return Message.err(Message.Text.NO_POWER_ERR);
            }
        }
        return super.delete(map);
    }

    /**
     * 普通用户查询自己的评论（可分页和关键字查询）
     * @param page
     * @param size
     * @param key
     * @param value
     * @return
     */
    @GetMapping("/list")
    public Map queryMyself(@RequestParam(value = "page", required = false) Integer page,
                           @RequestParam(value = "size", required = false) Integer size,
                           @RequestParam(value = "k", defaultValue = "") String key,
                           @RequestParam(value = "v", defaultValue = "") String value){
        if (getRequest().getUserId() == null) return Message.err(Message.Text.NO_POWER_ERR);
        LambdaQueryWrapper<Comments> queryWrapper = commentsService.searchToWrapper(key, value);
        queryWrapper.eq(Comments::getUserId,getRequest().getUserId());
        return page(page,size,queryWrapper);
    }

    @GetMapping("/query/aid")
     public Map queryByArticleId(@RequestParam("id") Integer articleId){
         return Message.send(Message.Text.QUERY_SUCCESS,commentsService.listByArticleId(articleId));
     }

    @GetMapping("/query/uid")
    public Map queryByUserId(@RequestParam("id") Integer userId){
        return Message.send(Message.Text.QUERY_SUCCESS,commentsService.listByUserId(userId));
    }

}
