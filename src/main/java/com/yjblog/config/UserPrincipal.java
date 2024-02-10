package com.yjblog.config;


import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public class UserPrincipal extends User {

    private final Long userId;

    // role : 역할 -> 관리자, 사용자, 매니저
    // authority : 권한 -> 글쓰기, 읽기, 사용자 정지 시키기

    public UserPrincipal(com.yjblog.domain.User user){
        super(user.getEmail(), user.getPassword(), List.of(
                new SimpleGrantedAuthority("ROLE_ADMIN"),
                new SimpleGrantedAuthority("WRITE")));
        this.userId = user.getId();
    }

    public Long getUserId() {
        return userId;
    }
}
