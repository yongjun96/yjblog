package com.yjblog.crypto;

import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.stereotype.Component;

//@Component
public class PasswordEncoderSCrypt {

    private static final SCryptPasswordEncoder encoder =
            new SCryptPasswordEncoder(16, 8, 1, 32, 64);

    public String passwordEncoder(String password){
        return encoder.encode(password);
    }

    public boolean matches(String rawPassword, String encodePassword){
        return encoder.matches(rawPassword, encodePassword);
    }
}
