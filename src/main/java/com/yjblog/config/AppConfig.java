package com.yjblog.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import java.util.List;

@Data
//@Configuration
@ConfigurationProperties(prefix = "yong-jun-blog") // application.yml 의 yongJunBlog: 의 세팅 환경을 추가
public class AppConfig {

    public String testData;

    public List<String> array;

    public Info info;

    @Data
    public static class Info{
        public String name;
        public String firstName;
        public String post;
    }
}
