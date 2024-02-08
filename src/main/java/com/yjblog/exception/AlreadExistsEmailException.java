package com.yjblog.exception;

public class AlreadExistsEmailException extends GlobalException {

    private static final String MESSAGE = "이미 존재하는 이메일입니다.";

    public AlreadExistsEmailException() {
        super(MESSAGE);
    }

    public AlreadExistsEmailException(String fieldName, String message){
        super(MESSAGE);
        addValidation(fieldName, message);
    }

    public AlreadExistsEmailException(Throwable cause) {
        super(MESSAGE, cause);
    }

    @Override
    public String getStatusCode(){
        return "400";
    }
}
