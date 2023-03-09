package com.codestates.mainproject.group018.somojeon.comment.repository;

import com.codestates.mainproject.group018.somojeon.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRespository extends JpaRepository<Comment, Long> {
}
