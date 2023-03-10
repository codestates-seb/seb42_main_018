package com.codestates.mainproject.group018.somojeon.record.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class UserRecordDto {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {
        private Long userRecordId;
        private String teamName;
        private int score;
        private String winLose;

    }
}
