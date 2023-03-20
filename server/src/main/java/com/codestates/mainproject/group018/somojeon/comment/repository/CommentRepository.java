package com.codestates.mainproject.group018.somojeon.comment.repository;

import com.codestates.mainproject.group018.somojeon.comment.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findAllByCommentId(Pageable pageable, Long recordId);
}
