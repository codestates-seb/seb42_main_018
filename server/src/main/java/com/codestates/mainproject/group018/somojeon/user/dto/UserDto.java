package com.codestates.mainproject.group018.somojeon.user.dto;

import com.codestates.mainproject.group018.somojeon.club.dto.UserClubDto;
import com.codestates.mainproject.group018.somojeon.images.dto.ImagesResponseDto;
import com.codestates.mainproject.group018.somojeon.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.List;

public class UserDto {
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Post{
        @Nullable
        String nickName;

        @Email
        String email;

        @Nullable
        String password;

        private Long profileImageId;

//        @Nullable
//        int age;

    }
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Patch{
        Long userId;

        @Nullable
        String nickName;

        private Long profileImageId;

//        @Nullable
//        String password;

    }
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response{
        Long userId;

        @NotNull
        String nickName;

        @Email
        String email;

       User.UserStatus userStatus;

        private ImagesResponseDto profileImage;

        public String getUserStatus() {
            return userStatus.getStatus();
        }

    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResponseWithClubs {
        Long userId;

        @NotNull
        String nickName;

        @Email
        String email;

        User.UserStatus userStatus;

        private ImagesResponseDto profileImage;

        List<UserClubDto.Response> userClubResponses;


        public String getUserStatus() {
            return userStatus.getStatus();
        }

    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResponseWithClub {
        String nickName;
        private ImagesResponseDto profileImage;
        private Integer playCount;
        private Integer winCount;
        private Integer loseCount;
        private Integer drawCount;
        private float winRate;


    }
}
