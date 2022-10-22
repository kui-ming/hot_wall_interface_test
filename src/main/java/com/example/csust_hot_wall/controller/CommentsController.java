package com.example.csust_hot_wall.controller;

import com.example.csust_hot_wall.entity.Comments;
import com.example.csust_hot_wall.service.CommentsService;
import com.example.csust_hot_wall.tools.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 评论控制器
 */
@RestController
@RequestMapping("/comments")
public class CommentsController extends BaseController<Comments, CommentsService>{

    @Autowired
    CommentsService commentsService;

    @GetMapping("/query/aid")
     public Map queryByArticleId(@RequestParam("id") Integer articleId){
         return Message.send(Message.Text.QUERY_SUCCESS,commentsService.listByArticleId(articleId));
     }

    @GetMapping("/query/uid")
    public Map queryByUserId(@RequestParam("id") Integer userId){
        return Message.send(Message.Text.QUERY_SUCCESS,commentsService.listByUserId(userId));
    }

    @GetMapping("/query/rid")
    public Map queryByReplyId(@RequestParam("id") Integer replyId){
        return Message.send(Message.Text.QUERY_SUCCESS,commentsService.listByReplyId(replyId));
    }
}
