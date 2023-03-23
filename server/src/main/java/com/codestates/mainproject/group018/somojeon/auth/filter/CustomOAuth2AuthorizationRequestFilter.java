package com.codestates.mainproject.group018.somojeon.auth.filter;

import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomOAuth2AuthorizationRequestFilter extends OAuth2AuthorizationRequestRedirectFilter {
    public CustomOAuth2AuthorizationRequestFilter(ClientRegistrationRepository clientRegistrationRepository) {
        super(clientRegistrationRepository);
    }

    public CustomOAuth2AuthorizationRequestFilter(ClientRegistrationRepository clientRegistrationRepository, String authorizationRequestBaseUri) {
        super(clientRegistrationRepository, authorizationRequestBaseUri);
    }

    public CustomOAuth2AuthorizationRequestFilter(OAuth2AuthorizationRequestResolver authorizationRequestResolver) {
        super(authorizationRequestResolver);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String paramName = "returnurl"; // 저장할 파라미터 이름
        String paramValue = request.getParameter(paramName); // 파라미터 값
        request.setAttribute(paramName, paramValue);

        super.doFilterInternal(request, response, filterChain);
    }
}
