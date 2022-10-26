package com.example.csust_hot_wall.configuration;

import com.example.csust_hot_wall.entity.HotValue;
import com.example.csust_hot_wall.mapper.RankingListManager;
import com.example.csust_hot_wall.service.RankingListService;
import com.example.csust_hot_wall.tools.CalendarUtil;
import com.example.csust_hot_wall.tools.TimeUtil;
import com.example.csust_hot_wall.tools.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;

@Component
@EnableScheduling // 开启定时任务
@EnableAsync // 开启异步多线程
public class MultithreadingScheduleTask {

    @Autowired
    RankingListService rankingListService;

    @Async
    @Scheduled(fixedDelay = 1000 * 60 * 60 * 2) // 每两小时更新一次
    public void updateHotRanking(){
        rankingListService.updateHotRanking();
    }

    @Async
    @Scheduled(fixedDelay = 1000 * 60 * 60) // 每小时更新一次
    public void updateNewRanking(){
        rankingListService.updateNewRanking();
    }
}
