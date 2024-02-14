package com.yjblog.annotation;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = MockUserFactory.class)
public @interface CustomWithMockUser {

    String username() default "yongjun96@gmail.com";

    String password() default  "1234";

    int level() default 5;

    String mobileNumber = "010-1234-5678";



}
