package com.yjblog.service;

import com.yjblog.domain.Role;
import com.yjblog.domain.User;
import com.yjblog.exception.AlreadExistsEmailException;
import com.yjblog.repository.UserRepository;
import com.yjblog.request.Signup;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JoinService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public void joinProcess(Signup signup){

        User user = User.builder()
                .email(signup.getEmail())
                .password(passwordEncoder.encode(signup.getPassword()))
                .name(signup.getName())
                .role(Role.ADMIN)
                .build();

        Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());
        if(optionalUser.isPresent()){
            throw new AlreadExistsEmailException();
        }

        userRepository.save(user);
    }
}
