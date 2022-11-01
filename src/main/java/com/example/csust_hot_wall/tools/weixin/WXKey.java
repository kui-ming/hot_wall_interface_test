package com.example.csust_hot_wall.tools.weixin;

import com.example.csust_hot_wall.configuration.YAMLPropertySourceFactory;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@Component
@PropertySource(value = "classpath:/wx_key.yml", factory = YAMLPropertySourceFactory.class)
@ConfigurationProperties(value = "wx")
public class WXKey {
    private String id;
    private String secret;
}
