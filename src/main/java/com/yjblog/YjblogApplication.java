package com.yjblog;

import com.yjblog.config.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableConfigurationProperties(AppConfig.class) // 부팅 시에 AppConfig.class 를 주입한다.
@EnableJpaAuditing
@SpringBootApplication
public class YjblogApplication {

    public static void main(String[] args) {
        SpringApplication.run(YjblogApplication.class, args);
    }

}
