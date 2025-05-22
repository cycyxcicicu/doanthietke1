package com.example.cauhoi2.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNCREATED_USER(1010,"uncreate user", HttpStatus.BAD_REQUEST),
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
    CANNOT_READ_FILE(1010, "Cannot read data in file", HttpStatus.BAD_REQUEST),
    CANNOT_SAVE_IMAGE(1011, "Cannot save image", HttpStatus.BAD_REQUEST),
    IMAGE_NOT_EXISTED(1012, "Image not existed", HttpStatus.BAD_REQUEST),
    EXAM_NOT_EXISTED(1013, "Exam not existed", HttpStatus.BAD_REQUEST),
    IMAGE_CANNOT_REMOVE(1014, "Cannot remove image", HttpStatus.BAD_REQUEST),
    EXIST_QUESTION_HAS_NO_ANSWER_CORRECT(1015, "Exist question has no answer correct", HttpStatus.BAD_REQUEST),
    EXIST_QUESTION_HAS_MANY_ANSWERS_CORRECT(1016, "Exist question has many answers correct", HttpStatus.BAD_REQUEST),
    EXIST_QUESTION_HAS_UNDER_2_ANSWER(1017, "Exist question has under 2 answer", HttpStatus.BAD_REQUEST)
    ;
    ErrorCode(int code, String message, HttpStatus httpStatusCode) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatusCode;
    }
    private final HttpStatusCode httpStatus;
    private final int code;
    private final String message;
}