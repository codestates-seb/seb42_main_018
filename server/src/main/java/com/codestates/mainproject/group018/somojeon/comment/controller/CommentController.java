package com.codestates.mainproject.group018.somojeon.comment.controller;

import com.codestates.mainproject.group018.somojeon.comment.dto.CommentDto;
import com.codestates.mainproject.group018.somojeon.comment.entity.Comment;
import com.codestates.mainproject.group018.somojeon.comment.mapper.CommentMapper;
import com.codestates.mainproject.group018.somojeon.comment.service.CommentService;
import com.codestates.mainproject.group018.somojeon.dto.MultiResponseDto;
import com.codestates.mainproject.group018.somojeon.dto.SingleResponseDto;
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

@Slf4j
@Validated
@RestController
@RequestMapping
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final CommentMapper commentMapper;

    // TODO-ET: identifier 구현
    @PostMapping("/records/{record-id}/comments")
    public ResponseEntity postComment(@PathVariable("record-id") @Positive long recordId,
                                      @Valid @RequestBody CommentDto.Post requestBody) {
        requestBody.addRecordId(recordId);

        Comment comment = commentMapper.commentPostDtoToComment(requestBody);

        Comment createdComment = commentService.createComment(comment, recordId);


        return new ResponseEntity<>(
                new SingleResponseDto<>(commentMapper.commentToCommentResponseDto(createdComment)),
                HttpStatus.CREATED);
    }

    @PatchMapping("/records/{record-id}/comments/{comment-id}")
    public ResponseEntity patchComment(@PathVariable("record-id") @Positive long recordId,
                                       @PathVariable("comment-id") @Positive long commentId,
                                       @Valid @RequestBody CommentDto.Patch requestBody) {
        requestBody.addRecordId(recordId);
        requestBody.addCommentId(commentId);

        Comment comment = commentService.updateComment(commentMapper.commentPatchDtoToComment(requestBody));

        return new ResponseEntity<>(
                new SingleResponseDto<>(commentMapper.commentToCommentResponseDto(comment)), HttpStatus.OK);
    }

    @GetMapping("/comments/{comment-id}")
    public ResponseEntity getComment(@PathVariable("comment-id") @Positive long commentId) {
        Comment comment = commentService.findComment(commentId);

        return new ResponseEntity<>(
                new SingleResponseDto<>(commentMapper.commentToCommentResponseDto(comment)), HttpStatus.OK);
    }

    @GetMapping("/records/{record-id}/comments")
    public ResponseEntity getComments(@PathVariable("record-id") @Positive long recordId,
                                      @RequestParam("page") int page,
                                      @RequestParam("size") int size) {
        Page<Comment> pageComments = commentService.findComments(page - 1, size, recordId);
        List<Comment> comments = pageComments.getContent();

        return new ResponseEntity<>(
                new MultiResponseDto<>(commentMapper.commentsToCommentResponseDtos(comments), pageComments),
                HttpStatus.OK);
    }

    @DeleteMapping("/comments/{comment-id}")
    public ResponseEntity deleteComment(@PathVariable("comment-id") @Positive long commentId) {
        commentService.deleteComment(commentId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
