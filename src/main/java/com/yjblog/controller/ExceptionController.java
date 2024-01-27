package com.yjblog.controller;

import com.yjblog.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
//@RestController // @RestController에 포함된 @ResponseBody 가 없기 때문에 http응답이 안됨.
public class ExceptionController {

    @ResponseStatus(HttpStatus.BAD_REQUEST) // 400
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody // 모델을 뷰로 랜더링 하지 않고 반환된 객체를 응답 본문(json으로 만들도록)에 쓰도록 함
    public ErrorResponse methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {

        ErrorResponse response = new ErrorResponse("400", "잘못된 요청입니다.");

        for(FieldError fieldError : e.getFieldErrors()){
            response.addValidation(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return response;
    }
}
