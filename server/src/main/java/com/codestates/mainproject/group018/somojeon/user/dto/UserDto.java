package com.codestates.mainproject.group018.somojeon.user.dto;

import com.codestates.mainproject.group018.somojeon.user.entity.User;
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
        @Nullable
        String nickName;

        @Email
        String email;

        @NotNull
        String password;

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
        String userName;

//        @Nullable
//        String password;



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
        String nickName;

        @Email
        String email;

       User.UserStatus userStatus;

        public String getUserStatus() {
            return userStatus.getStatus();
        }

    }
}
