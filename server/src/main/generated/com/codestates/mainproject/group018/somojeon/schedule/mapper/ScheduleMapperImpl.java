package com.codestates.mainproject.group018.somojeon.schedule.mapper;

import com.codestates.mainproject.group018.somojeon.schedule.dto.ScheduleDto;
import com.codestates.mainproject.group018.somojeon.schedule.entity.Schedule;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-03-09T16:50:35+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 11.0.17 (Azul Systems, Inc.)"
)
@Component
public class ScheduleMapperImpl implements ScheduleMapper {

    @Override
    public Schedule schedulePostDtoToSchedule(ScheduleDto.Post requestBody) {
        if ( requestBody == null ) {
            return null;
        }

        Schedule schedule = new Schedule();

        return schedule;
    }

    @Override
    public Schedule schedulePatchDtoToSchedule(ScheduleDto.Patch requestBody) {
        if ( requestBody == null ) {
            return null;
        }

        Schedule schedule = new Schedule();

        return schedule;
    }

    @Override
    public ScheduleDto.Response scheduleToScheduleResponseDto(Schedule schedule) {
        if ( schedule == null ) {
            return null;
        }

        ScheduleDto.Response response = new ScheduleDto.Response();

        return response;
    }

    @Override
    public List<ScheduleDto.Response> schedulesToScheduleResponseDtos(List<Schedule> schedules) {
        if ( schedules == null ) {
            return null;
        }

        List<ScheduleDto.Response> list = new ArrayList<ScheduleDto.Response>( schedules.size() );
        for ( Schedule schedule : schedules ) {
            list.add( scheduleToScheduleResponseDto( schedule ) );
        }

        return list;
    }
}
