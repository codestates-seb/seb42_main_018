package com.codestates.mainproject.group018.somojeon.record.mapper;

import com.codestates.mainproject.group018.somojeon.comment.dto.CommentDto;
import com.codestates.mainproject.group018.somojeon.comment.entity.Comment;
import com.codestates.mainproject.group018.somojeon.record.dto.RecordDto;
import com.codestates.mainproject.group018.somojeon.record.entity.Record;
import com.codestates.mainproject.group018.somojeon.team.dto.TeamDto;
import com.codestates.mainproject.group018.somojeon.team.entity.TeamRecord;
import com.codestates.mainproject.group018.somojeon.team.entity.UserTeam;
import com.codestates.mainproject.group018.somojeon.user.entity.User;
import com.codestates.mainproject.group018.somojeon.user.mapper.UserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

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
                .teams(teamRecordsToTeamRecordResponseDtos(record.getTeamRecords(), userMapper))
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

    default List<TeamDto.Response> teamRecordsToTeamRecordResponseDtos(List<TeamRecord> teamRecords,
                                                                       UserMapper userMapper) {
        return teamRecords.stream()
                .map(teamRecord -> {
                    TeamDto.Response response = new TeamDto.Response();
                    response.setTeamId(teamRecord.getTeam().getTeamId());
                    response.setScore(teamRecord.getTeam().getScore());
                    response.setWinLoseDraw(teamRecord.getTeam().getWinLoseDraw());

                    List<UserTeam> userTeams = teamRecord.getTeam().getUserTeams();
                    List<User> users = userTeams.stream()
                            .map(userTeam -> userTeam.getUser())
                            .collect(Collectors.toList());

                    response.setUsers(userMapper.usersToUserResponses(users));



                    return response;
                })
                .collect(Collectors.toList());
    }

    List<RecordDto.Response> recordsToRecordResponseDtos(List<Record> records);
}
