package com.example.csust_hot_wall.tools.weixin;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Component
public class OpenIdManage {
    @Autowired
    WXKey wxKey;

    public JSONObject getOpenId(String code){
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid="
                + wxKey.getId() +
                "&secret="
                + wxKey.getSecret() +
                "&js_code="
                + code +
                "&grant_type=authorization_code";
        WebClient webClient = WebClient.create();
        Mono<String> mono = webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class);
        String result = mono.block();
        return JSONObject.parseObject(result);
    }
}
