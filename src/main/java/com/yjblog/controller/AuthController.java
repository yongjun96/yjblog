package com.yjblog.controller;

import com.yjblog.domain.User;
import com.yjblog.exception.InvalidRequest;
import com.yjblog.exception.InvalidSigningInformation;
import com.yjblog.repository.UserRepository;
import com.yjblog.request.Login;
import com.yjblog.response.PostResponse;
import com.yjblog.response.SessionResponse;
import com.yjblog.service.AuthService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Base64;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    private static final String KEY = Base64.getEncoder().encodeToString(Jwts.SIG.HS256.key().build().getEncoded());

    @PostMapping("/auth/login")
    public SessionResponse login(@RequestBody @Valid Login login){
        // json 아이디/비밀번호
        log.info(">>> login : {}", login);

        // DB에서 조회
        String accessToken = authService.signing(login);

        // 토큰을 응답
        return new SessionResponse(accessToken);

    }

    @PostMapping("/auth/cookieLogin")
    public ResponseEntity<Object> cookieLogin(@RequestBody @Valid Login login){
        // json 아이디/비밀번호
        log.info(">>> login : {}", login);

        // DB에서 조회
        String accessToken = authService.signing(login);

        //spring 5.0 부터 추가 
        ResponseCookie responseCookie = ResponseCookie.from("SESSION", accessToken)
                // domain : ex). myService.com 도메인 주소 입력
                .domain("localhost") // todo 서버 환경에 따른 분리 필요
                .path("/") // 쿠키 경로 지정
                .httpOnly(true)
                .secure(false) //쿠키 연결시 보안인증된 도메인 접속만 (true -> https)
                .maxAge(Duration.ofDays(30)) // 유효기간 30일
                .sameSite("strict")
                .build();

        log.info(">>> cookie={}", responseCookie);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .build();
    }


    @PostMapping("/auth/jwtLogin")
    public SessionResponse jwtLogin(@RequestBody @Valid Login login){
        // json 아이디/비밀번호
        log.info(">>> login : {}", login);

        // 알고리즘 HS256 세팅 String을 바이트로 디코딩
        SecretKey key = Jwts.SIG.HS256.key().build();

        String jws = Jwts.builder().subject("Joe").signWith(key).compact();

        return new SessionResponse(jws);
    }

}
