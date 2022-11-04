package com.example.csust_hot_wall.controller;

import com.example.csust_hot_wall.entity.Follow;
import com.example.csust_hot_wall.service.FollowService;
import com.example.csust_hot_wall.tools.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 评论控制器
 */
@RestController
@RequestMapping("/follow")
public class FollowController extends BaseController<Follow,FollowService>{

    @Autowired
    FollowService followService;

    @PostMapping("/add")
    public Map add(@RequestBody Follow t){
        // 普通用户无需设置user_id
        if ("user".equals(getRequest().getPower())){
            if (getRequest().getUserId() == null) return Message.err(Message.Text.NO_POWER_ERR);
            t.setUserId(getRequest().getUserId());
        }
        return super.add(t);
    }

    @DeleteMapping("/del")
    public Map delete(@RequestBody  Map<String,Integer[]> map){
        // 重写BaseController中的删除接口，因为是通过联合主键删除，防止出现未知的错误
        return Message.err("本接口无效！");
    }

    @DeleteMapping("/del_follow")
    public Map delete(@RequestBody Follow follow){
        // 普通用户无需设置user_id
        if ("user".equals(getRequest().getPower())){
            if (getRequest().getUserId() == null) return Message.err(Message.Text.NO_POWER_ERR);
            follow.setUserId(getRequest().getUserId());
        }
        // 关键属性检查，为假返回属性缺失信息
        if (!follow.securityCheck()) return Message.err(Message.Code.ERR_ATTRIBUTE_MISS);
        // 通过联合主键删除条目
        if (followService.deleteByMultiId(follow)) {
            return Message.send(Message.Text.REMOVE_SUCCESS);
        }else {
            return Message.err(Message.Text.REMOVE_ERR);
        }
    }

    /**
     * 查询指定用户的关注列表
     * @param uid
     * @return
     */
    @GetMapping("/query/uid")
    public Map queryByUserId(@RequestParam(value = "id", required = false) Integer uid){
        // 普通用户可直接查询自己的关注
        if ("user".equals(getRequest().getPower())){
            if (uid == null && getRequest().getUserId() != null) uid = getRequest().getUserId();
        }
        return Message.send(Message.Text.QUERY_SUCCESS,followService.listByUserId(uid));
    }

    /**
     * 查询指定用户的粉丝列表
     * @param fid
     * @return
     */
    @GetMapping("/query/fid")
    public Map queryByFollowId(@RequestParam(value = "id",required = false) Integer fid){
        // 普通用户可直接查询自己的粉丝
        if ("user".equals(getRequest().getPower())){
            if (fid == null && getRequest().getUserId() != null) fid = getRequest().getUserId();
        }
        return Message.send(Message.Text.QUERY_SUCCESS,followService.listByFollowId(fid));
    }

    /**
     * 查询指定用户的关注总数
     * @param uid
     * @return
     */
    @GetMapping("/count/uid")
    public Map countByUserId(@RequestParam("id") Integer uid){
        // 普通用户可直接查询自己的关注总数
        if ("user".equals(getRequest().getPower())){
            if (uid == null && getRequest().getUserId() != null) uid = getRequest().getUserId();
        }
        return Message.send(Message.Text.QUERY_SUCCESS,followService.countByUserId(uid));
    }

    /**
     * 查询指定用户的粉丝总数
     * @param fid
     * @return
     */
    @GetMapping("/count/fid")
    public Map countByFollowId(@RequestParam(value = "id", required = false) Integer fid){
        // 普通用户可直接查询自己的粉丝总数
        if ("user".equals(getRequest().getPower())){
            if (fid == null && getRequest().getUserId() != null) fid = getRequest().getUserId();
        }
        return Message.send(Message.Text.QUERY_SUCCESS,followService.countByFollowId(fid));
    }
}
