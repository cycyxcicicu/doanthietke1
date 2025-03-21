package com.example.cauhoi2.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.cauhoi2.dto.request.ApiResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
//     @ExceptionHandler(value = RuntimeException.class)
//    ResponseEntity<ApiResponse> handlingRuntimeException(RuntimeException  exception){
//     ApiResponse apiResponse = new ApiResponse<>();
//     apiResponse.setCode(ErrorCode.UNCREATE_USER.getCode());
//     apiResponse.setMessage(ErrorCode.UNCREATE_USER.getMessage());
//     return ResponseEntity.badRequest().body(apiResponse);
//    }

   @ExceptionHandler(value = AppException.class)
   ResponseEntity<ApiResponse> handlingAppException(AppException  exception){
    ApiResponse apiResponse = new ApiResponse<>();
   ErrorCode errorCode = exception.getErrorCode();
   apiResponse.setCode(errorCode.getCode());
    apiResponse.setMessage(errorCode.getMessage());
    return ResponseEntity.badRequest().body(apiResponse);
   }


   @ExceptionHandler(value = MethodArgumentNotValidException.class)
   ResponseEntity<ApiResponse> handlingValidation(MethodArgumentNotValidException  exception){
    String enumKey= exception.getFieldError().getDefaultMessage();
    ErrorCode errorCode = ErrorCode.INVALID_KEY;
    try{errorCode = ErrorCode.valueOf(enumKey);}
    catch(Exception e){

    }
    
    ApiResponse apiResponse = new ApiResponse<>();

    apiResponse.setCode(errorCode.getCode());

    apiResponse.setMessage(errorCode.getMessage());

    return ResponseEntity.badRequest().body(apiResponse);
   }
   @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse> handleForeignKeyConstraintViolation(DataIntegrityViolationException ex) {
        ApiResponse apiResponse = new ApiResponse<>();
        
        // Thiết lập mã lỗi và thông điệp
        apiResponse.setCode(400);
        apiResponse.setMessage("Vi phạm ràng buộc khóa ngoại: " + ex.getMostSpecificCause().getMessage());

        // Trả về ResponseEntity với mã lỗi HTTP 400 (Bad Request)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }
}
