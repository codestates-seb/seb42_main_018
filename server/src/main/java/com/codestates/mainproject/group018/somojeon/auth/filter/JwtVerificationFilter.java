package com.codestates.mainproject.group018.somojeon.auth.filter;

import com.codestates.mainproject.group018.somojeon.auth.service.AuthService;
import com.codestates.mainproject.group018.somojeon.auth.token.CustomAuthenticationToken;
import com.codestates.mainproject.group018.somojeon.auth.token.JwtTokenizer;
import com.codestates.mainproject.group018.somojeon.auth.utils.CustomAuthorityUtils;
import com.codestates.mainproject.group018.somojeon.exception.BusinessLogicException;
import com.codestates.mainproject.group018.somojeon.exception.ExceptionCode;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;


@Slf4j
public class JwtVerificationFilter extends OncePerRequestFilter {  // (1)
    private final JwtTokenizer jwtTokenizer;
    private final CustomAuthorityUtils authorityUtils;
    private  final AuthService authService;

    public JwtVerificationFilter(JwtTokenizer jwtTokenizer, CustomAuthorityUtils authorityUtils,
                                 AuthService authService) {
        this.jwtTokenizer = jwtTokenizer;
        this.authorityUtils = authorityUtils;
        this.authService = authService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // (1)
        try {
            Map<String, Object> claims = verifyJws(request);
            setAuthenticationToContext(claims);
            response.addHeader("Expired","False");
        } catch (ExpiredJwtException ee) {
            log.warn("Expired ACCESS JWT Exception");
            if (request.getMethod().equals("POST") && request.getRequestURI().equals("/users")) {
                // todo-jh 하드 코딩 제거
                response.sendRedirect("http://localhost:3000/login");
            }
            else{
                response.addHeader("Access Token Expired","True");
                response.setStatus(HttpStatus.OK.value());

                request.getRequestURI();
                return;
            }

        }

        filterChain.doFilter(request, response);
    }


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String authorization = request.getHeader("Authorization");

        return authorization == null || !authorization.startsWith("Bearer");
    }

    private Map<String, Object> verifyJws(HttpServletRequest request) {
        String jws = request.getHeader("Authorization").replace("Bearer ", "");
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());
        Map<String, Object> claims = jwtTokenizer.getClaims(jws, base64EncodedSecretKey).getBody();

        return claims;
    }

    private void setAuthenticationToContext(Map<String, Object> claims) {
        String username = (String) claims.get("username"); //email
        String userId =  String.valueOf(claims.get("userId"));
        List<String> roles = (List)claims.get("roles");
        if (roles == null) return;
        List<GrantedAuthority> authorities = authorityUtils.createAuthorities(roles);
        Authentication authentication = new CustomAuthenticationToken(username, null,  userId, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        log.info("Verification Success for Authorization token");
        log.info("Request ID: {}", userId);
        log.info("Request roles: {}",roles.toString());

    }

}
