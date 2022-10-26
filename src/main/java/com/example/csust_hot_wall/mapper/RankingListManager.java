package com.example.csust_hot_wall.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.csust_hot_wall.entity.HotValue;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RankingListManager {
    // 每日新榜

    private int newListMaxLength = 30;

    private ArrayList<HotValue> newList = new ArrayList<>(newListMaxLength);

    // 热度榜

    private int hotListMaxLength = 20;

    private ArrayList<HotValue> hotList = new ArrayList<>(hotListMaxLength);

    /**
     * 获取热榜前top位文章
     * @param top
     * @return
     */
    public List<HotValue> getHot(int top){
        if (top < 1 || top > hotList.size()) return hotList;
        return hotList.subList(0,top);
    }

    /**
     * 获取热榜
     * @return
     */
    public List<HotValue> getHot(){
        return hotList;
    }

    /**
     * 获取新榜前top位文章
     * @param top
     * @return
     */
    public List<HotValue> getNew(int top){
        if (top < 1 || top > newList.size()) return newList;
        return newList.subList(0,top);
    }

    /**
     * 获取新榜
     * @return
     */
    public List<HotValue> getNew(){
        return newList;
    }

    /**
     * 按热度添加文章到热榜
     * @param hotValue
     * @return
     */
    public boolean pushHot(HotValue hotValue){
       return push(hotList,hotValue,hotListMaxLength);
    }

    /**
     * 按热度添加文章到新榜
     * @param hotValue
     * @return
     */
    public boolean pushNew(HotValue hotValue){
        return push(newList,hotValue,newListMaxLength);
    }


    /**
     * 按热度添加文章到指定榜单
     * @param list
     * @param hotValue
     * @param maxSize
     * @return
     */
    private boolean push(ArrayList<HotValue> list,HotValue hotValue,int maxSize){
        int lastIndex = list.size()-1;
        if (lastIndex == maxSize-1){
            if (list.get(lastIndex).getScore() >= hotValue.getScore()) return false;
        }
        for (int i = 0; i < list.size(); i++) {
            HotValue value = list.get(i);
            if (hotValue.getScore() > value.getScore()){
                list.add(i,hotValue);
                fixed(list);
                return true;
            }
            if (hotValue.getScore().equals(value.getScore()) && hotValue.getArticleId().equals(value.getArticleId())){
                fixed(list);
                return true;
            }
        }
        if (list.size() < maxSize){
            list.add(hotValue);
            return true;
        }
        return false;
    }

    /**
     * 榜单超出最大长度时进行截取
     * @param list
     */
    private void fixed(ArrayList<HotValue> list){
        if (list == hotList && list.size() > hotListMaxLength)
            hotList.subList(hotListMaxLength,list.size()).clear();
        else if (list == newList && list.size() > newListMaxLength)
            newList.subList(newListMaxLength,list.size()).clear();
    }

    public int getNewListMaxLength() {
        return newListMaxLength;
    }

    public void setNewListMaxLength(int newListMaxLength) {
        this.newListMaxLength = newListMaxLength;
    }

    public int getHotListMaxLength() {
        return hotListMaxLength;
    }

    public void setHotListMaxLength(int hotListMaxLength) {
        this.hotListMaxLength = hotListMaxLength;
    }
}
