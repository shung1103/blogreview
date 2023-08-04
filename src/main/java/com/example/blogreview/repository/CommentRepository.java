package com.example.blogreview.repository;

import com.example.blogreview.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Comment findByIdAndPostId(Long commentId, Long blogId);
}
