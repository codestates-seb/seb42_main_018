package com.codestates.mainproject.group018.somojeon.schedule.controller;

import com.codestates.mainproject.group018.somojeon.dto.MultiResponseDto;
import com.codestates.mainproject.group018.somojeon.dto.SingleResponseDto;
import com.codestates.mainproject.group018.somojeon.schedule.dto.ScheduleDto;
import com.codestates.mainproject.group018.somojeon.schedule.entity.Schedule;
import com.codestates.mainproject.group018.somojeon.schedule.mapper.ScheduleMapper;
import com.codestates.mainproject.group018.somojeon.schedule.service.ScheduleService;
import com.codestates.mainproject.group018.somojeon.utils.UriCreator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping("/schedules")
public class ScheduleController {
    private final ScheduleService scheduleService;
    private final ScheduleMapper scheduleMapper;

    public ScheduleController(ScheduleService scheduleService, ScheduleMapper scheduleMapper) {
        this.scheduleService = scheduleService;
        this.scheduleMapper = scheduleMapper;
    }

    @PostMapping
    public ResponseEntity postSchedule(@Valid @RequestBody ScheduleDto.Post requestBody) {
        Schedule schedule = scheduleMapper.schedulePostDtoToSchedule(requestBody);

        Schedule createdSchedule = scheduleService.createSchedule(schedule);
        URI location = UriCreator.createUri("/schedules", createdSchedule.getScheduleId());

        return ResponseEntity.created(location).build();
    }

    @PatchMapping("/{schedule-id}")
    public ResponseEntity patchSchedule(@PathVariable("schedule-id") @Positive long scheduleId,
                                        @Valid @RequestBody ScheduleDto.Patch requestBody) {
        requestBody.addScheduleId(scheduleId);

        Schedule schedule = scheduleService.updateSchedule(scheduleMapper.schedulePatchDtoToSchedule(requestBody));

        return new ResponseEntity<>(
                new SingleResponseDto<>(scheduleMapper.scheduleToScheduleResponseDto(schedule)), HttpStatus.OK);
    }

    @GetMapping("/{schedule-id}")
    public ResponseEntity getSchedule(@PathVariable("schedule-id") @Positive long scheduleId) {
        Schedule schedule = scheduleService.findSchedule(scheduleId);

        return new ResponseEntity<>(
                new SingleResponseDto<>(scheduleMapper.scheduleToScheduleResponseDto(schedule)), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getSchedules(@RequestParam("page") int page,
                                       @RequestParam("size") int size) {
        Page<Schedule> pageSchedules = scheduleService.findSchedules(page - 1, size);
        List<Schedule> schedules = pageSchedules.getContent();

        return new ResponseEntity<>(
                new MultiResponseDto<>(scheduleMapper.schedulesToScheduleResponseDtos(schedules), pageSchedules),
                HttpStatus.OK);
    }

    @DeleteMapping("/{schedule-id}")
    public ResponseEntity deleteSchedule(@PathVariable("schedule-id") @Positive long scheduleId) {
        scheduleService.deleteSchedule(scheduleId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
