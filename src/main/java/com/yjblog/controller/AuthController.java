package com.yjblog.controller;

import com.yjblog.config.jwt.JwtProvider;
import com.yjblog.request.Login;
import com.yjblog.request.Signup;
import com.yjblog.response.SessionResponse;
import com.yjblog.service.AuthService;
import io.jsonwebtoken.Jwts;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.Date;

import static com.yjblog.config.jwt.JwtProvider.ACCESS_TIME;

/**
 * Spring Security 를 사용할 것이기 때문에 사용하지 않을 예정
 * todo Spring Security 를 사용할 것이기 때문에 사용하지 않을 예정
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtProvider jwtProvider;

//    @PostMapping("/auth/login")
//    public SessionResponse login(@RequestBody @Valid Login login){
//        // json 아이디/비밀번호
//        log.info(">>> login : {}", login);
//
//        // DB에서 조회
//        String accessToken = authService.signing(login);
//
//        // 토큰을 응답
//        return new SessionResponse(accessToken);
//
//    }

//    @PostMapping("/auth/cookieLogin")
//    public ResponseEntity<Object> cookieLogin(@RequestBody @Valid Login login){
//        // json 아이디/비밀번호
//        log.info(">>> login : {}", login);
//
//        // DB에서 조회
//        String accessToken = authService.signing(login);
//
//        //spring 5.0 부터 추가
//        ResponseCookie responseCookie = ResponseCookie.from("SESSION", accessToken)
//                // domain : ex). myService.com 도메인 주소 입력
//                .domain("localhost") // todo 서버 환경에 따른 분리 필요
//                .path("/") // 쿠키 경로 지정
//                .httpOnly(true)
//                .secure(false) //쿠키 연결시 보안인증된 도메인 접속만 (true -> https)
//                .maxAge(Duration.ofDays(30)) // 유효기간 30일
//                .sameSite("strict")
//                .build();
//
//        log.info(">>> cookie={}", responseCookie);
//
//        return ResponseEntity.ok()
//                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
//                .build();
//    }


    @PostMapping("/auth/jwtLogin")
    public SessionResponse jwtLogin(@RequestBody @Valid Login login){

        Long userId = authService.jwtSigning(login);
        // json 아이디/비밀번호
        log.info(">>> login : {}", login);

        Date now = new Date();

        String jws = Jwts.builder()
                .setSubject(String.valueOf(userId))
                .signWith(jwtProvider.jwtSecretKey())
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + ACCESS_TIME))
                .compact();

        return new SessionResponse(jws);
    }

    @PostMapping("/auth/signup")
    public void signup(@RequestBody Signup signup){
        authService.signup(signup);
    }

}
