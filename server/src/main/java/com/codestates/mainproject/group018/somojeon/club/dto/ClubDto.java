package com.codestates.mainproject.group018.somojeon.club.dto;

import com.codestates.mainproject.group018.somojeon.club.enums.ClubPrivateStatus;
import com.codestates.mainproject.group018.somojeon.club.enums.ClubStatus;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.List;

public class ClubDto {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Post {

        private Long userId;

        private String clubName;

        private String content;

        @Pattern(regexp = "^[가-힣 가-힣]*$",
                message = "시/도 와 시/군/구 사이는 띄어쓰기 한개가 꼭 필요합니다.")
        private String local;

        @NotBlank(message = "카테고리 이름은 공백이 아니여야 합니다.")
        @Pattern(regexp = "^[가-힣]*$",
                message = "카테고리 이름은 한글로만 사용가능 합니다.")
        private String categoryName;
        private List<String> tagList;
        private ClubPrivateStatus clubPrivateStatus;

    }
    @Getter
    @Setter
    @AllArgsConstructor
    public static class PostPlayers {
        Long clubId;
        List<Long> playerUserIds;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Patch {

        private Long clubId;

        private String clubName;

        private String content;

        @Pattern(regexp = "^[가-힣 가-힣]*$",
                message = "시/도 와 시/군/구 사이는 띄어쓰기 한개가 꼭 필요합니다.")
        private String local;
        private List<String> tagList;
        private ClubPrivateStatus clubPrivateStatus;

    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class StatusPatch {

        private Long clubId;
        private ClubStatus clubStatus;
    }



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
        private List<String> tagList;
        private ClubPrivateStatus clubPrivateStatus;
        private int viewCount;
        private int memberCount;
        private String clubImage;
        private ClubStatus clubStatus;
        private LocalDateTime modifiedAt;

    }


}
