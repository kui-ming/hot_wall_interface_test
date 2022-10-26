package com.example.csust_hot_wall.service;

import com.example.csust_hot_wall.entity.Article;

import java.util.List;

public interface RankingListService {

    public void updateHotRanking();

    public void updateNewRanking();

    public List<Article> getHotRanking();

    public List<Article> getNewRanking();

    public List<Article> getHotRanking(int top);

    public List<Article> getNewRanking(int top);

    public List<Article> randomHotRanking(int num);

    public List<Article> randomNewRanking(int num);



}
