package com.example.csust_hot_wall.controller;

import com.example.csust_hot_wall.entity.User;
import com.example.csust_hot_wall.service.UserService;
import com.example.csust_hot_wall.tools.Message;
import com.example.csust_hot_wall.tools.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 用户控制器
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController<User,UserService>{

    @Autowired
    UserService userService;
}
