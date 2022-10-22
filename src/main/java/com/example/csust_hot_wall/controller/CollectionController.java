package com.example.csust_hot_wall.controller;

import com.example.csust_hot_wall.entity.Collection;
import com.example.csust_hot_wall.service.CollectionService;
import com.example.csust_hot_wall.tools.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.Map;

/**
 * 收藏控制器
 */
@RestController
@RequestMapping("/collection")
public class CollectionController extends BaseController<Collection, CollectionService>{

    @Autowired
    CollectionService collectionService;


    @PutMapping("/update")
    public Map alter(Collection collection) {
        return Message.err("无法修改收藏！");
    }

    /**
     * 根据用户编号查询
     * @param id
     * @return
     */
    @GetMapping("/query/uid")
    public Map queryByUserId(@RequestParam("id") Integer id){
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
