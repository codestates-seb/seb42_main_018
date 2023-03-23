package com.codestates.mainproject.group018.somojeon.record.mapper;

import com.codestates.mainproject.group018.somojeon.comment.dto.CommentDto;
import com.codestates.mainproject.group018.somojeon.comment.entity.Comment;
import com.codestates.mainproject.group018.somojeon.record.dto.RecordDto;
import com.codestates.mainproject.group018.somojeon.record.entity.Record;
import com.codestates.mainproject.group018.somojeon.user.mapper.UserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {UserMapper.class})
public interface RecordMapper {
    Record recordPostDtoToRecord(RecordDto.Post requestBody);
    Record recordPatchDtoToRecord(RecordDto.Patch requestBody);
//    RecordDto.Response recordToRecordResponseDto(Record record);
    default RecordDto.Response recordToRecordResponseDto(Record record, UserMapper userMapper) {
        if (record == null) {
            return null;
        }

        return RecordDto.Response
                .builder()
                .recordId(record.getRecordId())
                .createdAt(record.getCreatedAt())
                .firstTeam(record.getFirstTeam())
                .firstTeamScore(record.getFirstTeamScore())
                .secondTeam(record.getSecondTeam())
                .secondTeamScore(record.getSecondTeamScore())
                .comments(commentsToCommentResponseDtos(record.getComments()))
                .build();
    }

    default List<CommentDto.Response> commentsToCommentResponseDtos(List<Comment> comments) {
        return comments.stream()
                .map(comment -> {
                    CommentDto.Response response = new CommentDto.Response();
                    response.setCommentId(comment.getCommentId());
//                    response.setUserId(comment.getUser().getUserId());
//                    response.setNickName(comment.getUser().getNickName());
//                    response.setImages(comment.getUser().getImages());
                    response.setContent(comment.getContent());
                    response.setCreatedAt(comment.getCreatedAt());

                    return response;
                })
                .collect(Collectors.toList());
    }

    List<RecordDto.Response> recordsToRecordResponseDtos(List<Record> records);
}
