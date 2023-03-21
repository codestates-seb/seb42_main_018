package com.codestates.mainproject.group018.somojeon.schedule.controller;

import com.codestates.mainproject.group018.somojeon.dto.SingleResponseDto;
import com.codestates.mainproject.group018.somojeon.exception.BusinessLogicException;
import com.codestates.mainproject.group018.somojeon.exception.ExceptionCode;
import com.codestates.mainproject.group018.somojeon.schedule.dto.ScheduleDto;
import com.codestates.mainproject.group018.somojeon.schedule.entity.Schedule;
import com.codestates.mainproject.group018.somojeon.schedule.mapper.ScheduleMapper;
import com.codestates.mainproject.group018.somojeon.schedule.service.ScheduleService;
import com.codestates.mainproject.group018.somojeon.utils.Identifier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;


@RestController
@Slf4j
@Validated
@RequestMapping
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;
    private final ScheduleMapper scheduleMapper;
    private final Identifier identifier;

    @PostMapping("/clubs/{club-id}/schedules")
    public ResponseEntity postSchedule(@PathVariable("club-id") @Positive long clubId,
                                       @Valid @RequestBody ScheduleDto.Post requestBody) {
        requestBody.addClubId(clubId);

        if (!identifier.checkClubRole(clubId)) {
            throw new BusinessLogicException(ExceptionCode.ACCESS_DENIED);
        };

        Schedule schedule = scheduleMapper.schedulePostDtoToSchedule(requestBody);

        Schedule createdSchedule = scheduleService.createSchedule(schedule, clubId);

        return new ResponseEntity<>(
                new SingleResponseDto<>(scheduleMapper.scheduleToScheduleResponseDto(createdSchedule)),
                HttpStatus.CREATED);
    }

    @PatchMapping("/clubs/{club-id}/schedules/{schedule-id}")
    public ResponseEntity patchSchedule(@PathVariable("club-id") @Positive long clubId,
                                        @PathVariable("schedule-id") @Positive long scheduleId,
                                        @Valid @RequestBody ScheduleDto.Patch requestBody) {
        requestBody.addClubId(clubId);
        requestBody.addScheduleId(scheduleId);

        if (!identifier.checkClubRole(clubId)) {
            throw new BusinessLogicException(ExceptionCode.ACCESS_DENIED);
        };

        Schedule schedule = scheduleService.updateSchedule(scheduleMapper.schedulePatchDtoToSchedule(requestBody));

        return new ResponseEntity<>(
                new SingleResponseDto<>(scheduleMapper.scheduleToScheduleResponseDto(schedule)), HttpStatus.OK);
    }

//    @GetMapping("/schedules/{schedule-id}")
//    public ResponseEntity getSchedule(@PathVariable("schedule-id") @Positive long scheduleId) {
//        Schedule schedule = scheduleService.findSchedule(scheduleId);
//
//        return new ResponseEntity<>(
//                new SingleResponseDto<>(scheduleMapper.scheduleToScheduleResponseDto(schedule)), HttpStatus.OK);
//    }
//
//    @GetMapping("/schedules")
//    public ResponseEntity getSchedules(@RequestParam("page") int page,
//                                       @RequestParam("size") int size) {
//        Page<Schedule> pageSchedules = scheduleService.findSchedules(page - 1, size);
//        List<Schedule> schedules = pageSchedules.getContent();
//
//        return new ResponseEntity<>(
//                new MultiResponseDto<>(scheduleMapper.schedulesToScheduleResponseDtos(schedules), pageSchedules),
//                HttpStatus.OK);
//    }

    @DeleteMapping("/clubs/{club-id}/schedules/{schedule-id}")
    public ResponseEntity deleteSchedule(@PathVariable("club-id") @Positive long clubId,
                                         @PathVariable("schedule-id") @Positive long scheduleId) {
        scheduleService.deleteSchedule(scheduleId);

        if (!identifier.checkClubRole(clubId)) {
            throw new BusinessLogicException(ExceptionCode.ACCESS_DENIED);
        };

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
