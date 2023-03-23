package com.codestates.mainproject.group018.somojeon.utils;

import com.codestates.mainproject.group018.somojeon.auth.service.AuthService;
import com.codestates.mainproject.group018.somojeon.auth.token.CustomAuthenticationToken;
import com.codestates.mainproject.group018.somojeon.club.entity.UserClub;
import com.codestates.mainproject.group018.somojeon.club.enums.ClubRole;
import com.codestates.mainproject.group018.somojeon.club.service.ClubService;
import com.codestates.mainproject.group018.somojeon.oauth.entity.OAuthUser;
import com.codestates.mainproject.group018.somojeon.oauth.service.OauthUserService;
import com.codestates.mainproject.group018.somojeon.user.entity.User;
import com.codestates.mainproject.group018.somojeon.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class Identifier {
    private final UserService userService;
    private final ClubService clubService;

    private final AuthService authService;
    private final OauthUserService oauthUserService;
    private String[] DEFAULT_ALLOWED_CLUBROLES = new String[]{"MANAGER", "LEADER"};


    public boolean isVerified(Long targetId){
        // admin이거나 본인이 본인을 타겟으로 하면 true를 리턴
        if(isAdmin()) return true;
        return getUserId() == targetId;
    }

    public boolean isAdmin(){
        List<String> currentUserRole = getRoles();
        return currentUserRole.contains("ROLE_ADMIN");
    }

    public boolean isAdmin(Long userId){
        User user = userService.findUser(userId);
        return  user.getRoles().contains("ADMIN");
    }



    public Long getUserId(){
        CustomAuthenticationToken customAuthenticationToken = (CustomAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        return customAuthenticationToken.getUserId();
    }

    public Long getUserId(String registration, Long registrationId){
        OAuthUser oAuthUser =  oauthUserService.findOAuthUser(registration, registrationId);
        return oAuthUser.getUser().getUserId();
    }
    public String getEmail(){
        User user = userService.findUser(getUserId());
        return user.getEmail();
    }

    public List<String> getRoles(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null) return List.of("null");
        return authentication.getAuthorities()
                .stream().map(Object::toString)
                .collect(Collectors.toList());
    }

    public boolean checkClubRole(Long clubId, String... allowedClubRoles){
        // 입력받은 아이디와 역할에 대해, 현재 유저가 조건을 만족하는지 검사
        ClubRole clubRole = clubService.getUserClub(getUserId(), clubId);
        return List.of(allowedClubRoles).contains(clubRole.getRoles());

    }
    public boolean checkClubRole(Long clubId){
        // 입력받은 아이디와 역할에 대해, 현재 유저가 조건을 만족하는지 검사
        ClubRole clubRole = clubService.getUserClub(getUserId(), clubId);
        return List.of(DEFAULT_ALLOWED_CLUBROLES).contains(clubRole.getRoles());
    }

    public String getAccessToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if(token == null) return null;
        token = token.replace("Bearer ", "");
        // check verified access token
        Map<String, Object> claims = authService.getClaimsValues(token);
        String registration =  (String) claims.get("registration");
        String registrationId =  (String) claims.get("registrationId");
        if(registration == null || registrationId == null) return null;
        return token;
    }

    public List<Long> getClubIds(){
        Long userId = getUserId();
        List<UserClub> userClubs = clubService.getUserClubs(userId);
        return userClubs.stream()
                .map(
                        userClub -> userClub.getClub().getClubId()
                ).collect(Collectors.toList());
    }

}
