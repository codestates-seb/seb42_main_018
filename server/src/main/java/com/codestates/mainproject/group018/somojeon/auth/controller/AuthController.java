package com.codestates.mainproject.group018.somojeon.auth.controller;

import com.codestates.mainproject.group018.somojeon.auth.service.AuthService;
import com.codestates.mainproject.group018.somojeon.auth.token.JwtTokenProvider;
import com.codestates.mainproject.group018.somojeon.auth.token.JwtTokenizer;
import com.codestates.mainproject.group018.somojeon.exception.BusinessLogicException;
import com.codestates.mainproject.group018.somojeon.exception.ExceptionCode;
import com.codestates.mainproject.group018.somojeon.user.entity.User;
import com.codestates.mainproject.group018.somojeon.user.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/auth/refresh")
    public ResponseEntity<Map<String, String>> getNewTokens(HttpServletRequest request, HttpServletResponse response) {
        authService.refresh(request, response);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/auth/oauth/kakao")
    public ResponseEntity<Map<String, String>> postOauthForKakao() {
        authService.getAuthorizeCodeForKakao();
        return ResponseEntity.ok().build();
    }
}
