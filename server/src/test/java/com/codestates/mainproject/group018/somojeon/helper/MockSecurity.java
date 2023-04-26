package com.codestates.mainproject.group018.somojeon.helper;

import com.codestates.mainproject.group018.somojeon.auth.token.JwtTokenizer;

import java.util.*;

public class MockSecurity {
    public static String getInvalidAccessToken() {
        return "eyJhbGciOiJIUzUxMiJ9.NjA2NDUwOTc3LCJtZW1iZXJJZCI6Mjd9.1TvYDexLUkOkOsBksbS6dnyJ4Ig1m9LMdTJ2FzCdOW0GEEdM4S6MpLZTpMGZCa-BN9jnbC9htZljsi5e7Mc-OQ";
    }

    public static String getValidAccessToken(String secretKey, String role) {
        JwtTokenizer jwtTokenizer = new JwtTokenizer();
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", 1L);
        claims.put("roles", List.of(role));

        String subject = "test access token";
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 1);
        Date expiration = calendar.getTime();

        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(secretKey);

        String accessToken = jwtTokenizer.generateAccessToken(claims, subject, expiration, base64EncodedSecretKey);

        return accessToken;
    }
}
