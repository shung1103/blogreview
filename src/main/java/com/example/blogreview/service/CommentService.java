package com.example.blogreview.service;

import com.example.blogreview.dto.CommentRequestDto;
import com.example.blogreview.dto.CommentResponseDto;
import com.example.blogreview.entity.Comment;
import com.example.blogreview.entity.Post;
import com.example.blogreview.entity.User;
import com.example.blogreview.repository.CommentRepository;
import com.example.blogreview.repository.PostRepository;
import com.example.blogreview.security.UserDetailsImpl;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

public interface CommentService {
    CommentResponseDto createComment(Long postId, CommentRequestDto requestDto, UserDetailsImpl userDetails);

    ResponseEntity<String> updateCommnet(Long id, Long commentid, CommentRequestDto requestDto, UserDetailsImpl userDetails, HttpServletResponse res);

    ResponseEntity<String> deleteComment(Long id, Long commentid, UserDetailsImpl userDetails);
}
