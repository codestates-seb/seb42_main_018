package com.codestates.mainproject.group018.somojeon.tag.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class TagDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {

        private Long tagId;
        private String tagName;
    }

}
