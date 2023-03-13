package com.codestates.mainproject.group018.somojeon.category.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

public class CategoryDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Post {

        @NotBlank(message = "카테고리 이름은 공백이 아니여야 합니다.")
        @Pattern(regexp = "^[가-힣]*$",
                message = "카테고리 이름은 한글로만 사용가능 합니다.")
        private String categoryName;

    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {

        private Long categoryId;
        private String categoryName;
        private LocalDateTime createdAt;

    }

}
