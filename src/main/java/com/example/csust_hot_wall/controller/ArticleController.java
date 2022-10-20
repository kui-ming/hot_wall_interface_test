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
/*
    @PostMapping("/add")
    public Map add(@RequestBody Article article){
        if (Utils.PropertyIsEmpty(article,"userId")) return Message.err(Message.Code.ERR_ATTRIBUTE_MISS);
        article = article.stroke();
        if (articleService.save(article)) {
            return Message.send(Message.Text.ADD_SUCCESS);
        }else {
            return Message.err(Message.Text.ADD_ERR);
        }
    }
 */

}
