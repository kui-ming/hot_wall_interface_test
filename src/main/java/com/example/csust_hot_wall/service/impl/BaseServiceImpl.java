package com.example.csust_hot_wall.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.csust_hot_wall.entity.Entity;
import com.example.csust_hot_wall.mapper.MyBaseMapper;
import com.example.csust_hot_wall.service.BaseService;
import com.github.jeffreyning.mybatisplus.service.MppServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class BaseServiceImpl<M extends MyBaseMapper<T>,T> extends MppServiceImpl<MyBaseMapper<T>,T> implements BaseService<T> {

    @Autowired
    M mapper;

    @Override
    public T getById(Serializable id) {
        T t = mapper.selectById(id);
        if(t!=null) redundancy(t);
        return t;
    }

    @Override
    public <E extends IPage<T>> E page(E page, Wrapper<T> queryWrapper) {
        E e = mapper.selectPage(page, queryWrapper);
        List<T> records = page.getRecords();
        if (redundancy(records) != null){
            page.setRecords(records);
        }
        return e;
    }

    @Override
    public List<T> list(Wrapper<T> queryWrapper) {
        List<T> tList = mapper.selectList(queryWrapper);
        redundancy(tList);
        return tList;
    }

    /**
     * 为列表中的实体类的冗余字段赋值
     * @param tList
     * @return
     */
    protected List<T> redundancy(List<T> tList){
        for (T t : tList) {
            if (redundancy(t) == null) {
                return null;
            }
        }
        return tList;
    }

    /**
     * 为实体类的冗余字段赋值
     * @param t
     * @return
     */
    protected T redundancy(T t){
        return null;
    }


    static abstract class SearchKey<T>{
        String keyName;

        protected SearchKey(String keyName){
            this.keyName = keyName;
        }

        public abstract LambdaQueryWrapper<T> search(String value);
    }

    static class Searcher<T extends Entity>{
        List<SearchKey> searchList = new ArrayList<>();

        public LambdaQueryWrapper<T> getWrapper(String key,String value){
            LambdaQueryWrapper<T> wrapper = new LambdaQueryWrapper<>();
            if ("".equals(key)) return wrapper;
            for (SearchKey searchKey : searchList) {
                if (searchKey.keyName.equals(key)){
                    wrapper = searchKey.search(value);
                    return wrapper;
                }
            }
            return wrapper;
        }

        public void add(SearchKey searchKey){
            searchList.add(searchKey);
        }
    }


}
