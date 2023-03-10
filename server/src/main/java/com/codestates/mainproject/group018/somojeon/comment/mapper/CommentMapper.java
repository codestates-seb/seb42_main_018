package com.codestates.mainproject.group018.somojeon.comment.mapper;

import com.codestates.mainproject.group018.somojeon.comment.dto.CommentDto;
import com.codestates.mainproject.group018.somojeon.comment.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {
    Comment commentPostDtoToComment(CommentDto.Post requestBody);
    Comment commentPatchDtoToComment(CommentDto.Patch requestBody);
    CommentDto.Response commentToCommentResponseDto(Comment comment);
    List<CommentDto.Response> commentsToCommentResponseDtos(List<Comment> comments);
}
