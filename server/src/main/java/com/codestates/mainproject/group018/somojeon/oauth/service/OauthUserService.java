package com.codestates.mainproject.group018.somojeon.oauth.service;

import com.codestates.mainproject.group018.somojeon.auth.service.AuthService;
import com.codestates.mainproject.group018.somojeon.exception.BusinessLogicException;
import com.codestates.mainproject.group018.somojeon.exception.ExceptionCode;
import com.codestates.mainproject.group018.somojeon.oauth.entity.OAuthUser;
import com.codestates.mainproject.group018.somojeon.oauth.repository.OAuthUserRepository;
import com.codestates.mainproject.group018.somojeon.user.entity.User;
import com.codestates.mainproject.group018.somojeon.user.service.UserService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Optional;

@Service
public class OauthUserService {
    private final OAuthUserRepository oAuthUserRepository;
    private final AuthService authService;

    public OauthUserService(OAuthUserRepository oAuthUserRepository, AuthService authService) {
        this.oAuthUserRepository = oAuthUserRepository;
        this.authService = authService;
    }

    private void findVerifiedUser(String registration, Long registrationId) {
        Optional<OAuthUser> oAuthUser = oAuthUserRepository.findByRegistrationAndRegistrationId(registration, registrationId);
        if (oAuthUser.isPresent())
            throw new BusinessLogicException(ExceptionCode.USER_EXISTS);
    }


    public boolean IsUser(String registration, Long registrationId) {
        Optional<OAuthUser> oAuthUser = oAuthUserRepository.findByRegistrationAndRegistrationId(registration, registrationId);
        return oAuthUser.isPresent();
    }


    public void createOAuthUser(String token, User user) {
        Map<String, Object> claims = authService.getClaimsValues(token);
        String registration =  (String) claims.get("registration");
        String registrationId =  (String) claims.get("registrationId");
        OAuthUser oAuthUser = new OAuthUser(registration, Long.parseLong(registrationId), user);
        oAuthUserRepository.save(oAuthUser);
    }


}
