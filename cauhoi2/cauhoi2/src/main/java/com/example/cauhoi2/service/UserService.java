package com.example.cauhoi2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.cauhoi2.dto.request.UserCreateRequest;
import com.example.cauhoi2.dto.request.UserUpdateRequest;
import com.example.cauhoi2.dto.request.UserUpdatepasswordRequest;
import com.example.cauhoi2.dto.response.UserResponse;
import com.example.cauhoi2.entity.User;
import com.example.cauhoi2.exception.AppException;
import com.example.cauhoi2.exception.ErrorCode;
import com.example.cauhoi2.mapper.UserMapper;
import com.example.cauhoi2.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    
    UserRepository userRepository;
    UserMapper userMapper;
    public User createRequest(UserCreateRequest request){

        if (userRepository.existsByUsername(request.getUsername())) throw new AppException(ErrorCode.USER_EXISTED);
        User user = userMapper.toUser(request);

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        
        return userRepository.save(user);
    }
    public List<User> getUserok(){
        return userRepository.findAll();
    }
    public UserResponse getUser(String id){
        return userMapper.toUserResponse(userRepository.findById(id).orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED)));
    }
    public UserResponse updateUser(String id, UserUpdateRequest request){
        User user = userRepository.findById(id).orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));
        
        userMapper.toupdate(user, request);
        return userMapper.toUserResponse(userRepository.save(user));
    }
    public void deleteUser(String id){
        userRepository.deleteById(id);
    }
    public UserResponse updateUserpassword( UserUpdatepasswordRequest request){
        
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));  
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);   
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        
        return userMapper.toUserResponse(userRepository.save(user));
    }
}
