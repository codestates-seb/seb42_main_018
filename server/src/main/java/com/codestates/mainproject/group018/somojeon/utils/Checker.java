package com.codestates.mainproject.group018.somojeon.utils;

import com.codestates.mainproject.group018.somojeon.auth.customtoken.CustomAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

public class Checker {
    public static boolean checkVerified(Long targetId){
        List<String> currentUserRole = getRoles();
        if(currentUserRole.contains("ROLE_ADMIN")) return true;
        return getMemberId() == targetId;
    }

    public static boolean checkAdmin(){
        List<String> currentUserRole = getRoles();
        return currentUserRole.contains("ROLE_ADMIN");
    }

//    public static boolean checkVerificationResult(HttpServletRequest request){
//       return request.getAttribute("ok").equals(true);
//    }

    public static Long getMemberId(){
        CustomAuthenticationToken customAuthenticationToken = (CustomAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        return customAuthenticationToken.getMemberId();
    }

    public static List<String> getRoles(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null) return List.of("null");
        return authentication.getAuthorities()
                .stream().map(Object::toString)
                .collect(Collectors.toList());
    }

}
