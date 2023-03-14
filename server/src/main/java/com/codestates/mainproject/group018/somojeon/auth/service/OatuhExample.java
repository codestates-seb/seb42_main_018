package com.codestates.mainproject.group018.somojeon.auth.service;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

public class OatuhExample {
    public static void main(String[] args) {
        RestTemplate restTemplate =
                new RestTemplate(new HttpComponentsClientHttpRequestFactory());

        UriComponents uriComponents =
                UriComponentsBuilder
                        .newInstance()
                        .scheme("https")
                        .host("kauth.kakao.com")
//                        .port(80)
                        .path("/oauth/authorize?client_id={REST_API_KEY}&redirect_uri={REDIRECT_URI}&response_type=code")
                        .encode()
                        .build();
        URI uri = uriComponents.expand("cc9eb581caf2361034da01b9c99c75dd", "https://somojeon.vercel.app").toUri();

        String result = restTemplate.getForObject(uri, String.class);

        System.out.println(result);
    }
}
