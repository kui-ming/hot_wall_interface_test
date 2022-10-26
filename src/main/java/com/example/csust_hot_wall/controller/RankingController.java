package com.example.csust_hot_wall.controller;


import com.example.csust_hot_wall.service.RankingListService;
import com.example.csust_hot_wall.tools.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 用户控制器
 */
@RestController
@RequestMapping("/ranking")
public class RankingController{

    @Autowired
    RankingListService rankingListService;

    @GetMapping("/hot")
    public Map hotRanking(@RequestParam(value = "top" , defaultValue = "20") Integer top){
        if (top == 30) return Message.send(Message.Text.QUERY_SUCCESS, rankingListService.getHotRanking());
        else return Message.send(Message.Text.QUERY_SUCCESS, rankingListService.getHotRanking(top));
    }

    @GetMapping("/random_hot")
    public Map randomHot(){
        return Message.send(Message.Text.QUERY_SUCCESS,rankingListService.randomHotRanking(1));
    }

    @GetMapping("/new")
    public Map newRanking(@RequestParam(value = "top", defaultValue = "30") Integer top){
        if (top == 30) return Message.send(Message.Text.QUERY_SUCCESS, rankingListService.getNewRanking());
        else return Message.send(Message.Text.QUERY_SUCCESS, rankingListService.getNewRanking(top));
    }

    @GetMapping("/random_new")
    public Map randomNew(){
        return Message.send(Message.Text.QUERY_SUCCESS,rankingListService.randomNewRanking(1));
    }

}
