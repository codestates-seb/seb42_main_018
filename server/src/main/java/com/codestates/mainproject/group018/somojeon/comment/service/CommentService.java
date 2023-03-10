package com.codestates.mainproject.group018.somojeon.comment.service;

import com.codestates.mainproject.group018.somojeon.comment.entity.Comment;
import com.codestates.mainproject.group018.somojeon.comment.repository.CommentRepository;
import com.codestates.mainproject.group018.somojeon.record.service.RecordService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;
    private final RecordService recordService;

    public CommentService(CommentRepository commentRepository, RecordService recordService) {
        this.commentRepository = commentRepository;
        this.recordService = recordService;
    }

    public Comment createComment(Comment comment, Long recordId) {
        recordService.findVerifiedRecord(recordId); // 경기 전적 확인

        return commentRepository.save(comment);
    }

    public Comment updateComment(Comment comment) {
        Comment findComment = findVerifiedComment(comment.getCommentId());

        Optional.ofNullable(comment.getContent())
                .ifPresent(findComment::setContent);

        return commentRepository.save(comment);
    }

    public Comment findComment(long commentId) {
        return findVerifiedComment(commentId);
    }

    public Page<Comment> findComments(int page, int size) {
        return commentRepository.findAll(PageRequest.of(page, size, Sort.by("commentId").descending()));
    }

    public void deleteComment(long commentId) {
        Comment comment = findComment(commentId);

        commentRepository.delete(comment);
    }

    public Comment findVerifiedComment(long commentId) {
        Optional<Comment> optionalComment =
                commentRepository.findById(commentId);
        Comment findComment = optionalComment.orElseThrow(() -> new RuntimeException("댓글이 존재 하지 않음"));

        return findComment;
    }
}
