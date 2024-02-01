package com.yjblog.config;

import com.yjblog.exception.Unauthorized;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    //preHandle → 핸들러 가기 전에 무조건 실행 (controller 가기 전에 실행)
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info(">> preHandle");

        // getParameter 는 null 값이 있을 수 있음
        String accessToken = request.getParameter("accessToken");
        if(accessToken != null && accessToken.equals("yyongjun")){
            return true;
        }
        // 실패의 경우
        throw new Unauthorized();
    }

    @Override
    // postHandle → 컨트롤러가 실행되고 나서 실행 (controller 반환이 다 끝난 후)
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info(">> postHandle");
    }

    @Override
    // afterCompletion → 뷰까지 보내고 난 다음 실행 (view 반환이 다 끝나고 난 후)
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info(">> afterCompletion");
    }
}
