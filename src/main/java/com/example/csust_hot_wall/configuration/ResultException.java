package com.example.csust_hot_wall.configuration;

public class ResultException extends RuntimeException {
    private Integer code;
    private String message;
    private Object data;

    public ResultException(Integer code, String message, Object data) {
        super(message);
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ResultException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
        this.data = null;
    }

    public ResultException(String message) {
        super(message);
        this.code = -1;
        this.message = message;
        this.data = null;
    }

}
