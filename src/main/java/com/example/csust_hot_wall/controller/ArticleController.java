package com.example.csust_hot_wall.controller;

import com.example.csust_hot_wall.entity.Article;
import com.example.csust_hot_wall.service.ArticleService;
import com.example.csust_hot_wall.tools.Message;
import com.example.csust_hot_wall.tools.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 文章控制器
 */
@RestController
@RequestMapping("/article")
public class ArticleController extends  BaseController<Article,ArticleService>{

    @Autowired
    ArticleService articleService;

    /**
     * 通过ID获取文章详情
     * @param id
     * @return
     */
    @GetMapping("/details")
    public Map getDetailsById(@RequestParam("id") Integer id){
        Article article = articleService.getDetailsById(id);
        return Message.send(Message.Text.QUERY_SUCCESS,article);
    }

}
