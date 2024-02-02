package com.yjblog.exception;

public class InvalidSigningInformation extends GlobalException{

    private static final String MESSAGE = "아이디/비밀번호가 올바르지 않습니다.";

    public InvalidSigningInformation() {
        super(MESSAGE);
    }

    public InvalidSigningInformation(Throwable cause) {
        super(MESSAGE, cause);
    }

    @Override
    public String getStatusCode(){
        return "400";
    }
}
