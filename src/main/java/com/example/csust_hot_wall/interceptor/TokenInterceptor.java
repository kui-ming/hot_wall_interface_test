package com.example.csust_hot_wall.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.example.csust_hot_wall.controller.BaseController;
import com.example.csust_hot_wall.tools.JwtUtils;
import com.example.csust_hot_wall.tools.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TokenInterceptor implements HandlerInterceptor {

    Map<String, List<String>> rolePermissionControlList = new HashMap<>();

    public TokenInterceptor(){
        ArrayList<String> user = new ArrayList<>(); // 用户角色
        // 被拒绝的路由地址
        user.add("/category/add"); // 类别的添加
        user.add("/category/update"); // 类别的更新
        user.add("/category/del");  // 类别的删除
        user.add("/user/add"); // 用户的添加
        user.add("/user/del"); // 用户的删除
        // 添加到控制列表
        rolePermissionControlList.put("user",user);
    }

    /**
     * 前往控制层之前
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if ("OPTIONS".equals(request.getMethod())){
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }
        // 获取token
        String jwt = request.getHeader("user-token");
        // 验证token
        if (JwtUtils.checkJWT(jwt)){
            Map<String, Object> payload = JwtUtils.getPayload(jwt);
            if (payload != null){
                String open_id = (String) payload.get("open_id");
                Integer user_id = (Integer) payload.get("user_id");
                String power = (String) payload.get("power");
                request.setAttribute("user_id",user_id);
                request.setAttribute("open_id",open_id);
                request.setAttribute("power",power);
                return permissionControl(request, response);
            }
        }
        JSONObject jsonObject = new JSONObject(Message.err("token verify fail!"));
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().append(jsonObject.toJSONString());
        return false;
    }

    private boolean permissionControl(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String power = (String) request.getAttribute("power");
        if (power != null){
            List<String> routeList = rolePermissionControlList.get(power);
            if (routeList != null) {
                for (String route : routeList) {
                    if (request.getRequestURI().equals(route)){
                        JSONObject jsonObject = new JSONObject(Message.err(Message.Text.NO_POWER_ERR));
                        response.setContentType("text/html;charset=UTF-8");
                        response.getWriter().append(jsonObject.toJSONString());
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
