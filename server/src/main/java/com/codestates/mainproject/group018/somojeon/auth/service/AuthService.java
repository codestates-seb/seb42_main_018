package com.codestates.mainproject.group018.somojeon.auth.service;

import com.codestates.mainproject.group018.somojeon.auth.token.JwtTokenProvider;
import com.codestates.mainproject.group018.somojeon.auth.token.JwtTokenizer;
import com.codestates.mainproject.group018.somojeon.exception.BusinessLogicException;
import com.codestates.mainproject.group018.somojeon.exception.ExceptionCode;
import com.codestates.mainproject.group018.somojeon.user.entity.User;
import com.codestates.mainproject.group018.somojeon.user.repository.UserRepository;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Service
public class AuthService {
    private final JwtTokenizer jwtTokenizer;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(JwtTokenizer jwtTokenizer, UserRepository userRepository, JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenizer = jwtTokenizer;
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public void refresh(HttpServletRequest request, HttpServletResponse response) {
        // RefreshToken 검증
        String refreshToken = request.getHeader("Refresh");
        if(refreshToken == null) {

        }
        Map<String, Object> claims = null;
        try {
            claims = getClaimsValues(refreshToken);
        } catch (ExpiredJwtException expiredJwtException) {
            response.addHeader("Refresh-Token-Expired", "True");
            response.setStatus(HttpStatus.OK.value());
            return;
        }


        // Token 재발급
        String email = (String) claims.get("sub");
        Optional<User> optionalUser = userRepository.findByEmail(email);
        User user = optionalUser.orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
        jwtTokenProvider.provideTokens(user, response);
        response.addHeader("Refresh-Token-Expired ", "False");
    }

    public Map<String, Object> getClaimsValues(String token) {
        // RefreshToken 검증
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());
        Map<String, Object> claims = jwtTokenizer.getClaims(token, base64EncodedSecretKey).getBody();
        return claims;

    }


}
