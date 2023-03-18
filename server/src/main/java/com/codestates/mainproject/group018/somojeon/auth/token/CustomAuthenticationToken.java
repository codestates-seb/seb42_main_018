package com.codestates.mainproject.group018.somojeon.auth.token;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class CustomAuthenticationToken extends UsernamePasswordAuthenticationToken {
    private final Long userId;

    public CustomAuthenticationToken(Object principal, Object credentials, String userId,
                                     Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
        this.userId = Long.parseLong(userId);
    }

    public Long getUserId() {
        return userId;
    }

}
