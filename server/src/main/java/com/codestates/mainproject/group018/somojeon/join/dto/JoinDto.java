package com.codestates.mainproject.group018.somojeon.join.dto;

import com.codestates.mainproject.group018.somojeon.join.enums.JoinDecisionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
