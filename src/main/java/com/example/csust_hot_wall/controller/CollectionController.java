package com.example.csust_hot_wall.controller;

import com.example.csust_hot_wall.entity.Collection;
import com.example.csust_hot_wall.service.CollectionService;
import com.example.csust_hot_wall.tools.Message;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 收藏控制器
 */
@RestController
@RequestMapping("/collection")
public class CollectionController extends BaseController<Collection, CollectionService>{

    @Autowired
    CollectionService collectionService;

    @PostMapping("/add")
    public Map add(@RequestBody Collection t){
       // 登录用户只能增加自己的收藏，无需传入用户id
        if ("user".equals(getRequest().getPower())){
            if (getRequest().getUserId() == null) return Message.err();
            t.setUserId(getRequest().getUserId());
        }
       return super.add(t);
    }

    @DeleteMapping("/del")
    public Map delete(@RequestBody Map<String,Integer[]> map){
        Integer[] ids = map.getOrDefault("ids",new Integer[]{});
        // 登录用户只能删除自己的收藏
        if ("user".equals(getRequest().getPower())){
            if (getRequest().getUserId() == null) return Message.err();
            List<Collection> collectionList = collectionService.listByIds(Arrays.asList(ids));
            for (Collection collection : collectionList) {
                // 出现非登录用户的收藏则报错
                if (!collection.getUserId().equals(getRequest().getUserId())) return Message.err(Message.Text.NO_POWER_ERR);
            }
        }
        return super.delete(map);
    }

    @PutMapping("/update")
    public Map alter(Collection collection) {
        return Message.err("无法修改收藏！");
    }


    /* 用户专用 */

    /**
     * 通过文章id删除用户的收藏
     * @param articleId
     * @return
     */
    @DeleteMapping("/cancel")
    public Map cancelCollectionByArticleId(@RequestParam("aid") Integer articleId){
        if (getRequest().getUserId() == null) return Message.err();
        if (collectionService.removeByUserIdAndArticleId(getRequest().getUserId(),articleId)) {
            return Message.send(Message.Text.REMOVE_SUCCESS);
        }
        return Message.err(Message.Text.REMOVE_ERR);
    }

    @GetMapping("article")
    public Map isCollectArticle(@RequestParam("aid") Integer articleId){
        if (getRequest().getUserId() == null) return Message.err();
        if (collectionService.isCollect(getRequest().getUserId(),articleId)){
            return Message.send("已收藏", true);
        }
        return Message.send("未收藏", false);
    }

    /* 查询类 */

    /**
     * 根据用户编号查询
     * @param id
     * @return
     */
    @GetMapping("/query/uid")
    public Map queryByUserId(@RequestParam(value = "id", required = false) Integer id){
        Integer userId = getRequest().getUserId();
        String power = getRequest().getPower();
        // 当角色为用户时，自动填充用户id
        if ("user".equals(power)){
            if (userId == null) return Message.err(Message.Text.NO_POWER_ERR);
            id = userId;
        }
        if (id == null) return Message.err(Message.Code.ERR_ATTRIBUTE_MISS);
        return Message.send(Message.Code.SUCCESS,collectionService.listByUserId(id));
    }

    /**
     * 根据文章编号查询
     * @param id
     * @return
     */
    @GetMapping("/query/aid")
    public Map queryByArticleId(@RequestParam("id") Integer id){
        return Message.send(Message.Code.SUCCESS,collectionService.listByArticleId(id));
    }
}
