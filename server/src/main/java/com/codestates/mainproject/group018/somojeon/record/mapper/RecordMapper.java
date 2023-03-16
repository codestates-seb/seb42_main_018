package com.codestates.mainproject.group018.somojeon.record.mapper;

import com.codestates.mainproject.group018.somojeon.record.dto.RecordDto;
import com.codestates.mainproject.group018.somojeon.record.entity.Record;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RecordMapper {
//    Record recordPostDtoToRecord(RecordDto.Post requestBody);
//    Record recordPatchDtoToRecord(RecordDto.Patch requestBody);
    RecordDto.Response recordToRecordResponseDto(Record record);
    List<RecordDto.Response> recordsToRecordResponseDtos(List<Record> records);
}
