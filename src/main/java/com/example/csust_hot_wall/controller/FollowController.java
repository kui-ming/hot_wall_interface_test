package com.example.csust_hot_wall.controller;

import com.example.csust_hot_wall.entity.Follow;
import com.example.csust_hot_wall.service.FollowService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 评论控制器
 */
@RestController
@RequestMapping("/follow")
public class FollowController extends BaseController<Follow, FollowService>{

}
