package com.codestates.mainproject.group018.somojeon.team.dto;

import com.codestates.mainproject.group018.somojeon.user.dto.UserDto;
import lombok.*;

import java.util.List;

public class TeamDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Post {
        private Long recordId;
        private Integer score;
        private String winLoseDraw;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Patch {
        private Long teamId;
        private Integer score;
        private String winLoseDraw;

        public void addTeamId(Long teamId) {
            this.teamId = teamId;
        }
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long teamId;
        private Integer score;
        private String winLoseDraw;
        private List<UserDto.Response> users;
    }
}
