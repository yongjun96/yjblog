package com.yjblog.config;

import com.yjblog.exception.Unauthorized;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


/**
 * Spring Security 를 사용할 것이기 때문에 사용하지 않을 예정
 * todo Spring Security 를 사용할 것이기 때문에 사용하지 않을 예정
 */

@Slf4j
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    //preHandle → 핸들러 가기 전에 무조건 실행 (controller 가기 전에 실행)
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info(">> preHandle");

        // getParameter 는 null 값이 있을 수 있음
        String accessToken = request.getParameter("accessToken");
        if(accessToken != null && !accessToken.equals("")){
            // HttpServletRequest 의 setAttribute 를 통해서 key, value로 값을 보낼 수 있음
            // controller 에서 받을 때는 @RequestAttribute("username") String username 방식으로 파라메터로 받기 가능
            request.setAttribute("username", accessToken);
            return true;
        }
        // 실패의 경우 (만들어 놓은 Custom exception을 반환)
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
