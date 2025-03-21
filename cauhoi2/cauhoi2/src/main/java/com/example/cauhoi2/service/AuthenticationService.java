package com.example.cauhoi2.service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.cauhoi2.dto.request.AuthenticationRequest;
import com.example.cauhoi2.dto.request.IntrospectRequest;
import com.example.cauhoi2.dto.request.LogoutRequest;
import com.example.cauhoi2.dto.request.RefreshRequest;
import com.example.cauhoi2.dto.response.AuthenticationResponse;
import com.example.cauhoi2.dto.response.IntrospectRespone;
import com.example.cauhoi2.entity.InvalideteToken;
import com.example.cauhoi2.entity.User;
import com.example.cauhoi2.exception.AppException;
import com.example.cauhoi2.exception.ErrorCode;
import com.example.cauhoi2.repository.InvalideteRepository;
import com.example.cauhoi2.repository.UserRepository;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
    UserRepository userRepository;
    InvalideteRepository invalidatedTokenRepository;
    @NonFinal
    @Value("${jwt.signerKey}")
    protected  String sign_key;
    public AuthenticationResponse authenticate( AuthenticationRequest request)
    {
        var user = userRepository.findByUsername(request.getUsername()).orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean authenticated= passwordEncoder.matches(request.getPassword(), user.getPassword());
        if(!authenticated)
        throw new AppException(ErrorCode.UNAUTHENTICATED);
    var token = generateToken(user);
        return AuthenticationResponse.builder()
        .token(token)
        .authenticated(true)
        .build();
    }
    private String generateToken(User user){
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                                                  .subject(user.getUsername())
                                                  .issuer("devteria.com")
                                                  .issueTime(new Date())
                                                  .expirationTime(new Date(
                                                    Instant.now().plus(1,ChronoUnit.HOURS).toEpochMilli()
                                                  ))
                                                  .jwtID(UUID.randomUUID().toString())
                                                .claim("userid",user.getUserid())        
                                                .build();
        Payload payload= new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);
        try {
            jwsObject.sign(new MACSigner(sign_key.getBytes()));
            return jwsObject.serialize();
        }  catch (JOSEException e) {
            
            throw new RuntimeException(e);
        }

    }
    public  IntrospectRespone introspect (IntrospectRequest request) throws JOSEException, ParseException{
        var token = request.getToken();
        boolean isvalid= true;
        try{verifyToken(token);}
            catch(Exception ex){
               isvalid= false;
            }
     return IntrospectRespone.builder()
                                    .valid(isvalid)
                                    .build();

    }
    public void Logout(LogoutRequest request) throws JOSEException, ParseException{
        var signToken = verifyToken(request.getToken());
        String jit = signToken.getJWTClaimsSet().getJWTID();
        Date expirytime= signToken.getJWTClaimsSet().getExpirationTime();
        InvalideteToken invalidatedToken= InvalideteToken.builder()
        .id(jit)
        .expiryTime(expirytime)
        .build();
        invalidatedTokenRepository.save(invalidatedToken);
    }
    private SignedJWT verifyToken(String token) throws JOSEException, ParseException{
        JWSVerifier verifier = new MACVerifier(sign_key.getBytes());
                SignedJWT signedJWT= SignedJWT.parse(token);
                var verifyed=signedJWT.verify(verifier);
                Date expytime =signedJWT.getJWTClaimsSet().getExpirationTime();
                if(!(verifyed && expytime.after(new Date())))
                throw new AppException(ErrorCode.UNAUTHENTICATED);
    
                if(invalidatedTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID()))
                throw new AppException(ErrorCode.UNAUTHENTICATED);
                
                return signedJWT;
    }

    public AuthenticationResponse refreshToken(RefreshRequest request) throws JOSEException, ParseException{
        // Xác thực và kiểm tra token hiện tại
    var signjwt = verifyToken(request.getToken());

    // Lấy thời gian hết hạn của token
    Date expiryTime = signjwt.getJWTClaimsSet().getExpirationTime();
    long timeRemaining = expiryTime.getTime() - System.currentTimeMillis();

    // Nếu còn ít hơn 10 phút, làm mới token
    if (timeRemaining < 10 * 60 * 1000) {
        String jit = signjwt.getJWTClaimsSet().getJWTID();
        InvalideteToken invalidatedToken = InvalideteToken.builder()
            .id(jit)
            .expiryTime(expiryTime)
            .build();
        invalidatedTokenRepository.save(invalidatedToken);

        // Lấy thông tin người dùng từ token
        String username = signjwt.getJWTClaimsSet().getSubject();
        var user = userRepository.findByUsername(username).orElseThrow(
            () -> new AppException(ErrorCode.UNAUTHENTICATED)
        );

        // Tạo token mới và trả về
        String newToken = generateToken(user);

        return AuthenticationResponse.builder()
            .token(newToken)
            .authenticated(true)
            .build();
    } else {
        // Nếu token vẫn còn hiệu lực, trả về token cũ
        return AuthenticationResponse.builder()
            .token(request.getToken())  // Trả lại token cũ
            .authenticated(true)
            .build();
    }
}
    
}
