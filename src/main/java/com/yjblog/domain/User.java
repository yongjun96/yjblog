package com.yjblog.domain;

import com.yjblog.domain.base.BaseTimeEntity;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String name;

    private String email;

    private String password;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Post> posts =new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Role role;

//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
//    private List<Session> sessions = new ArrayList<>();

    @Builder
    public User(String name, String email, String password, Role role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    /**
     * Spring Security 를 사용할 것이기 때문에 사용하지 않을 예정
     * todo Spring Security 를 사용할 것이기 때문에 사용하지 않을 예정
     */
//    public Session addSession() {
//         Session session = Session.builder()
//                 .user(this)
//                 .build();
//
//         sessions.add(session);
//
//         return session;
//    }
}
