package com.codestates.mainproject.group018.somojeon.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ScheduleDto {

    @Getter
    @AllArgsConstructor
    public static class Post {

        private LocalDate date;

        private String place;
    }

    @Getter
    @AllArgsConstructor
    public static class Patch {

        private Long scheduleId;

        private LocalDate date;

        private String place;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long scheduleId;
        private LocalDate date;
        private String place;
        private LocalDateTime createdAt;
    }
}
