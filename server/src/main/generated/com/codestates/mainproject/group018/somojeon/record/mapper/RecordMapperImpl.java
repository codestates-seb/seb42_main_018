package com.codestates.mainproject.group018.somojeon.record.mapper;

import com.codestates.mainproject.group018.somojeon.record.dto.RecordDto;
import com.codestates.mainproject.group018.somojeon.record.entity.Record;
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
public class RecordMapperImpl implements RecordMapper {

    @Override
    public Record recordPostDtoToRecord(RecordDto.Post requestBody) {
        if ( requestBody == null ) {
            return null;
        }

        Record record = new Record();

        return record;
    }

    @Override
    public Record recordPatchDtoToRecord(RecordDto.Patch requestBody) {
        if ( requestBody == null ) {
            return null;
        }

        Record record = new Record();

        return record;
    }

    @Override
    public RecordDto.Response recordToRecordResponseDto(Record record) {
        if ( record == null ) {
            return null;
        }

        RecordDto.Response response = new RecordDto.Response();

        return response;
    }

    @Override
    public List<RecordDto.Response> recordsToRecordResponseDtos(List<Record> records) {
        if ( records == null ) {
            return null;
        }

        List<RecordDto.Response> list = new ArrayList<RecordDto.Response>( records.size() );
        for ( Record record : records ) {
            list.add( recordToRecordResponseDto( record ) );
        }

        return list;
    }
}
