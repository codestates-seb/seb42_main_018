package com.codestates.mainproject.group018.somojeon.auth.handler;

import com.codestates.mainproject.group018.somojeon.auth.token.JwtTokenizer;
import com.codestates.mainproject.group018.somojeon.exception.BusinessLogicException;
import com.codestates.mainproject.group018.somojeon.exception.ExceptionCode;
import com.codestates.mainproject.group018.somojeon.oauth.service.OauthUserService;
import com.codestates.mainproject.group018.somojeon.utils.Identifier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2UserSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtTokenizer jwtTokenizer;
    private final OauthUserService oauthUserService;
    private final Identifier identifier;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {


        var oAuth2User = (OAuth2User)authentication.getPrincipal();
        String registration = ((OAuth2AuthenticationToken) authentication).getAuthorizedClientRegistrationId();
        Long registrationId = null;
        String email = null;
        Map<String, Object> account = null;
        if(registration == "kakao"){
            log.info("kakao oauth 2.0");
            registrationId = (Long) oAuth2User.getAttributes().get("id");
            account = (Map<String, Object>) oAuth2User.getAttributes().get("kakao_account");
        }

        if(registration == null || registrationId == null){
            throw new BusinessLogicException(ExceptionCode.CLIENT_NOT_FOUND);
        }
        Map<String, String> param = new HashMap<>();
        String path = "register";
        if(oauthUserService.IsUser(registration, registrationId)){
            log.info("Request kakao user is already somojeon user!");
            param.put("id", String.valueOf(identifier.getUserId(registration, registrationId)));
            path = "oauth2/receive";
        }
        else{
            if((boolean)account.get("has_email")) email = (String) account.get("email");
            param.put("email", email);
        }
        redirect(request, response, registration, String.valueOf(registrationId), param, path);
    }

    private void redirect(HttpServletRequest request, HttpServletResponse response
                         ,String registration, String registrationId,  Map<String, String> param, String path) throws IOException {
        String accessToken = delegateAccessToken(registration, registrationId);
        String refreshToken = delegateRefreshToken(registration, registrationId);
//        String path = (String) request.getAttribute("oauth2/receive");
        String uri =  createURI(accessToken, refreshToken, path, param).toString();

        getRedirectStrategy().sendRedirect(request, response, uri);
    }

    private String delegateAccessToken(String registration, String registrationId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("registration", registration);
        claims.put("registrationId", registrationId);

        String subject = registrationId;
        Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getAccessTokenExpirationMinutes());

        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());

        String accessToken = "Bearer " + jwtTokenizer.generateAccessToken(claims, subject, expiration, base64EncodedSecretKey);

        return accessToken;
    }

    private String delegateRefreshToken(String registration, String registrationId) {
        String subject = registrationId;
        Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getRefreshTokenExpirationMinutes());
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());

        String refreshToken = jwtTokenizer.generateRefreshToken(subject, expiration, base64EncodedSecretKey);

        return refreshToken;
    }

    private URI createURI(String accessToken, String refreshToken, String path, Map<String, String> param) {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("access_token", accessToken);
        queryParams.add("refresh_token", refreshToken);
        for(String key : param.keySet()){
            queryParams.add(key, param.get(key));
        }

        return UriComponentsBuilder
                .newInstance()
//                .scheme("https")
//                .host("dev-somojeon.vercel.app")
                .scheme("http")
                .host("localhost")
                .port(3000)
                .path(path)
                .queryParams(queryParams)
                .build()
                .toUri();
    }
}
