package com.yjblog.service;

import com.yjblog.domain.Session;
import com.yjblog.domain.User;
import com.yjblog.exception.InvalidSigningInformation;
import com.yjblog.repository.UserRepository;
import com.yjblog.request.Login;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    @Transactional
    public String signing(Login login){

        User user = userRepository.findByEmailAndPassword(login.getEmail(), login.getPassword())
                .orElseThrow(() -> new InvalidSigningInformation());

        Session session = user.addSession();

        return session.getAccessToken();
    }
}
