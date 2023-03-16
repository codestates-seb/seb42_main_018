package com.codestates.mainproject.group018.somojeon.team.dto;

import com.codestates.mainproject.group018.somojeon.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

public class TeamDto {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long teamId;
        private Integer score;
        private String winLose;
        private List<User> users;
    }
}
