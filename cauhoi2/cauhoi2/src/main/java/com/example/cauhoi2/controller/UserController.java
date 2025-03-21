package com.example.cauhoi2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.example.cauhoi2.dto.request.ApiResponse;
import com.example.cauhoi2.dto.request.UserCreateRequest;
import com.example.cauhoi2.dto.request.UserUpdateRequest;
import com.example.cauhoi2.dto.request.UserUpdatepasswordRequest;
import com.example.cauhoi2.dto.response.UserResponse;
import com.example.cauhoi2.entity.User;
import com.example.cauhoi2.service.UserService;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/users")

@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping
    public ApiResponse<User> createuser (@RequestBody @Valid UserCreateRequest request) {
        
        ApiResponse<User> apiResponse = new ApiResponse<>();
        userService.createRequest(request);
        apiResponse.setMessage("Đăng kí thành công");
        return apiResponse;
    }
    @GetMapping
    public ApiResponse<List<User>> getUsers() {
        ApiResponse<List<User>> apiResponse = new ApiResponse<>();
        try {
            
            List<User> users = userService.getUserok();
            apiResponse.setResult(users);
            apiResponse.setMessage("Users retrieved successfully.");
        } catch (Exception e) {
            apiResponse.setMessage("Failed to fetch users.");
            apiResponse.setResult(null);
        }
        return apiResponse;
    }
    @GetMapping("/{userid}")
    UserResponse getUser(@PathVariable("userid") String id)
    {
        return userService.getUser(id);
    }
    @PutMapping("/{userid}")
    public UserResponse putMethodName( @RequestBody UserUpdateRequest request, @PathVariable String userid) {
        return userService.updateUser(userid, request);
    }
    
    @DeleteMapping("/{userid}")
    public String deleteMethodName(@PathVariable String userid) {
        userService.deleteUser(userid);
        return "User deleted successfully";
    }
    @PutMapping("")
    public ApiResponse<User> putMethodName( @RequestBody @Valid UserUpdatepasswordRequest request) {
        ApiResponse<User> apiResponse = new ApiResponse<>();
        userService.updateUserpassword(request);
        apiResponse.setMessage("sửa thành password thành công");
        return apiResponse;
    }

}
