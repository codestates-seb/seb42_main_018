package com.codestates.mainproject.group018.somojeon.auth.handler;

import com.codestates.mainproject.group018.somojeon.auth.token.JwtTokenizer;
import com.codestates.mainproject.group018.somojeon.auth.utils.CustomAuthorityUtils;
import com.codestates.mainproject.group018.somojeon.user.entity.User;
import com.codestates.mainproject.group018.somojeon.user.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class SomojeonOAuth2UserSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {   // (1)
    private final JwtTokenizer jwtTokenizer;
    private final CustomAuthorityUtils authorityUtils;
    private final UserService userService;

    // (2)
    public SomojeonOAuth2UserSuccessHandler(JwtTokenizer jwtTokenizer,
                                      CustomAuthorityUtils authorityUtils,
                                      UserService userService) {
        this.jwtTokenizer = jwtTokenizer;
        this.authorityUtils = authorityUtils;
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        var oAuth2User = (OAuth2User)authentication.getPrincipal();
        String email = String.valueOf(oAuth2User.getAttributes().get("email")); // (3)
        List<String> authorities = authorityUtils.createRoles(email);           // (4)

        saveUser(email);  // (5)
        redirect(request, response, email, authorities);  // (6)
    }

    private void saveUser(String email) {
        User user = new User(email);
//        userService.createUser(user);
    }

    private void redirect(HttpServletRequest request, HttpServletResponse response, String username, List<String> authorities) throws IOException, IOException {
//        String accessToken = delegateAccessToken(username, authorities);  // (6-1)
//        String refreshToken = delegateRefreshToken(username);     // (6-2)
//
//        String uri = createURI(accessToken, refreshToken).toString();   // (6-3)
        getRedirectStrategy().sendRedirect(request, response, "http://localhost:8080");   // (6-4)
    }

//    private String delegateAccessToken(String username, List<String> authorities) {
//        Map<String, Object> claims = new HashMap<>();
//        claims.put("username", username);
//        claims.put("roles", authorities);
//
//        String subject = username;
//        Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getAccessTokenExpirationMinutes());
//
//        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());
//
//        String accessToken = jwtTokenizer.generateAccessToken(claims, subject, expiration, base64EncodedSecretKey);
//
//        return accessToken;
//    }
//
//    private String delegateRefreshToken(String username) {
//        String subject = username;
//        Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getRefreshTokenExpirationMinutes());
//        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());
//
//        String refreshToken = jwtTokenizer.generateRefreshToken(subject, expiration, base64EncodedSecretKey);
//
//        return refreshToken;
//    }
//
//    private URI createURI(String accessToken, String refreshToken) {
//        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
//        queryParams.add("access_token", accessToken);
//        queryParams.add("refresh_token", refreshToken);
//
//        return UriComponentsBuilder
//                .newInstance()
//                .scheme("http")
//                .host("localhost")
////                .port(80)
//                .path("/receive-token.html")
//                .queryParams(queryParams)
//                .build()
//                .toUri();
//    }

}
