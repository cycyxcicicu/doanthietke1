package com.example.cauhoi2.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cauhoi2.dto.request.ApiResponse;
import com.example.cauhoi2.dto.request.AuthenticationRequest;
import com.example.cauhoi2.dto.request.IntrospectRequest;
import com.example.cauhoi2.dto.request.LogoutRequest;
import com.example.cauhoi2.dto.request.RefreshRequest;
import com.example.cauhoi2.dto.response.AuthenticationResponse;
import com.example.cauhoi2.dto.response.IntrospectRespone;
import com.example.cauhoi2.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.text.ParseException;
import java.util.jar.JarException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/auth")


@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationController {
    @Autowired
    AuthenticationService authenticationService;


    @PostMapping("/token")
    public ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        
        var result=authenticationService.authenticate(request);
        return ApiResponse.<AuthenticationResponse>builder()
                        .result(result)
                        .build();
    }
    @PostMapping("/introspect")
    public ApiResponse<IntrospectRespone> authenticate(@RequestBody IntrospectRequest request)throws ParseException, JOSEException {
        
        var result=authenticationService.introspect(request);
        
        return ApiResponse.<IntrospectRespone>builder()
                        .result(result)
                        .Code(1000)
                        .build();
    }
    @PostMapping("/logout")
    ApiResponse<Void> Logout(@RequestBody LogoutRequest request) throws JOSEException, ParseException{
         authenticationService.Logout(request);
        return ApiResponse.<Void>builder()
            .Code(1000)
                .build();
    }
    @PostMapping("/refresh")
    ApiResponse<AuthenticationResponse> refresh(@RequestBody RefreshRequest request) throws JOSEException, ParseException{
         var result =authenticationService.refreshToken(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .result(result)
                .build();
    }
}
