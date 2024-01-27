package com.yjblog.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PostCreate {

    @NotBlank(message = "제목이 없습니다.")
    private String title;

    @NotBlank(message = "내용이 없습니다.")
    private String content;

    // 빌더의 장점
    // 1. 가독성이 좋다
    // 2. 값 생성의 유연함.
    // 3. 필요한 값만 받을 수 있다. -> 오버로딩 가능한 조건 찾아 보기
    // 4. 객체의 불변성
    @Builder
    public PostCreate(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
