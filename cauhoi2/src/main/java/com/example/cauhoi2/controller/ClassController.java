package com.example.cauhoi2.controller;

import com.example.cauhoi2.dto.request.ApiResponse;
import com.example.cauhoi2.dto.request.ClassCreationRequest;
import com.example.cauhoi2.dto.request.ClassUpdateRequest;
import com.example.cauhoi2.dto.response.ClassResponse;
import com.example.cauhoi2.dto.response.UserOfClassResponse;
import com.example.cauhoi2.exception.AppException;
import com.example.cauhoi2.exception.ErrorCode;
import com.example.cauhoi2.service.ClassService;
import com.example.cauhoi2.service.UserService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/class")

@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClassController {
    @Autowired
    ClassService classService;
    @Autowired
    UserService userService;
    //tạo lớp
    @PostMapping("/{userid}")
    public ApiResponse<ClassResponse> createclass(@RequestBody ClassCreationRequest request,@PathVariable("userid") String id) {
        ApiResponse<ClassResponse> apiResponse = new ApiResponse<>();
        //apiResponse.setResult(classService.createclass(request,id));
        classService.createClass(request,id);
        apiResponse.setMessage("tạo lớp thành công");
        return apiResponse;
    }
    @GetMapping("")
    public ApiResponse<List<ClassResponse>> getClasz() {
        ApiResponse<List<ClassResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(classService.getclass());
        return apiResponse;
    }
    @GetMapping("/my-class")
    public ApiResponse<List<ClassResponse>> getClaszByUserid() {
        ApiResponse<List<ClassResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(classService.getMyClasses());
        return apiResponse;
    }
    // sửa lớp
    @PutMapping("/{classid}")
    public ApiResponse<ClassResponse> putMethodName(@PathVariable("classid") String id, @RequestBody ClassUpdateRequest request) {
        ApiResponse<ClassResponse> apiResponse = new ApiResponse<>();
        classService.updateClass(request, id);
        apiResponse.setCode(1000);
        apiResponse.setMessage("sửa lớp thành công");
        return apiResponse;
    }
    // thêm thành viên
    @PostMapping("/{userid}/{classid}")
    public ApiResponse<String> themthanhvien(@PathVariable("userid") String userid,@PathVariable("classid") String classid) {
        ApiResponse<String> apiResponse = new ApiResponse<>();

        apiResponse.setMessage(classService.themthanhvienclass(classid, userid));
        if( classService.themthanhvienclass(classid, userid) == "thanh vien nay da ton tai")
        {apiResponse.setCode(1001);}
        
        return apiResponse;
    }
    //xóa lớp
    @DeleteMapping("/{classid}")
    public ApiResponse<String> xoalop(@PathVariable("classid") String classid){
        ApiResponse<String> apiResponse = new ApiResponse<>();
       
        apiResponse.setMessage(classService.deleteclass(classid));
        return apiResponse;
    }
    //xóa thành viên
    @DeleteMapping("/{userid}/{classid}")
    public ApiResponse<String> xoathanhvien(@PathVariable("userid") String userid,@PathVariable("classid") String classid){
        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setMessage(classService.deletethanhvien(classid, userid));
        return apiResponse;
    }
    //danh sach học sinh trong lớp
    @GetMapping("/{classid}")
    public ApiResponse<List<UserOfClassResponse>> getthanhvienlophoc(@PathVariable("classid") String classid) {
        ApiResponse<List<UserOfClassResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(classService.getclassbyuser(classid));
        return apiResponse;
        
    }
    
}
