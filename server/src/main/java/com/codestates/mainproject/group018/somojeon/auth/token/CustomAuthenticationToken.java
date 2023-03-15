package com.codestates.mainproject.group018.somojeon.auth.token;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class CustomAuthenticationToken extends UsernamePasswordAuthenticationToken {
    private final Long memberId;

    public CustomAuthenticationToken(Object principal, Object credentials, String memberId,
                                     Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
        this.memberId = Long.parseLong(memberId);
    }

    public Long getMemberId() {
        return memberId;
    }
}
