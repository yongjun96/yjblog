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
// EntityListeners란 JPA Entity에서 이벤트가 발생할 때마다 특정 로직을 실행시킬 수 있는 어노테이션
// AuditingEntityListener.class : 대상 개체에 업데이트 이벤트가 일어나는 것을 auditing 하도록 설정된 경우에 생성된 날짜, 수정된 날짜, 생성자를 저장할 수 있도록 활성화
@EntityListeners(AuditingEntityListener.class)
// @MappedSupperclass가 선언된 클래스는 Entity가 아니며 직접 생성해서 사용될 일이 없기 때문에 대부분 추상 클래스다.
// public abstract class BaseTimeEntity으로 만드는 것이 원리적으로는 더 맞다.
// 결론적으로 부모 클래스를 상속받는 자식 클래스에게 매핑 정보만을 제공하고 싶을 때 사용하는 어노테이션
@MappedSuperclass
public class BaseTimeEntity {

    @CreatedDate // insert 자동 날짜 저장
    @Column(nullable = false)
    private LocalDateTime createAt;

    @LastModifiedDate // insert, update 자동 날짜 저장
    @Column(nullable = false)
    private LocalDateTime updateAt;
}
