package com.codestates.mainproject.group018.somojeon.club.dto;

import com.codestates.mainproject.group018.somojeon.category.dto.CategoryDto;
import com.codestates.mainproject.group018.somojeon.tag.dto.TagDto;
import com.codestates.mainproject.group018.somojeon.tag.entity.Tag;
import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

public class ClubDto {

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Post {

        @NotBlank(message = "소모임 이름은 공백이 아니여야 합니다.")
        @Pattern(regexp = "^[가-힣]*$",
                message = "카테고리 이름은 특수문자를 사용할 수 없습니다.")
        private String clubName;

        private String content;

        @Pattern(regexp = "^[가-힣 가-힣]*$",
                message = "시/도 와 시/군/구 사이는 띄어쓰기 한개가 꼭 필요합니다.")
        private String local;

        @NotBlank(message = "카테고리 이름은 공백이 아니여야 합니다.")
        @Pattern(regexp = "^[가-힣]*$",
                message = "카테고리 이름은 한글로만 사용가능 합니다.")
        private String categoryName;

//        @Pattern(regexp = "^[a-zA-Z0-9가-힣]$", message = "Tag 이름은 특수문자가 아니여야 합니다.")
        private List<String> tagName;
        private boolean isPrivate;

    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Patch {

        private Long clubId;

        @NotBlank(message = "소모임 이름은 공백이 아니여야 합니다.")
        @Pattern(regexp = "^[가-힣]*$",
                message = "카테고리 이름은 특수문자를 사용할 수 없습니다.")
        private String clubName;

        private String content;

        @Pattern(regexp = "^[가-힣 가-힣]*$",
                message = "시/도 와 시/군/구 사이는 띄어쓰기 한개가 꼭 필요합니다.")
        private String local;

//        @Pattern(regexp = "\\w+", message = "Tag 이름은 특수문자가 아니여야 합니다.")
        private List<String> tagName;
        private boolean isPrivate;
    }

    //TODO-DW:회원등급 Patch,

    //TODO-DW:소모임장 위임 Patch

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {

        private Long clubId;
        private String clubName;
        private String content;
        private String local;
        private String categoryName;
        private List<TagDto.Response> tagResponseDtos;
        private boolean isPrivate;
        private LocalDateTime modifiedAt;

    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetResponse {
        private Long clubId;
        private String clubName;
        private String content;
        private String local;
        private String categoryName;
        private int viewCount;
        private int memberCount;
        private List<TagDto.Response> tagResponseDtos;
        private boolean isPrivate;
        private LocalDateTime modifiedAt;

    }


    //TODO-DW: myResponse 는 아마도 따로해야 할듯?


}
