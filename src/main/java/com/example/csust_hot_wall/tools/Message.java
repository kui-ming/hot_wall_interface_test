package com.example.csust_hot_wall.tools;

import org.springframework.cglib.beans.BeanMap;

import java.util.*;

public class Message {

    public static Map send(Code code, String msg, Object obj){
        Map<String,Object> map = new HashMap<>();
        map.put("code",code.getCode());
        map.put("msg",msg);
        if (obj!=null) {
            if (obj instanceof Message.Data || obj instanceof Map || obj instanceof List) {
                obj = dataConversion(obj);
            }
            map.put("data",obj);
        }
        return map;
    }

    public static Map send(){
        return send(Code.SUCCESS);
    }

    public static Map send(Code code){
        return send(code,code.getMsg(),null);
    }

    public static Map send(String msg){
        return send(Code.SUCCESS,msg);
    }

    public static Map send(Text msg){
        return send(Code.SUCCESS,msg.getTxt());
    }

    public static Map send(Code code,String msg){
        return send(code,msg,null);
    }

    public static Map send(String msg, Object data){
        return send(Code.SUCCESS,msg,data);
    }

    public static Map send(Text msg, Object data){
        return send(msg.getTxt(),data);
    }

    public static Map send(Code code, Object data){
        return send(code,code.getMsg(),data);
    }

    public static Map err(){
        return send(Code.ERR);
    }

    public static Map err(Code code){
        return send(code);
    }

    public static Map err(String msg){
        return send(Code.ERR,msg);
    }

    public static Map err(Text msg){
        return send(Code.ERR,msg.getTxt());
    }

    public static Map err(Text msg,String append){
        return send(Code.ERR,msg.getTxt()+append);
    }

    public static Map repeat(){
        return send(Code.REPEAT);
    }

    public static Map repeat(String msg){
        return send(Code.REPEAT,msg);
    }

    public static Map repeat(Text msg){
        return send(Code.REPEAT,msg.getTxt());
    }


    /**
     * 属性转换，将List/Map中的Date属性全部转换为map属性，使得boot正确读取
     * @param data
     * @return
     */
    private static Object dataConversion(Object data){
        if (data == null) return null;
        // 如果等于Date类型
        if (data instanceof Message.Data){
            data = ((Data) data).data; // 转换为Map类型
            data = dataConversion(data); // Map类型肯定还要转换一次，使得其属性中的Date类型都得到转换
            return data; // 返回Map
        }
        // 如果是列表
        else if(data instanceof List) {
            List<Object> objectList = new ArrayList<>();
            for (Object o : ((List) data)) { // 循环每个元素
                // 如果元素为其下的三种类型之一
                if (o instanceof Message.Data || o instanceof Map || o instanceof List) {
                    objectList.add(dataConversion(o)); // 利用递归方法，将其中所有属性都转换后返回
                } else objectList.add(o);
            }
            return objectList; // 返回List
        }
        // 如果是Map类型
        else if (data instanceof Map){
            for (Object o : ((Map) data).keySet()) { // 循环每个key
                Object value = ((Map) data).get(o); // 获取当前key的值
                if (value instanceof Data || value instanceof Map || value instanceof List){
                    ((Map) data).put(o, dataConversion(value)); // 利用递归方法，将值中属性进行转换
                }
            }
            return data;
        }
        else return data;
    }

    public static class Data{
        Map<String,Object> data;

        public static Data crate(String name,Object value){
            Data data = new Data();
            data.put(name,value);
            return data;
        }

        public static Data crate(Object value){
            Data data = new Data();
            data.put(value);
            return data;
        }

        public Data(){
            data = new LinkedHashMap<>();
        }

        public void put(String name,Object value){
            data.put(name,value);
        }
		
		 public void put(Object value){
            if (value == null) return;
            BeanMap beanMap = BeanMap.create(value);
            for (Object key : beanMap.keySet()) {
                data.put(key + "", beanMap.get(key));
            }
        }

        public void remove(String key){
            data.remove(key);
        }

        public Map<String, Object> getMap() {
            return data;
        }

        public Object get(String key){
            return data.get(key);
        }
    }

    public enum Code{
        ERR_ATTRIBUTE_MISS(-3,"属性缺失！"),
        REPEAT(-2,"数据重复！"),
        ERR(-1,"未知错误！"),
        SUCCESS(0,"操作成功！"),
        ;

        private int code;
        private String msg;

        private Code(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        @Override
        public String toString() {
            return "Code{" +
                    "code=" + code +
                    ", msg='" + msg + '\'' +
                    '}';
        }

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }
    }

    public enum Text{
        EXISTED_ERR("数据已存在！"),
        NO_EXISTED_ERR("数据不存在！"),
        PARSE_ERR("数据解析失败！"),
        QUERY_ERR("数据查询失败！"),
        REMOVE_ERR("数据删除失败！"),
        ALTER_ERR("数据修改失败！"),
        ADD_ERR("数据添加失败！"),
        LOGIN_ERR("用户名或密码错误！"),
        USER_REPEAT("用户名已存在！"),
        LOGIN_SUCCESS("登录成功！"),
        ADD_SUCCESS("数据添加成功！"),
        REMOVE_SUCCESS("数据删除成功！"),
        ALTER_SUCCESS("数据修改成功！"),
        QUERY_SUCCESS("数据查询成功！"),
        DATA_EXISTS("数据已经存在！");
        ;

        private String txt;

        private Text(String txt){
            this.txt = txt;
        }

        @Override
        public String toString() {
            return txt;
        }

        public String getTxt() {
            return txt;
        }
    }
}
