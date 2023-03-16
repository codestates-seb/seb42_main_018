package com.codestates.mainproject.group018.somojeon.tag.dto;

import lombok.*;

public class TagDto {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {

        private Long tagId;
        private String tagName;
    }

}
