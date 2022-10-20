package com.example.csust_hot_wall.controller;

import com.example.csust_hot_wall.entity.Follow;
import com.example.csust_hot_wall.entity.Likes;
import com.example.csust_hot_wall.service.FollowService;
import com.example.csust_hot_wall.service.LikesService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 评论控制器
 */
@RestController
@RequestMapping("/likes")
public class LikesController extends BaseController<Likes, LikesService>{

}