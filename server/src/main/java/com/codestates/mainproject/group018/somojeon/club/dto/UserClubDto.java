package com.codestates.mainproject.group018.somojeon.club.dto;

import com.codestates.mainproject.group018.somojeon.club.enums.ClubRole;
import com.codestates.mainproject.group018.somojeon.level.etity.Level;
import lombok.*;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class UserClubDto {
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {

        private Long userClubId;

        private boolean isPlayer;

        private ClubRole clubRole;

        private Level level;

        private int playCount;

        private int winCount;

        private int winRate;

    }
}
