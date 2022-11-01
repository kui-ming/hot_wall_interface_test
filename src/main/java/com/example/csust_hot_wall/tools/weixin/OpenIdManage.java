package com.example.csust_hot_wall.tools.weixin;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Component
public class OpenIdManage {

    private String AppID = "wx776ecb8ff83988c9";
    private String AppSecret = "d2a02bd1b9405d3c0c59c00073f2f4ea";

    public JSONObject getOpenId(String code){
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid="
                + AppID +
                "&secret="
                + AppSecret +
                "&js_code="
                + code +
                "&grant_type=authorization_code";
        WebClient webClient = WebClient.create();
        Mono<String> mono = webClient.get()
                .uri(url)
                .attribute("appid", AppID)
                .attribute("secret", AppSecret)
                .attribute("js_code", code)
                .attribute("grant_type", "authorization_code")
                .retrieve()
                .bodyToMono(String.class);
        String result = mono.block();
        System.out.println();
        return JSONObject.parseObject(result);
    }
}
