package com.example.csust_hot_wall.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.csust_hot_wall.configuration.ResultException;
import com.example.csust_hot_wall.entity.Entity;
import com.example.csust_hot_wall.mapper.MyBaseMapper;
import com.example.csust_hot_wall.service.BaseService;
import com.github.jeffreyning.mybatisplus.service.MppServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;


public class BaseServiceImpl<M extends MyBaseMapper<T>,T extends Entity> extends MppServiceImpl<MyBaseMapper<T>,T> implements BaseService<T> {

    @Autowired
    M mapper;

    // 查询器
    Searcher<T> searcher;


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

    /**
     * 向控制层暴露的关键字查询方法
     * @param key
     * @param value
     * @return
     */
    @Override
    public LambdaQueryWrapper<T> searchToWrapper(String key, String value) {
        if (searcher == null){
            synchronized (BaseServiceImpl.class){
                if (searcher == null){
                    searcher = new Searcher<>(); // 第一次进行关键词搜索时创建搜索器
                    Class<?>[] classes = this.getClass().getDeclaredClasses(); // 获取该类的所有内部类
                    for (Class<?> aClass : classes) {
                        // 如果该内部类的父类为SearchKey，证明此类的作用为关键字查询
                        if (aClass.getSuperclass() == SearchKey.class){
                            Constructor<SearchKey<T>> constructor = null; // 准备获取该关键字类的默认构造方法
                            SearchKey<T> object = null; // 准备关键字类的对象
                            try {
                                constructor = (Constructor<SearchKey<T>>) aClass.getDeclaredConstructor(); //  获取该类无参构造方法
                                constructor.setAccessible(true); // 禁用限定符检查，提高反射性能接近20倍
                                object = (SearchKey<T>) constructor.newInstance();
                            } catch (NoSuchMethodException e) {
                                e.printStackTrace();
                                throw new ResultException("此类关键字查询模块异常！");
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                                throw new ResultException("此类关键字查询模块具有非法访问异常！");
                            } catch (InstantiationException e) {
                                e.printStackTrace();
                                throw new ResultException("此类关键字查询模块实例化异常！");
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                                throw new ResultException("此类关键字查询模块实例化调用时异常！");
                            }
                            object.setBoss(this);
                            searcher.add(object);
                        }
                    }
                }
            }
        }
        return searcher.getWrapper(key, value);
    }

    /**
     * 查询关键字父类
     * 定义这个父类第一是统一关键字子类的实现规则，第二是在通过反射生成关键字对象时，区分其他无关内部类
     * @param <T>
     */
    static abstract class SearchKey<T>{

        protected String keyName;

        protected BaseServiceImpl boss;

        protected SearchKey(String keyName){
            this.keyName = keyName;
        }

        public abstract void search(LambdaQueryWrapper<T> wrapper, String value);

        protected void setBoss(BaseServiceImpl boss){
            this.boss = boss;
        }

        protected BaseServiceImpl getBoss(){
            return this.boss;
        }
    }

    /**
     * 查询器，通过查询器与SearchKey子类实现关键字搜索
     * @param <T>
     */
    static class Searcher<T extends Entity>{
        List<SearchKey<T>> searchList = new ArrayList<>();

        public LambdaQueryWrapper<T> getWrapper(String key,String value){
            for (SearchKey<T> searchKey : searchList) {
                if (searchKey.keyName.equals(key)){
                    LambdaQueryWrapper<T> wrapper = new LambdaQueryWrapper<>();
                    searchKey.search(wrapper,value);
                    return wrapper;
                }
            }
            if ("".equals(key)) return new LambdaQueryWrapper<T>();
            throw new ResultException("关键字查询失败！");
            // return wrapper;
        }

        public void add(SearchKey<T> searchKey){
            searchList.add(searchKey);
        }
    }


}
