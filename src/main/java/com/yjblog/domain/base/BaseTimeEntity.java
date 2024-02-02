package com.yjblog.domain.base;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public class BaseTimeEntity {

    @CreatedDate // insert 자동 날짜 저장
    @Column(nullable = false)
    private LocalDateTime createAt;

    @LastModifiedDate // insert, update 자동 날짜 저장
    @Column(nullable = false)
    private LocalDateTime updateAt;
}
