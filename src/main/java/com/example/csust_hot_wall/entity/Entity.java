package com.example.csust_hot_wall.entity;

import java.io.Serializable;

public abstract class Entity<T> implements Serializable {

    public T stroke(){
        return (T) this;
    }

    public Integer getId(){
        return -1;
    }

    public abstract boolean securityCheck();

    public boolean isAllNotNull(Object ...objects){
        for (Object object : objects) {
            if (object == null) return false;
        }
        return true;
    }

}
