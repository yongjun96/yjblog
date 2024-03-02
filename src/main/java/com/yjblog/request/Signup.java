package com.yjblog.request;

import com.yjblog.domain.Role;
import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@ToString
public class Signup {

    private String name;
    private String password;
    private String email;
    private Role role;

    @Builder
    public Signup(String name, String password, String email, Role role) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.role = role;
    }
}
