package com.yjblog.response;

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
public class ErrorResponse {

    //회사 마다 팀마다 규칙이 다름
    private final String code;
    private final String message;
    private final Map<String, String> validation = new HashMap<>();

    @Builder
    public ErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public void addValidation(String fieldName, String errorMessage){
        this.validation.put(fieldName, errorMessage);
    }
}
