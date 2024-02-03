package com.yjblog.service;

import com.yjblog.domain.User;
import com.yjblog.exception.InvalidSigningInformation;
import com.yjblog.repository.UserRepository;
import com.yjblog.request.Login;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    public User signing(Login login){

        User user = userRepository.findByEmailAndPassword(login.getEmail(), login.getPassword())
                .orElseThrow(() -> new InvalidSigningInformation());

        user.addSession();

        return user;
    }
}
