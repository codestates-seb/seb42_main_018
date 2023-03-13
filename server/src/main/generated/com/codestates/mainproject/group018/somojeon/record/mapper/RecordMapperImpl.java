package com.codestates.mainproject.group018.somojeon.record.mapper;

import com.codestates.mainproject.group018.somojeon.comment.dto.CommentDto;
import com.codestates.mainproject.group018.somojeon.comment.entity.Comment;
import com.codestates.mainproject.group018.somojeon.record.dto.RecordDto;
import com.codestates.mainproject.group018.somojeon.record.dto.UserRecordDto;
import com.codestates.mainproject.group018.somojeon.record.entity.Record;
import com.codestates.mainproject.group018.somojeon.record.entity.UserRecord;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-03-13T00:17:09+0900",
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

        record.setDate( requestBody.getDate() );
        record.setPlace( requestBody.getPlace() );

        return record;
    }

    @Override
    public Record recordPatchDtoToRecord(RecordDto.Patch requestBody) {
        if ( requestBody == null ) {
            return null;
        }

        Record record = new Record();

        record.setRecordId( requestBody.getRecordId() );
        record.setDate( requestBody.getDate() );
        record.setPlace( requestBody.getPlace() );

        return record;
    }

    @Override
    public RecordDto.Response recordToRecordResponseDto(Record record) {
        if ( record == null ) {
            return null;
        }

        RecordDto.Response response = new RecordDto.Response();

        response.setRecordId( record.getRecordId() );
        response.setPlace( record.getPlace() );
        response.setDate( record.getDate() );
        response.setCreatedAt( record.getCreatedAt() );
        response.setUserRecords( userRecordListToResponseList( record.getUserRecords() ) );
        response.setComments( commentListToResponseList( record.getComments() ) );

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

    protected UserRecordDto.Response userRecordToResponse(UserRecord userRecord) {
        if ( userRecord == null ) {
            return null;
        }

        UserRecordDto.Response response = new UserRecordDto.Response();

        response.setUserRecordId( userRecord.getUserRecordId() );
        response.setTeamName( userRecord.getTeamName() );
        response.setScore( userRecord.getScore() );
        response.setWinLose( userRecord.getWinLose() );

        return response;
    }

    protected List<UserRecordDto.Response> userRecordListToResponseList(List<UserRecord> list) {
        if ( list == null ) {
            return null;
        }

        List<UserRecordDto.Response> list1 = new ArrayList<UserRecordDto.Response>( list.size() );
        for ( UserRecord userRecord : list ) {
            list1.add( userRecordToResponse( userRecord ) );
        }

        return list1;
    }

    protected CommentDto.Response commentToResponse(Comment comment) {
        if ( comment == null ) {
            return null;
        }

        CommentDto.Response response = new CommentDto.Response();

        response.setCommentId( comment.getCommentId() );
        response.setContent( comment.getContent() );
        response.setCreatedAt( comment.getCreatedAt() );

        return response;
    }

    protected List<CommentDto.Response> commentListToResponseList(List<Comment> list) {
        if ( list == null ) {
            return null;
        }

        List<CommentDto.Response> list1 = new ArrayList<CommentDto.Response>( list.size() );
        for ( Comment comment : list ) {
            list1.add( commentToResponse( comment ) );
        }

        return list1;
    }
}
