package com.example.cauhoi2.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNCREATED_USER(9999,"uncreate existed", HttpStatus.BAD_REQUEST),
    INVALID_KEY(1001,"Invalid message key", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1002, "user existed", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1003,"Username must be ai least character", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1005, "user not existed", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(1006, "authenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007,"user do not have permission", HttpStatus.UNAUTHORIZED),
    INVALID_DATE(1008, "Your age must be at least {min}", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1004,"Password lớn hơn 8 kí tự", HttpStatus.BAD_REQUEST),
    ROLE_NOT_EXISTED(1005, "ROLE not existed", HttpStatus.BAD_REQUEST),
    CLASS_NOT_EXISTED(1005, "CLASS not existed", HttpStatus.BAD_REQUEST),
    QUESTION_NOT_EXISTED(1005, "Question not existed", HttpStatus.BAD_REQUEST),
    CLASS_USER_NOT_EXISTED(1005, "class user not existed", HttpStatus.BAD_REQUEST),
    NOT_USERID_TEST_ID(1005, "test uest not existed", HttpStatus.BAD_REQUEST),
    TEST_NOT_EXISTED(1005, "Test user not existed", HttpStatus.BAD_REQUEST),
    INVALID_NUMBER_FORMAT(1006,"Chuyển đổi sô thất bại", HttpStatus.BAD_REQUEST),
    PASSWORD_INCORRECT(1007, "Password incorrect", HttpStatus.BAD_REQUEST),
    COURSE_NOT_FOUND(1008, "Course not found", HttpStatus.BAD_REQUEST),
    COURSE_EXISTED(1009, "Course existed", HttpStatus.BAD_REQUEST),
    CATEGORY_EXISTED(1010, "Course existed", HttpStatus.BAD_REQUEST)
    ;
    ErrorCode(int code, String message, HttpStatus httpStatusCode) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatusCode;
    }
    private HttpStatusCode httpStatus;
    private int code;
    private String message;
}