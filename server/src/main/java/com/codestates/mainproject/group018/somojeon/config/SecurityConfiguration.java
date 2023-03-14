package com.codestates.mainproject.group018.somojeon.config;

import com.codestates.mainproject.group018.somojeon.auth.filter.JwtAuthenticationFilter;
import com.codestates.mainproject.group018.somojeon.auth.filter.JwtVerificationFilter;
import com.codestates.mainproject.group018.somojeon.auth.filter.LogFilter;
import com.codestates.mainproject.group018.somojeon.auth.handler.UserAccessDeniedHandler;
import com.codestates.mainproject.group018.somojeon.auth.handler.UserAuthenticationEntryPoint;
import com.codestates.mainproject.group018.somojeon.auth.handler.UserAuthenticationFailureHandler;
import com.codestates.mainproject.group018.somojeon.auth.handler.UserAuthenticationSuccessHandler;
import com.codestates.mainproject.group018.somojeon.auth.service.AuthService;
import com.codestates.mainproject.group018.somojeon.auth.token.JwtTokenizer;
import com.codestates.mainproject.group018.somojeon.auth.utils.CustomAuthorityUtils;
import com.codestates.mainproject.group018.somojeon.user.mapper.UserMapper;
import com.codestates.mainproject.group018.somojeon.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@Slf4j
public class SecurityConfiguration {
    private final JwtTokenizer jwtTokenizer;
    private final CustomAuthorityUtils authorityUtils; // 추가
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthService authService;

    public SecurityConfiguration(JwtTokenizer jwtTokenizer, CustomAuthorityUtils authorityUtils,
                                 UserRepository userRepository, UserMapper userMapper,
                                 AuthService authService) {
        this.jwtTokenizer = jwtTokenizer;
        this.authorityUtils = authorityUtils;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.authService = authService;
    }

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
                );
        return http.build();
    }

    // (7)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


    public class CustomFilterConfigurer extends AbstractHttpConfigurer<CustomFilterConfigurer, HttpSecurity> {
        @Override
        public void configure(HttpSecurity builder) throws Exception {
            AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);

            JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager, jwtTokenizer);
            jwtAuthenticationFilter.setFilterProcessesUrl("/auth/login");

            jwtAuthenticationFilter.setAuthenticationSuccessHandler(new UserAuthenticationSuccessHandler(userRepository, userMapper));  // (3) 추가
            jwtAuthenticationFilter.setAuthenticationFailureHandler(new UserAuthenticationFailureHandler());  // (4) 추가

            JwtVerificationFilter jwtVerificationFilter = new JwtVerificationFilter(jwtTokenizer, authorityUtils, authService);

//            UserUrIVerificationFilter userUrIVerificationFilter = new UserUrIVerificationFilter();

            builder
                .addFilterBefore(new LogFilter(), ChannelProcessingFilter.class)
                .addFilter(jwtAuthenticationFilter)
                .addFilterAfter(jwtVerificationFilter, JwtAuthenticationFilter.class);
//                .addFilterAfter(userUrIVerificationFilter, JwtVerificationFilter.class);
        }
    }
}
