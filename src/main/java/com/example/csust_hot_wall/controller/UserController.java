package com.example.csust_hot_wall.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.csust_hot_wall.entity.User;
import com.example.csust_hot_wall.service.UserService;
import com.example.csust_hot_wall.tools.Message;
import com.example.csust_hot_wall.tools.Utils;
import com.example.csust_hot_wall.tools.weixin.OpenIdManage;
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
    @Autowired
    OpenIdManage openIdManage;

    @PostMapping("/openid")
    public Map openid(@RequestBody Map<String,String> data){
        String code = data.get("code");
        if (code == null) return Message.err(Message.Text.QUERY_ERR,"code不存在！");
        JSONObject openId = openIdManage.getOpenId(code);
        return Message.send(Message.Text.QUERY_SUCCESS,openId);
    }

    @GetMapping("infoByOpenid")
    public Map infoByOpenid(@RequestBody Map<String,String> data){
        String openid = data.get("openid");
        User user = userService.getByOpenid(openid);
        if (user==null) return Message.err(Message.Text.QUERY_ERR,"可能没有该数据。");
        return Message.send(Message.Text.QUERY_SUCCESS,user);
    }
}
