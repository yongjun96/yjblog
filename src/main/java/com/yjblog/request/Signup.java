package com.yjblog.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Signup {

    private String name;
    private String password;
    private String email;

    @Builder
    public Signup(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
    }
}
