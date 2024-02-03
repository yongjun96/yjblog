package com.yjblog.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accessToken;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Builder
    public Session(User user) {
        //랜덤으로 UUID를 만들어 준다.
        this.accessToken = UUID.randomUUID().toString();
        this.user = user;
    }
}
