package com.codestates.mainproject.group018.somojeon.oauth.service;

import com.codestates.mainproject.group018.somojeon.exception.BusinessLogicException;
import com.codestates.mainproject.group018.somojeon.exception.ExceptionCode;
import com.codestates.mainproject.group018.somojeon.oauth.entity.OAuthUser;
import com.codestates.mainproject.group018.somojeon.oauth.repository.OAuthUserRepository;
import com.codestates.mainproject.group018.somojeon.user.entity.User;
import com.codestates.mainproject.group018.somojeon.user.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OauthUserService {
    private final OAuthUserRepository oAuthUserRepository;
    private final UserService userService;

    public OauthUserService(OAuthUserRepository oAuthUserRepository, UserService userService) {
        this.oAuthUserRepository = oAuthUserRepository;
        this.userService = userService;
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


}
