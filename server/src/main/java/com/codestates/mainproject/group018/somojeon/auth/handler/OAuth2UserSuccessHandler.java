package com.codestates.mainproject.group018.somojeon.auth.handler;

import com.codestates.mainproject.group018.somojeon.auth.token.JwtTokenProvider;
import com.codestates.mainproject.group018.somojeon.auth.token.JwtTokenizer;
import com.codestates.mainproject.group018.somojeon.auth.utils.CustomAuthorityUtils;
import com.codestates.mainproject.group018.somojeon.exception.BusinessLogicException;
import com.codestates.mainproject.group018.somojeon.exception.ExceptionCode;
import com.codestates.mainproject.group018.somojeon.oauth.service.OauthUserService;
import com.codestates.mainproject.group018.somojeon.user.entity.User;
import com.codestates.mainproject.group018.somojeon.user.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class OAuth2UserSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {   // (1)
    private final JwtTokenizer jwtTokenizer;
    private final CustomAuthorityUtils authorityUtils;
    private final OauthUserService oauthUserService;

    private final JwtTokenProvider jwtTokenProvider;

    // (2)


    public OAuth2UserSuccessHandler(JwtTokenizer jwtTokenizer, CustomAuthorityUtils authorityUtils
            , OauthUserService oauthUserService, JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenizer = jwtTokenizer;
        this.authorityUtils = authorityUtils;
        this.oauthUserService = oauthUserService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

//        authentication.get
        var oAuth2User = (OAuth2User)authentication.getPrincipal();
        String registraion = ((OAuth2AuthenticationToken) authentication).getAuthorizedClientRegistrationId();
        String id = null;
        String email = null;
        if(registraion == "kakao"){
            id = String.valueOf(oAuth2User.getAttributes().get("id"));
            Map<String, Object> account = (Map<String, Object>) oAuth2User.getAttributes().get("kakao_account");
            if((boolean)account.get("has_email")) email = (String) account.get("email");
        }

        if(registraion == null || id == null){
            throw new BusinessLogicException(ExceptionCode.CLIENT_NOT_FOUND);
        }
        boolean home = oauthUserService.IsUser(registraion, Long.parseLong(id));
        response.addHeader("email", email);
        redirect(request, response, registraion, id, home);
    }

    private void redirect(HttpServletRequest request, HttpServletResponse response
                         ,String registration, String registrationId, boolean home) throws IOException, IOException {
        String accessToken = delegateAccessToken(registration, registrationId);  // (6-1)
        String refreshToken = delegateRefreshToken(registration, registrationId);
        String path = home ? "" : "register";
        String uri =  createURI(accessToken, refreshToken, path).toString()
                           ;

        getRedirectStrategy().sendRedirect(request, response, uri);
    }

    private String delegateAccessToken(String registration, String registrationId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("registration", registration);
        claims.put("registrationId", registrationId);

        String subject = registrationId;
        Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getAccessTokenExpirationMinutes());

        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());

        String accessToken = jwtTokenizer.generateAccessToken(claims, subject, expiration, base64EncodedSecretKey);

        return accessToken;
    }

    private String delegateRefreshToken(String registration, String registrationId) {
        String subject = registrationId;
        Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getRefreshTokenExpirationMinutes());
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());

        String refreshToken = jwtTokenizer.generateRefreshToken(subject, expiration, base64EncodedSecretKey);

        return refreshToken;
    }

    private URI createURI(String accessToken, String refreshToken, String path) {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("access_token", accessToken);
        queryParams.add("refresh_token", refreshToken);

        return UriComponentsBuilder
                .newInstance()
                .scheme("https")
                .host("dev-somojeon.vercel.app")
                .path(path)
                .queryParams(queryParams)
                .build()
                .toUri();
    }
}
