package com.codestates.mainproject.group018.somojeon.join.dto;

import com.codestates.mainproject.group018.somojeon.join.enums.JoinDecisionStatus;
import lombok.*;

public class JoinDto {

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Post {
        private String content;
        private Long clubId;
    }

    @Getter
    @AllArgsConstructor
    public static class DecisionPatch {
        private Long joinsId;
        private Long clubId;
        private String decision;

        public void addJoinsId(Long joinsId) {
            this.joinsId = joinsId;
        }
    }


    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private String content;
//                private List<UserDto.Response> userResponseDto;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DecisionResponse {
        private Long userId;
        private JoinDecisionStatus joinDecisionStatus;
    }

}
