package com.codestates.mainproject.group018.somojeon.User.dto;

import com.codestates.mainproject.group018.somojeon.User.Entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
public class UserDto {
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Post{
        @NotNull
        String name;

        @Email
        String email;

        @NotNull
        String password;

        @Nullable
        int age;

    }
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Patch{
        Long userId;

        @Nullable
        String name;

//        @Nullable
//        String password;

        @Nullable
        int age;

        int voteCount;


        @Nullable
        User.UserStatus userStatus;


        public void setUserId(Long userId) {
            this.userId = userId;
        }
    }
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response{
        Long userId;

        @NotNull
        String name;

        @Email
        String email;


        @Nullable
        int age;

       User.UserStatus userStatus;

        public String getUserStatus() {
            return userStatus.getStatus();
        }

    }
}
