package com.yjblog.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Spring Security 를 사용할 것이기 때문에 사용하지 않을 예정
 * todo Spring Security 를 사용할 것이기 때문에 사용하지 않을 예정
 */

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accessToken;

//    @ManyToOne(fetch = FetchType.LAZY)
//    private User user;

//    @Builder
//    public Session(User user) {
//        //랜덤으로 UUID를 만들어 준다.
//        this.accessToken = UUID.randomUUID().toString();
//        this.user = user;
//    }
}
