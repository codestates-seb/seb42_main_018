package com.codestates.mainproject.group018.somojeon.config;

import com.codestates.mainproject.group018.somojeon.auth.filter.JwtAuthenticationFilter;
import com.codestates.mainproject.group018.somojeon.auth.filter.JwtVerificationFilter;
import com.codestates.mainproject.group018.somojeon.auth.filter.LogFilter;
import com.codestates.mainproject.group018.somojeon.auth.handler.*;
import com.codestates.mainproject.group018.somojeon.auth.service.AuthService;
import com.codestates.mainproject.group018.somojeon.auth.token.JwtTokenizer;
import com.codestates.mainproject.group018.somojeon.auth.utils.CustomAuthorityUtils;
import com.codestates.mainproject.group018.somojeon.club.mapper.ClubMapper;
import com.codestates.mainproject.group018.somojeon.oauth.repository.OAuthUserRepository;
import com.codestates.mainproject.group018.somojeon.user.mapper.UserMapper;
import com.codestates.mainproject.group018.somojeon.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class SecurityConfiguration {

    @Autowired
    private OAuth2UserSuccessHandler oAuth2UserSuccessHandler;
    private final JwtTokenizer jwtTokenizer;
    private final CustomAuthorityUtils authorityUtils; // 추가
    private final UserService userService;
    private final UserMapper userMapper;
    private final AuthService authService;
    private final OAuthUserRepository oauthUserRepository;
    private final ClubMapper clubMapper;

    @Value("${oauth.kakao.redirect-address}")
    String redirectAddress;



    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .headers().frameOptions().sameOrigin()
                .and()
                .csrf().disable()
                .cors(withDefaults())
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)  // (1) 추가
                .and()
                .formLogin().disable()
                .httpBasic().disable()
                .exceptionHandling()
                .authenticationEntryPoint(new UserAuthenticationEntryPoint())
                .accessDeniedHandler(new UserAccessDeniedHandler())
                .and()
                .apply(new CustomFilterConfigurer())
                .and()
                .authorizeHttpRequests(authorize -> authorize
//                        .antMatchers(HttpMethod.PATCH, "/users/**").hasAnyRole("USER", "ADMIN")
//                        .antMatchers(HttpMethod.GET, "/users/**").permitAll()
//                        .antMatchers(HttpMethod.DELETE, "/users/**").hasAnyRole("USER", "ADMIN")
//                        .antMatchers(HttpMethod.POST, "/users/questions").hasAnyRole("USER", "ADMIN")
//                        .antMatchers(HttpMethod.POST, "/users").permitAll()
//                        .antMatchers(HttpMethod.GET, "/users").permitAll()
//                        .antMatchers(HttpMethod.GET, "/users?*").permitAll()
//                        .antMatchers(HttpMethod.GET, "/questions").permitAll()
//                        .antMatchers(HttpMethod.GET, "/questions/tags").permitAll()
//                        .antMatchers(HttpMethod.GET, "/questions/**").hasAnyRole("USER", "ADMIN")
//                        .antMatchers(HttpMethod.POST, "/questions").hasAnyRole("USER", "ADMIN")
//                        .antMatchers(HttpMethod.PATCH, "/questions/**").hasRole("USER")
//                        .antMatchers(HttpMethod.PATCH, "/questions/*/vote").hasRole("USER")
//                        .antMatchers(HttpMethod.DELETE, "/questions/**").hasRole("USER")
//                        .antMatchers(HttpMethod.POST, "/*/questions/*/answers").hasRole("USER")
//                        .antMatchers(HttpMethod.PATCH, "/*/answers/**").hasRole("USER")
//                        .antMatchers(HttpMethod.DELETE, "/answers/**").hasRole("USER")
//                        .antMatchers(HttpMethod.PATCH, "/answers/*/answers").hasRole("USER")
//                        .antMatchers(HttpMethod.GET, "/tags").permitAll()
                        .anyRequest().permitAll()
                )
                .oauth2Login()
                    .successHandler(oAuth2UserSuccessHandler);


        return http.build();
    }


    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        var clientRegistration = kakaoClientRegistration();    // (3-1)

        return new InMemoryClientRegistrationRepository(clientRegistration);   // (3-2)
    }
    private ClientRegistration kakaoClientRegistration() {
        return ClientRegistration.withRegistrationId("kakao") // request uri "/oauth2/authorization/kakao"
                .clientId("cc9eb581caf2361034da01b9c99c75dd")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .redirectUri(redirectAddress)
                .authorizationUri("https://kauth.kakao.com/oauth/authorize")
                .tokenUri("https://kauth.kakao.com/oauth/token")
                .userInfoUri("https://kapi.kakao.com/v2/user/me")
                .userNameAttributeName("id")
                .clientName("Kakao")
                .build();
    }



        public class CustomFilterConfigurer extends AbstractHttpConfigurer<CustomFilterConfigurer, HttpSecurity> {
        @Override
        public void configure(HttpSecurity builder) throws Exception {
            AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);

            JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager, jwtTokenizer, oauthUserRepository);
            jwtAuthenticationFilter.setFilterProcessesUrl("/auth/login");

            jwtAuthenticationFilter.setAuthenticationSuccessHandler(new UserAuthenticationSuccessHandler(userService, userMapper, clubMapper));
            jwtAuthenticationFilter.setAuthenticationFailureHandler(new UserAuthenticationFailureHandler());

            JwtVerificationFilter jwtVerificationFilter = new JwtVerificationFilter(jwtTokenizer, authorityUtils, authService);


            builder
                .addFilterBefore(new LogFilter(), ChannelProcessingFilter.class)
                .addFilter(jwtAuthenticationFilter)
                .addFilterAfter(jwtVerificationFilter, JwtAuthenticationFilter.class);
        }


    }
}
