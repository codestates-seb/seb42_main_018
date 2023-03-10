package com.codestates.mainproject.group018.somojeon.join.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class JoinDto {

    @Getter
    @AllArgsConstructor
    public static class Post {
        private String content;

    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private String content;
//                private List<UserDto.Response> userResponseDto;
    }

}
