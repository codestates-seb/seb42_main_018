package com.codestates.mainproject.group018.somojeon.team.dto;

import com.codestates.mainproject.group018.somojeon.user.dto.UserDto;
import lombok.*;

import java.util.List;

public class TeamDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Post {
        private Long clubId;
        private Long scheduleId;
        private Integer teamNumber;
        private Integer score;
        private String winLoseDraw;

        public void addClubId(Long clubId) {
            this.clubId = clubId;
        }

        public void addScheduleId(Long scheduleId) {
            this.scheduleId = scheduleId;
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Patch {
        private Long clubId;
        private Long scheduleId;
        private Long teamId;
        private Integer teamNumber;
        private Integer score;
        private String winLoseDraw;

        public void addTeamId(Long teamId) {
            this.teamId = teamId;
        }

        public void addClubId(Long clubId) {
            this.clubId = clubId;
        }

        public void addScheduleId(Long scheduleId) {
            this.scheduleId = scheduleId;
        }
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long teamId;
        private Integer teamNumber;
        private Integer score;
        private String winLoseDraw;
        private List<UserDto.Response> users;
    }
}
