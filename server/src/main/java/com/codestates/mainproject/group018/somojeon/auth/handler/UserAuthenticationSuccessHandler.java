package com.codestates.mainproject.group018.somojeon.auth.handler;

import com.codestates.mainproject.group018.somojeon.club.entity.UserClub;
import com.codestates.mainproject.group018.somojeon.club.mapper.ClubMapper;
import com.codestates.mainproject.group018.somojeon.dto.SingleResponseDto;
import com.codestates.mainproject.group018.somojeon.user.dto.UserDto;
import com.codestates.mainproject.group018.somojeon.user.entity.User;
import com.codestates.mainproject.group018.somojeon.user.mapper.UserMapper;
import com.codestates.mainproject.group018.somojeon.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class UserAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final UserService userService;
    private final UserMapper userMapper;
    private final ClubMapper clubMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        // 인증 성공 후, 로그를 기록하거나 사용자 정보를 response로 전송하는 등의 추가 작업을 할 수 있다.
        log.info("# Authenticated successfully!");

        User user = userService.findUserByEmail(authentication.getName());
        String userId =String.valueOf(user.getUserId());
        response.addHeader("memeber-id", userId);

        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());

        List<UserClub> userClubs =  userService.findUserClub(user.getUserId());

        UserDto.ResponseWithClubs responseWithClubs = userMapper.userToUserResponseWithClubs(user, userClubs, clubMapper);
        response.getWriter().write(new ObjectMapper().writeValueAsString(new SingleResponseDto<>(responseWithClubs)));
        log.info("LOGIN ID: {}", userId);


    }
}