package com.yjblog.config;

import com.yjblog.config.data.UserSession;
import com.yjblog.exception.Unauthorized;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class AuthResolver implements HandlerMethodArgumentResolver {

    @Override
    // controller 로 넘어 오는 요청이 @RequestAttribute("username") String username 정말로 이 Dto 가 맞아? 물어 보는 메소드
    public boolean supportsParameter(MethodParameter parameter) {

        // MethodParameter 로 보낸 값이 우리가 만든 UserSession 이 맞는가?
        // getParameterType -> UserSession.class
        // getMethod -> UserSession 을 호출 하는 controller 확인
        return parameter.getParameterType().equals(UserSession.class);
    }

    @Override
    // 실제로 controller 로 넘어갈 Dto 의 값을 세팅
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        // AuthInterceptor의 preHandle에서 accessToken을 만들어서 반환한 것을 잡아서 씀
        // NativeWebRequest 를 이용해서 accessToken을 가져옴
        String accessToken = webRequest.getParameter("accessToken");

        if(accessToken == null || accessToken.equals("")){
            throw new Unauthorized();
        }

        UserSession userSession = new UserSession();
        userSession.name = accessToken;
        return userSession;
    }
}
