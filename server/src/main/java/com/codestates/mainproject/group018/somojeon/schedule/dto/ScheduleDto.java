package com.codestates.mainproject.group018.somojeon.schedule.dto;

import com.codestates.mainproject.group018.somojeon.candidate.dto.CandidateDto;
import com.codestates.mainproject.group018.somojeon.record.dto.RecordDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.geo.Point;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ScheduleDto {

    @Getter
    @AllArgsConstructor
    public static class Post {
        private LocalDate date;
        private String placeName;
        private Point point;
    }

    @Getter
    @AllArgsConstructor
    public static class Patch {
        private Long scheduleId;
        private LocalDate date;
        private String placeName;
        private Point point;

        public void addScheduleId(Long scheduleId) {
            this.scheduleId = scheduleId;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long scheduleId;
        private LocalDate date;
        private LocalDateTime createdAt;
        private String placeName;
        private Point point;
        private List<RecordDto.Response> records;
        private List<CandidateDto.Response> candidates;
    }
}
