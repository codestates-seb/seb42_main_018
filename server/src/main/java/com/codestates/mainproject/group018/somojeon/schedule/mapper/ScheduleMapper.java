package com.codestates.mainproject.group018.somojeon.schedule.mapper;

import com.codestates.mainproject.group018.somojeon.schedule.dto.ScheduleDto;
import com.codestates.mainproject.group018.somojeon.schedule.entity.Schedule;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ScheduleMapper {
    Schedule schedulePostDtoToSchedule(ScheduleDto.Post requestBody);
    Schedule schedulePatchDtoToSchedule(ScheduleDto.Patch requsetBody);
    ScheduleDto.Response scheduleToScheduleResponseDto(Schedule schedule);
    List<ScheduleDto.Response> schedulesToScheduleResponseDtos(List<Schedule> schedules);
}
