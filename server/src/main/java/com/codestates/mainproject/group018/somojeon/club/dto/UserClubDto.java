package com.codestates.mainproject.group018.somojeon.club.dto;

import com.codestates.mainproject.group018.somojeon.club.enums.ClubMemberStatus;
import com.codestates.mainproject.group018.somojeon.club.enums.ClubRole;
import com.codestates.mainproject.group018.somojeon.club.enums.JoinStatus;
import com.codestates.mainproject.group018.somojeon.level.etity.Level;
import com.codestates.mainproject.group018.somojeon.user.dto.UserDto;
import lombok.*;

import javax.validation.constraints.Positive;
import java.util.List;

public class UserClubDto {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JoinPost {

//        @Positive
        private Long userId;
        private Long clubId;
        private String content;

        public void addUserId(Long userId) {
            this.userId = userId;
        }

        public void addClubId(Long clubId) {
            this.clubId = clubId;
        }
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MemberStatusPatch {

        private Long userId;
        private Long clubId;
        private ClubMemberStatus clubMemberStatus;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ClubRolePatch {

        private Long userId;
        private Long clubId;
        private ClubRole clubRole;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JoinDecisionPatch{

        private Long userId;
        private Long clubId;
        private JoinStatus joinStatus;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {

        private Long clubId;
        private ClubRole clubRole;
        private ClubMemberStatus clubMemberStatus;
        private boolean isPlayer;
        private Level level;
        private int playCount;
        private int winCount;
        private int winRate;

    }
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResponseWithUser {

        private Long clubId;
        private ClubRole clubRole;
        private ClubMemberStatus clubMemberStatus;
        private boolean isPlayer;
        private Level level;
        private int playCount;
        private int winCount;
        private int winRate;
        private UserDto.UserInfoResponse userInfo;

    }


    @Getter
    @Setter
    @NoArgsConstructor
    public static class JoinResponse {

        private Long userClubId;
        private String content;
        private ClubRole clubRole;
        private UserDto.UserInfoResponse userInfo;
        private JoinStatus joinStatus;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class UserClubMemberResponse {

        private Long userClubId;
        private ClubMemberStatus clubMemberStatus;
        private UserDto.UserInfoResponse userInfo;

    }

}
