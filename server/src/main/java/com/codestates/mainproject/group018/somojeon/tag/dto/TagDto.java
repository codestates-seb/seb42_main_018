package com.codestates.mainproject.group018.somojeon.tag.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class TagDto {

    @Getter
    @AllArgsConstructor
    public static class Response {

        private Long tagId;
        private String tagName;
    }

}
