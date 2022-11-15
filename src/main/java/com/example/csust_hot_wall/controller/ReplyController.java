package com.example.csust_hot_wall.controller;

import com.example.csust_hot_wall.entity.Comments;
import com.example.csust_hot_wall.entity.Reply;
import com.example.csust_hot_wall.service.ReplyService;
import com.example.csust_hot_wall.tools.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reply")
public class ReplyController extends BaseController<Reply, ReplyService> {

    @Autowired
    ReplyService replyService;

    @PostMapping("/add")
    public Map add(@RequestBody Reply t){
        // 普通用户无需传入user_id
        if ("user".equals(getRequest().getPower())){
            if (getRequest().getUserId() == null) return Message.err();
            t.setUserId(getRequest().getUserId());
        }
        return super.add(t);
    }

    @DeleteMapping("/del")
    public Map delete(@RequestBody Map<String,Integer[]> map){
        Integer[] ids = map.getOrDefault("ids",new Integer[]{});
        // 登录用户只能删除自己的评论
        if ("user".equals(getRequest().getPower())){
            if (getRequest().getUserId() == null) return Message.err();
            List<Reply> replyList = replyService.listByIds(Arrays.asList(ids));
            for (Reply reply : replyList) {
                // 出现非登录用户的评论则报错
                if (!reply.getUserId().equals(getRequest().getUserId())) return Message.err(Message.Text.NO_POWER_ERR);
            }
        }
        return super.delete(map);
    }

}
