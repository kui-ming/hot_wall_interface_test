package com.example.csust_hot_wall.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.csust_hot_wall.entity.Entity;
import com.example.csust_hot_wall.service.BaseService;
import com.example.csust_hot_wall.tools.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@CrossOrigin
public abstract class BaseController<T extends Entity,S extends BaseService<T>> {

    @Autowired
    S service;

    @Autowired
    HttpServletRequest request;

    @PostMapping("/add")
    public Map add(@RequestBody T t){
        if (! t.securityCheck()) return Message.err(Message.Code.ERR_ATTRIBUTE_MISS);
        t = (T) t.stroke();
        if (service.save(t)) {
            return Message.send(Message.Text.ADD_SUCCESS);
        }else {
            return Message.err(Message.Text.ADD_ERR);
        }
    }

    @PutMapping("/update")
    public Map alter(@RequestBody T t){
        if (t.getId() == null) return Message.err(Message.Code.ERR_ATTRIBUTE_MISS);
        if (service.updateById(t)) {
            return Message.send(Message.Text.ALTER_SUCCESS);
        }else {
            return Message.err(Message.Text.ALTER_ERR);
        }
    }

    @DeleteMapping("/del")
    public Map delete(@RequestBody Map<String,Integer[]> map){
        Integer[] ids = map.getOrDefault("ids",new Integer[]{});
        boolean isOk = false;
        if (ids.length > 1){
            isOk = service.removeByIds(Arrays.asList(ids));
        }else {
            Integer id = ids[0];
            isOk = service.removeById(id);
        }
        if (isOk) {
            return Message.send(Message.Text.REMOVE_SUCCESS);
        }else {
            return Message.err(Message.Text.REMOVE_ERR);
        }
    }

    @GetMapping("info")
    @ResponseBody
    public Map queryByid(@RequestParam("id") Integer id ){
        T t = service.getById(id);
        if (t==null) return Message.err(Message.Text.QUERY_ERR,"????????????????????????");
        return Message.send(Message.Text.QUERY_SUCCESS,t);
    }


    @GetMapping("query")
    public Map query(@RequestParam(value = "page", required = false) Integer page,
                    @RequestParam(value = "size", required = false) Integer size,
                    @RequestParam(value = "k", defaultValue = "") String key,
                    @RequestParam(value = "v", defaultValue = "") String value){
        LambdaQueryWrapper<T> queryWrapper = service.searchToWrapper(key, value);
        return page(page, size, queryWrapper);
    }

    public Map page(Integer page,Integer size,LambdaQueryWrapper<T> queryWrapper){
        if (page != null){
            size = size != null ? size : 10; // ????????????
            size = size < 0 ? 0 : size;
            Page<T> tPage = new Page<>(page,size);
            service.page(tPage,queryWrapper);
            Message.Data data = new Message.Data();
            data.put("list", tPage.getRecords());
            data.put("current",tPage.getCurrent());
            data.put("pages",tPage.getPages());
            data.put("total",tPage.getTotal());
            data.put("size",tPage.getSize());
            data.put("has_next",tPage.hasNext());
            data.put("has_previous",tPage.hasPrevious());
            return Message.send(Message.Text.QUERY_SUCCESS,data);
        }else {
            List<T> list = service.list(queryWrapper);
            return Message.send(Message.Text.QUERY_SUCCESS,list);
        }
    }

    protected RequestHandle getRequest(){
        if (RequestHandle.request == null) {
            RequestHandle.request = request;
        }
        return RequestHandle.instance;
    }

    static class RequestHandle{

        private static HttpServletRequest request = null;

        private static RequestHandle instance = new RequestHandle();

        public Integer getUserId(){
            return (Integer) request.getAttribute("user_id");
        }

        public String getPower(){
            return (String) request.getAttribute("power");
        }

        public String getOpenId(){
            return (String) request.getAttribute("open_id");
        }

    }

}
