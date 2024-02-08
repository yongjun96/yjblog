package com.yjblog.service;

import com.yjblog.crypto.PasswordEncoder;
import com.yjblog.domain.Session;
import com.yjblog.domain.User;
import com.yjblog.exception.AlreadExistsEmailException;
import com.yjblog.exception.InvalidSigningInformation;
import com.yjblog.repository.UserRepository;
import com.yjblog.request.Login;
import com.yjblog.request.Signup;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

//    @Transactional
//    public String signing(Login login){
//
//        User user = userRepository.findByEmailAndPassword(login.getEmail(), login.getPassword())
//                .orElseThrow(() -> new InvalidSigningInformation());
//
//        Session session = user.addSession();
//
//        return session.getAccessToken();
//    }

    @Transactional
    public Long jwtSigning(Login login){

//        User user = userRepository.findByEmailAndPassword(login.getEmail(), login.getPassword())
//                .orElseThrow(() -> new InvalidSigningInformation());

        User findEmailUser = userRepository.findByEmail(login.getEmail())
                .orElseThrow(() -> new InvalidSigningInformation());

        boolean matches = passwordEncoder.matches(login.getPassword(), findEmailUser.getPassword());
        if(!matches){
            throw new InvalidSigningInformation();
        }

        return findEmailUser.getId();
    }

    public User signup(Signup signup) {

        Optional<User> optionalUser = userRepository.findByEmail(signup.getEmail());

        if(optionalUser.isPresent()){
            throw new AlreadExistsEmailException();
        }

        String password = passwordEncoder.passwordEncoder(signup.getPassword());

        User user = User.builder()
                .name(signup.getName())
                .password(password)
                .email(signup.getEmail())
                .build();

        return userRepository.save(user);
    }
}
