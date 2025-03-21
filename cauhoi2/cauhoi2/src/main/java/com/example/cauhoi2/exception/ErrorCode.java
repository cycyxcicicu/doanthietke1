package com.example.cauhoi2.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public enum ErrorCode {
    
    UNCREATE_USER(9999,"uncreate existed"),
    INVALID_KEY(1001,"Invalid meesage key"),
    USER_EXISTED(1002, "user existed"),
    USERNAME_INVALID(1003,"Username must be ai least  character"),
    USER_NOT_EXISTED(1005, "user not existed"),
    UNAUTHENTICATED(1006, "authenticated"),
    UNAUTHORIZE(1007,"user do not have permission"),
    INVALID_DATE(1008, "Your age must be at least {min}"),
    PASSWORK_INVALID(1004,"Passwork lớn hơn 8 kí tự"),
    ROLE_NOT_EXISTED(1005, "ROLE not existed"),
    CLASS_NOT_EXISTED(1005, "CLASS not existed"),
    QUESTION_NOT_EXISTED(1005, "Question not existed"),
    CLASS_USER_NOT_EXISTED(1005, "class user not existed"),
    NOT_USERID_TESTID(1005, "test uest not existed"),
    TEST_NOT_EXISTED(1005, "Test user not existed"),
    INVALID_NUMBER_FORMAT(1006,"chuyển đổi sô thất bại")
    ;
       private ErrorCode(int code, String message) {
        this.code = code;
        
        this.message = message;
    }

    private int code;
    private String message;
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    
}
