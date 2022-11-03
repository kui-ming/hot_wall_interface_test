package com.example.csust_hot_wall.controller;

import com.example.csust_hot_wall.entity.Article;
import com.example.csust_hot_wall.service.ArticleService;
import com.example.csust_hot_wall.tools.Message;
import com.example.csust_hot_wall.tools.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 文章控制器
 */
@RestController
@RequestMapping("/article")
@CrossOrigin
public class ArticleController extends  BaseController<Article,ArticleService>{

    @Autowired
    ArticleService articleService;

    @PostMapping("/add")
    public Map add(@RequestBody Article article){
        Integer user_id = (Integer) request.getAttribute("user_id");
        article.setUserId(user_id);
        return super.add(article);
    }

    @PutMapping("/update")
    public Map alter(@RequestBody Article t){
        Integer user_id = (Integer) request.getAttribute("user_id");
        // 当前用于等于作者时
        if (!(user_id != null && user_id.equals(t.getUserId()))) return Message.err(Message.Text.NO_POWER_ERR);
        return super.alter(t);
    }

    @DeleteMapping("/del")
    public Map delete(@RequestBody Map<String,Integer[]> map){
        Integer user_id = (Integer) request.getAttribute("user_id");
        String power = (String) request.getAttribute("power");
        Integer[] ids = map.getOrDefault("ids",new Integer[]{});
        List<Article> articleList = articleService.listByIds(Arrays.asList(ids));
        for (Article article : articleList) {
            if (!article.getUserId().equals(user_id) && "user".equals(power))
                return Message.err(Message.Text.NO_POWER_ERR);
        }
        return super.delete(map);
    }

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
