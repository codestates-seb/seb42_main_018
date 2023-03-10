package com.codestates.mainproject.group018.somojeon.join.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

public class JoinDto {

    @Getter
    @AllArgsConstructor
    public static class Post {
        private String content;

    }

    @Getter
    @AllArgsConstructor
    public static class Response {
        private String content;
        //        private List<UserDto.Response> userResponseDto;
    }

}
