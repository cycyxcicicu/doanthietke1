package com.example.cauhoi2.controller;

import com.example.cauhoi2.dto.request.*;
import com.example.cauhoi2.dto.response.ApiResponse;
import com.example.cauhoi2.dto.response.LoginResponse;
import com.example.cauhoi2.dto.response.IntrospectResponse;
import com.example.cauhoi2.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@Slf4j
@RestController
@RequestMapping("/auth")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationController {
    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/login")
    public ApiResponse<LoginResponse> authenticate(@RequestBody AuthenticationRequest request) {
        var result=authenticationService.authenticate(request);
        return ApiResponse.<LoginResponse>builder()
            .result(result)
            .build();
    }

    @PostMapping("/introspect")
    public ApiResponse<IntrospectResponse> authenticate(@RequestBody IntrospectRequest request)throws ParseException, JOSEException {
        var result=authenticationService.introspect(request);
        return ApiResponse.<IntrospectResponse>builder()
            .result(result)
            .code(1000)
            .build();
    }

    @PostMapping("/logout")
    ApiResponse<Void> Logout(@RequestHeader("Authorization") String bearerToken) throws JOSEException, ParseException{
        authenticationService.Logout(bearerToken.replace("Bearer ", "").trim());
        return ApiResponse.<Void>builder()
            .code(1000)
            .build();
    }

    @PostMapping("/refresh")
    ApiResponse<LoginResponse> refresh(@RequestBody RefreshRequest request) throws JOSEException, ParseException{
        var result =authenticationService.refreshToken(request);
        return ApiResponse.<LoginResponse>builder()
            .result(result)
            .build();
    }
}