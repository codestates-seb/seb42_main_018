package com.codestates.mainproject.group018.somojeon.schedule.controller;

import com.codestates.mainproject.group018.somojeon.dto.MultiResponseDto;
import com.codestates.mainproject.group018.somojeon.dto.SingleResponseDto;
import com.codestates.mainproject.group018.somojeon.schedule.dto.ScheduleDto;
import com.codestates.mainproject.group018.somojeon.schedule.entity.Schedule;
import com.codestates.mainproject.group018.somojeon.schedule.mapper.ScheduleMapper;
import com.codestates.mainproject.group018.somojeon.schedule.service.ScheduleService;
import com.codestates.mainproject.group018.somojeon.user.mapper.UserMapper;
import com.codestates.mainproject.group018.somojeon.utils.Identifier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;


@RestController
@Slf4j
@Validated
@RequestMapping
@CrossOrigin(value = {"https://dev.somojeon.site", "https://dev-somojeon.vercel.app"})
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;
    private final ScheduleMapper scheduleMapper;
    private final Identifier identifier;
    private final UserMapper userMapper;

    @PostMapping("/clubs/{club-id}/schedules")
    public ResponseEntity postSchedule(@PathVariable("club-id") @Positive long clubId,
                                       @Valid @RequestBody ScheduleDto.Post requestBody) {
        requestBody.addClubId(clubId);

//        if (identifier.checkClubRole(clubId)) {
//            throw new BusinessLogicException(ExceptionCode.ACCESS_DENIED);
//        };

        Schedule schedule = scheduleMapper.schedulePostDtoToSchedule(requestBody);

        Schedule createdSchedule = scheduleService.createSchedule(schedule, clubId, requestBody.getRecords(),
                requestBody.getTeamList(), requestBody.getCandidates());

        return new ResponseEntity<>(
                new SingleResponseDto<>(scheduleMapper.scheduleToScheduleResponseDto(createdSchedule, userMapper)),
                HttpStatus.CREATED);
    }

    @PutMapping("/clubs/{club-id}/schedules/{schedule-id}")
    public ResponseEntity putSchedule(@PathVariable("club-id") @Positive long clubId,
                                        @PathVariable("schedule-id") @Positive long scheduleId,
                                        @Valid @RequestBody ScheduleDto.Patch requestBody) {
        requestBody.addClubId(clubId);
        requestBody.addScheduleId(scheduleId);

//        if (identifier.checkClubRole(clubId)) {
//            throw new BusinessLogicException(ExceptionCode.ACCESS_DENIED);
//        };

        Schedule schedule = scheduleService.updateSchedule(scheduleMapper.schedulePatchDtoToSchedule(requestBody),
                requestBody.getRecords(), requestBody.getTeamList(), requestBody.getCandidates());

        return new ResponseEntity<>(
                new SingleResponseDto<>(scheduleMapper.scheduleToScheduleResponseDto(schedule, userMapper)), HttpStatus.OK);
    }

    @GetMapping("/clubs/{club-id}/schedules")
    public ResponseEntity getSchedulesByClub(@PathVariable("club-id") @Positive long clubId,
                                             @RequestParam(value = "page", defaultValue = "1") int page,
                                             @RequestParam(value = "size", defaultValue = "10") int size) {
        Page<Schedule> schedulePage = scheduleService.findSchedules(clubId, page - 1, size);
        List<Schedule> schedules = schedulePage.getContent();

        return new ResponseEntity<>(
                new MultiResponseDto<>(scheduleMapper.schedulesToScheduleResponseDtos(schedules), schedulePage),
        HttpStatus.OK);
    }

    @GetMapping("/clubs/{club-id}/schedules/{schedule-id}")
    public ResponseEntity getScheduleByClub(@PathVariable("club-id") @Positive long clubId,
                                            @PathVariable("schedule-id") @Positive long scheduleId) {
        Schedule schedule = scheduleService.findScheduleByClub(clubId, scheduleId);

        return new ResponseEntity<>(
                new SingleResponseDto<>(scheduleMapper.scheduleToScheduleResponseDto(schedule, userMapper)),
        HttpStatus.OK);
    }

    @DeleteMapping("/clubs/{club-id}/schedules/{schedule-id}")
    public ResponseEntity deleteSchedule(@PathVariable("club-id") @Positive long clubId,
                                         @PathVariable("schedule-id") @Positive long scheduleId) {
        scheduleService.deleteSchedule(scheduleId, clubId);

//        if (identifier.checkClubRole(clubId)) {
//            throw new BusinessLogicException(ExceptionCode.ACCESS_DENIED);
//        };

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
