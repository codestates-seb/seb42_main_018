package com.codestates.mainproject.group018.somojeon.team.dto;

import com.codestates.mainproject.group018.somojeon.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

public class TeamDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Post {
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
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long teamId;
        private Integer score;
        private String winLoseDraw;
        private List<User> users;
    }
}
