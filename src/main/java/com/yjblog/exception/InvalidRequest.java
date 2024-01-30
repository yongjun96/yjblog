package com.yjblog.exception;

import lombok.Getter;

/**
 * httpStatus -> 400
 */
@Getter
public class InvalidRequest extends GlobalException {

    private static final String MESSAGE = "잘못된 요청입니다.";

    public InvalidRequest() {
        super(MESSAGE);
    }

    public InvalidRequest(String fieldName, String message){
        super(MESSAGE);
        addValidation(fieldName, message);
    }

    public InvalidRequest(Throwable cause) {
        super(MESSAGE, cause);
    }

    @Override
    public String getStatusCode(){
        return "400";
    }
}
