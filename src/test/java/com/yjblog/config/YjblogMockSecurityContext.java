package com.yjblog.config;

import com.yjblog.domain.Role;
import com.yjblog.domain.User;
import com.yjblog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
public class YjblogMockSecurityContext implements WithSecurityContextFactory<YjblogMockUser> {

    private final BCryptPasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    // 회원으로 들어갈 객체들을 구현
    @Override
    public SecurityContext createSecurityContext(YjblogMockUser annotation) {
        var user = User.builder()
                .email(annotation.email())
                .name(annotation.name())
                .password(passwordEncoder.encode(annotation.password()))
                .role(annotation.role())
                .build();

        userRepository.save(user);

        var principal = new UserPrincipal(user);
        var role = new SimpleGrantedAuthority("ROLE_"+user.getRole());
        var authenticationToken = new UsernamePasswordAuthenticationToken(principal, user.getPassword(), List.of(role));

        var context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authenticationToken);

        return context;
    }
}
