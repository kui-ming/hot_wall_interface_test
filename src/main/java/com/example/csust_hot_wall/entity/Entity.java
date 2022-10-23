package com.example.csust_hot_wall.entity;

import java.io.Serializable;

public abstract class Entity implements Serializable {

    public Entity stroke(){
        return this;
    }

    public Integer getId(){
        return -1;
    }

    public abstract boolean securityCheck();

    protected boolean isAllNotNull(Object ...objects){
        for (Object object : objects) {
            if (object == null) return false;
        }
        return true;
    }

}
