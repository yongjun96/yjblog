package com.yjblog.config.jwt;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;

@Component
public class JwtProvider {

    public static final long ACCESS_TIME = 6 * 60 * 60 * 1000L;               // 액세스 토큰 6시간
    public static final long REFRESH_TIME = 7 * 24 * 60 * 60 * 1000L;     // 리프레시 토큰 7일

    // application custom secretKey
    @Value("${custom.jwt.secretKey}")
    private String secretKey;

    // secretKeyPlain Base64 변환
    public SecretKey jwtSecretKey() {
        String keyBase64Encoded = Base64.getEncoder().encodeToString(secretKey.getBytes());
        return Keys.hmacShaKeyFor(keyBase64Encoded.getBytes());
    }
}
