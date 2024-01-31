package com.yjblog.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * "code": "400",
 * "message": "잘못된 요청입니다."
 * "validation": {
 *     "title": "제목이 없습니다."
 *     "content": "내용이 없습니다."
 * }
 *
 */

@Getter
// 비어 있지 않은 객체만 json으로 보냄
// 잘 사용하지 않음
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class ErrorResponse {

    //회사 마다 팀마다 규칙이 다름
    private final String code;
    private final String message;
    private final Map<String, String> validation;
    //private final T data;

    @Builder
    public ErrorResponse(String code, String message, Map<String, String> validation) {
        this.code = code;
        this.message = message;
        this.validation = validation != null ? validation : new HashMap<>();
    }

    public void addValidation(String fieldName, String errorMessage){
        this.validation.put(fieldName, errorMessage);
    }
}
