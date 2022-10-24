package com.example.csust_hot_wall.configuration;

import com.example.csust_hot_wall.tools.Message;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Map;

/**
 * 全局控制器异常管理
 */
@RestControllerAdvice
public class ControllerExceptionAdvice {

    @ExceptionHandler(value = Exception.class)
    public Map errHandler(Exception e){
        if (e.getClass() == DuplicateKeyException.class) {
            return Message.err("主属性完整性不允许重复！");
        }
        return Message.err(e.getMessage());
    }

    @ExceptionHandler(value = ResultException.class)
    public Map resultExceptionHandler(ResultException re){
        return Message.err(re.getMessage());
    }
}
