package com.example.csust_hot_wall.controller;

import com.example.csust_hot_wall.entity.Comments;
import com.example.csust_hot_wall.service.CommentsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 评论控制器
 */
@RestController
@RequestMapping("/comments")
public class CommentsController extends BaseController<Comments, CommentsService>{

}
