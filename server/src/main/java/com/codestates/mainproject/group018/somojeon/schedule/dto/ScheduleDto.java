package com.codestates.mainproject.group018.somojeon.schedule.dto;

import com.codestates.mainproject.group018.somojeon.candidate.dto.CandidateDto;
import com.codestates.mainproject.group018.somojeon.record.dto.RecordDto;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class ScheduleDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Post {

        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDate date;

        @DateTimeFormat(pattern = "HH:mm:ss")
        private LocalTime time;

        private String placeName;
        private Double longitude;
        private Double latitude;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Patch {
        private Long scheduleId;

        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDate date;

        @DateTimeFormat(pattern = "HH:mm:ss")
        private LocalTime time;

        private String placeName;
        private Double longitude;
        private Double latitude;

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
        private Long scheduleId;
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDate date;
        @DateTimeFormat(pattern = "HH:mm:ss")
        private LocalTime time;
        private LocalDateTime createdAt;
        private String placeName;
        private Double longitude;
        private Double latitude;
        private List<RecordDto.Response> records;
        private List<CandidateDto.Response> candidates;
    }
}
