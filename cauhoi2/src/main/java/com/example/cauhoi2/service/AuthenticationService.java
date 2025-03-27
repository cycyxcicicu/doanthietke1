package com.example.cauhoi2.service;

import com.example.cauhoi2.dto.request.AuthenticationRequest;
import com.example.cauhoi2.dto.request.IntrospectRequest;
import com.example.cauhoi2.dto.request.RefreshRequest;
import com.example.cauhoi2.dto.response.AuthenticationResponse;
import com.example.cauhoi2.dto.response.IntrospectResponse;
import com.example.cauhoi2.entity.InvalidedToken;
import com.example.cauhoi2.entity.User;
import com.example.cauhoi2.exception.AppException;
import com.example.cauhoi2.exception.ErrorCode;
import com.example.cauhoi2.repository.InvalidedRepository;
import com.example.cauhoi2.repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
    UserRepository userRepository;
    InvalidedRepository invalidatedTokenRepository;
    @NonFinal
    @Value("${jwt.signerKey}")
    protected  String sign_key;
    public AuthenticationResponse authenticate(AuthenticationRequest request)
    {
        var user = userRepository.findByUsername(request.getUsername()).orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean isMatchPassword = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if(!isMatchPassword)
            throw new AppException(ErrorCode.PASSWORD_INCORRECT);
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
            .expirationTime(
                new Date(
                    Instant.now().plus(1,ChronoUnit.HOURS).toEpochMilli()
                )
            )
            .jwtID(UUID.randomUUID().toString())
            .claim("userid",user.getUserId())
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
    public IntrospectResponse introspect (IntrospectRequest request) throws JOSEException, ParseException{
        var token = request.getToken();
        boolean isvalid= true;
        try{
            verifyToken(token);
        }
        catch(Exception ex){
           isvalid = false;
        }
        return IntrospectResponse.builder()
            .valid(isvalid)
            .build();
    }
    public void Logout(String token) throws JOSEException, ParseException{
        var signToken = verifyToken(token);
        String jit = signToken.getJWTClaimsSet().getJWTID();
        Date expireTime= signToken.getJWTClaimsSet().getExpirationTime();
        InvalidedToken invalidatedToken= InvalidedToken.builder()
            .id(jit)
            .expiryTime(expireTime)
            .build();
        invalidatedTokenRepository.save(invalidatedToken);
    }
    private SignedJWT verifyToken(String token) throws JOSEException, ParseException{
        JWSVerifier verifier = new MACVerifier(sign_key.getBytes());
        SignedJWT signedJWT= SignedJWT.parse(token);
        var verified = signedJWT.verify(verifier);
        Date expireTime =signedJWT.getJWTClaimsSet().getExpirationTime();
        if(!(verified && expireTime.after(new Date())))
            throw new AppException(ErrorCode.UNAUTHENTICATED);

        if(invalidatedTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID()))
            throw new AppException(ErrorCode.UNAUTHENTICATED);

        return signedJWT;
    }
    public AuthenticationResponse refreshToken(RefreshRequest request) throws JOSEException, ParseException{
        // Xác thực và kiểm tra token hiện tại
        var signJwt = verifyToken(request.getToken());

        // Lấy thời gian hết hạn của token
        Date expiryTime = signJwt.getJWTClaimsSet().getExpirationTime();
        long timeRemaining = expiryTime.getTime() - System.currentTimeMillis();

        // Nếu còn ít hơn 10 phút, làm mới token
        if (timeRemaining < 10 * 60 * 1000) {
            String jit = signJwt.getJWTClaimsSet().getJWTID();
            InvalidedToken invalidatedToken = InvalidedToken.builder()
                .id(jit)
                .expiryTime(expiryTime)
                .build();
            invalidatedTokenRepository.save(invalidatedToken);

            // Lấy thông tin người dùng từ token
            String username = signJwt.getJWTClaimsSet().getSubject();
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
