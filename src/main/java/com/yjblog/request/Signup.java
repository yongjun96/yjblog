package com.yjblog.request;

import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@ToString
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
