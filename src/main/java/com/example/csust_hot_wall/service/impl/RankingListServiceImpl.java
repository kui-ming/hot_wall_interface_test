package com.example.csust_hot_wall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.csust_hot_wall.entity.Article;
import com.example.csust_hot_wall.entity.Comments;
import com.example.csust_hot_wall.entity.HotValue;
import com.example.csust_hot_wall.mapper.CommentsMapper;
import com.example.csust_hot_wall.mapper.RankingListManager;
import com.example.csust_hot_wall.service.ArticleService;
import com.example.csust_hot_wall.service.RankingListService;
import com.example.csust_hot_wall.tools.CalendarUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RankingListServiceImpl implements RankingListService {

    @Autowired
    RankingListManager rankingListManager;
    @Autowired
    ArticleService articleService;
    @Autowired
    CommentsMapper commentsMapper;

    /**
     * 更新热榜
     */
    public void updateHotRanking() {
        // 查询近期文章数据
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        Calendar calendar = CalendarUtil.forward(7); // 七天之前
        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(Calendar.HOUR_OF_DAY, calendar1.get(Calendar.HOUR_OF_DAY) - 4); // 前四个小时
        // 查询前七天内至四个小时前的文章
        wrapper.between(Article::getCreationTime,calendar.getTime(),calendar1.getTime());
        List<Article> articleList = articleService.list(wrapper);
        if (articleList.size() < 4){
            calendar = CalendarUtil.forward(30); // 30天之前
            wrapper.between(Article::getCreationTime,calendar.getTime(),calendar1.getTime());
            articleList = articleService.list(wrapper);
        }
        else if (articleList.size() < 4){
            // 查询4小时前的所有文章
            wrapper.le(Article::getCreationTime,calendar1.getTime());
            articleList = articleService.list(wrapper);
        }
        // 清空旧排行榜
        rankingListManager.clearHot();
        // 计算
        for (Article article : articleList) {
            HotValue hotValue = new HotValue();
            hotValue.setArticleId(article.getId());
            hotValue.setScore(calcHot(article));
            rankingListManager.pushHot(hotValue);
        }
    }

    /**
     * 更新新榜
     */
    @Override
    public void updateNewRanking() {
        // 查询近期文章数据
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        Calendar calendar = CalendarUtil.forward(1); // 七天之前
        // 查询发布于一天内的文章
        wrapper.ge(Article::getCreationTime,calendar.getTime());
        List<Article> articleList = articleService.list(wrapper);
        // 清空旧排行榜
        rankingListManager.clearNew();
        // 计算
        for (Article article : articleList) {
            HotValue hotValue = new HotValue();
            hotValue.setArticleId(article.getId());
            hotValue.setScore(calcHot(article));
            rankingListManager.pushNew(hotValue);
        }
    }

    @Override
    public List<Article> getHotRanking() {
        return getHotRanking(rankingListManager.getHotListMaxLength());
    }

    @Override
    public List<Article> getNewRanking() {
        return getNewRanking(rankingListManager.getNewListMaxLength());
    }

    @Override
    public List<Article> getHotRanking(int top) {
        List<HotValue> hotValueList = rankingListManager.getHot(top);
        return getArticleList(hotValueList);
    }

    @Override
    public List<Article> getNewRanking(int top) {
        List<HotValue> hotValueList = rankingListManager.getNew(top);
        return getArticleList(hotValueList);
    }

    @Override
    public List<Article> randomHotRanking(int num) {
        List<HotValue> hot = rankingListManager.getHot();
        return random(hot,num);
    }

    @Override
    public List<Article> randomNewRanking(int num) {
        List<HotValue> newRanking = rankingListManager.getNew();
        return random(newRanking,num);
    }

    private List<Article> random(List<HotValue> list,int num){
        num = Math.min(num, list.size());
        if (num == list.size()) return getArticleList(list);
        Set<Integer> set = new HashSet<>();
        while(true){
            set.add((int) (Math.random() * list.size()));
            if (set.size() >= num) break;
        }
        List<HotValue> hotValueList = new ArrayList<>();
        for (Integer index : set) {
            hotValueList.add(list.get(index));
        }
        return getArticleList(hotValueList);
    }

    /**
     * 通过热榜信息获取文章列表
     * @param list
     * @return
     */
    private List<Article> getArticleList(List<HotValue> list){
        List<Article> articleList = new ArrayList<>();
        for (HotValue hotValue : list) {
            Article article = articleService.getById(hotValue.getArticleId());
            if (article != null) articleList.add(article);
        }
        return articleList;
    }

    /**
     * 计算文章热度
     * @param article
     * @return
     */
    private double calcHot(Article article){
        /*
        使用 hacker news 的排名算法
        Score=P/T^G
            P = 浏览数 + 1 + (点赞 * 2 + 评论回复 * 4 + 收藏 * 8) ^ G
            T = ((发布间隔小时 + 1) - (发布间隔小时 - 最近评论间隔小时) / 2) (PS:间隔小时用当前时间减去发布或评论时间)
            G = 1.1 (PS:G数越大，时间久的文章排名下滑越快)
         */
        double g = 1.1;
        // 计算P值
        Integer visitors = article.getVisitors(); // 浏览数
        Integer likes = article.getLikes(); // 点赞数
        Integer collections = article.getCollectionCount(); // 收藏数
        Integer comments = article.getCommentsCount(); // 评论数
        double p = visitors + 1 + Math.pow(likes * 2 + comments * 4 + collections * 8, g);
        // 获取发布间隔小时
        long creationTime = article.getCreationTime().getTime();
        long nowTime = new Date().getTime();
        double intervalTime = nowTime - creationTime;
        intervalTime = intervalTime < 0 ? 0 : intervalTime / 1000 / 60 / 60;
        // 获取最近评论间隔小时
        double commentsTime = 0;
        LambdaQueryWrapper<Comments> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Comments::getArticleId, article.getId());
        wrapper.orderByDesc(Comments::getCreationTime);
        Comments c = commentsMapper.selectOneByArticleIdOrderByCreationTime(article.getId());
        if (c != null){
            commentsTime = nowTime - c.getCreationTime().getTime();
            commentsTime = commentsTime < 0 ? 0 : commentsTime / 1000 / 60 / 60;
            commentsTime = commentsTime == 0 ? 0 : commentsTime / 2;
        }
        // 计算T值
        double t = Math.pow(intervalTime - commentsTime, g);
        // 返回热度
        return p == 0 ? 0 : p/t;
    }



}
