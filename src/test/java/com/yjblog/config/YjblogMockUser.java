package com.yjblog.config;

import com.yjblog.domain.Role;
import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * todo @WithMockUser(username = "yongjun96@gmail.com", roles = {"ADMIN"}) 를 테스트가 인식하지 못하는데, 그걸 해결하기 위한 어노테이션 제작
 */

@Retention(RetentionPolicy.RUNTIME) // 실시간으로 인식할 수 있도록 런타임 지정
@WithSecurityContext(factory = YjblogMockSecurityContext.class) // 이 안에서 회윈가입을 미리 진행을 하고 UserPrincipal 를 spring security context 안에 미리 넣어 줄 수 있다.
public @interface YjblogMockUser {

    String name() default "yongJun";

    String email() default "yongjun96@gmail.com";

    String password() default "1234";

    Role role() default Role.ADMIN;
}
