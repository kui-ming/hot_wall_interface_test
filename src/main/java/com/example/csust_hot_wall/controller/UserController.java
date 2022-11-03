package com.example.csust_hot_wall.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.csust_hot_wall.entity.Article;
import com.example.csust_hot_wall.entity.User;
import com.example.csust_hot_wall.service.UserService;
import com.example.csust_hot_wall.tools.JwtUtils;
import com.example.csust_hot_wall.tools.Message;
import com.example.csust_hot_wall.tools.weixin.OpenIdManage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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

    @PutMapping("/update")
    public Map alter(@RequestBody User t){
        Integer user_id = (Integer) request.getAttribute("user_id");
        String power = (String) request.getAttribute("power");
        if ("user".equals(power)){
            // 角色等于用户时,只能修改自己
            if (t.getId() == null && user_id != null) t.setId(user_id); // 如没有传入用户id，直接使用当前用户id
            else if (!(user_id != null && user_id.equals(t.getId()))) return Message.err(Message.Text.NO_POWER_ERR);
        }
        return super.alter(t);
    }


    @PostMapping("/openid")
    public Map openid(@RequestBody Map<String,String> data){
        String code = data.get("code");
        if (code == null) return Message.err(Message.Text.QUERY_ERR,"code不存在！");
        JSONObject openId = openIdManage.getOpenId(code);
        return Message.send(Message.Text.QUERY_SUCCESS,openId);
    }

    @PostMapping("/login")
    public Map login(@RequestBody Map<String,String> data){
        String code = data.get("code");
        if (code == null) return Message.err(Message.Text.QUERY_ERR,"code不存在！");
        // 通过code从微信拿取openid
        JSONObject res = openIdManage.getOpenId(code);
        if (res.getInteger("errcode") != 0) return Message.err(res.getString("errmsg"));
        User user = userService.login(res.getString("openid"));
        if (user == null) return Message.err(Message.Text.LOGIN_ERR);
        Map<String, Object> map = new HashMap<>();
        map.put("user_id",user.getId());
        map.put("open_id",user.getOpenid());
        map.put("power","user");
        String jwt = JwtUtils.createJWT(String.valueOf(user.getId()), map, 60 * 60 * 24 * 7);
        map.put("token",jwt);
        return Message.send(Message.Text.LOGIN_SUCCESS,map);
    }

    @GetMapping("infoByOpenid")
    public Map infoByOpenid(@RequestBody Map<String,String> data){
        String openid = data.get("openid");
        User user = userService.getByOpenid(openid);
        if (user==null) return Message.err(Message.Text.QUERY_ERR,"可能没有该数据。");
        return Message.send(Message.Text.QUERY_SUCCESS,user);
    }
}
