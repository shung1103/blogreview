package com.example.blogreview.controller;

import com.example.blogreview.dto.CommentRequestDto;
import com.example.blogreview.dto.CommentResponseDto;
import com.example.blogreview.security.UserDetailsImpl;
import com.example.blogreview.service.CommentService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    // 댓글 작성
    @PostMapping("/{postid}/comment")
    public CommentResponseDto createComment(@PathVariable Long postid, @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.createComment(postid, requestDto, userDetails.getUser());
    }

    // 댓글 수정
    @PutMapping("/{postid}/comment/{commentid}")
    public ResponseEntity<String> updateComment(@PathVariable Long postid, @PathVariable Long commentid, @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails, HttpServletResponse res) {
        return commentService.updateCommnet(postid, commentid, requestDto, userDetails.getUser(), res);
    }

    @DeleteMapping("/{postid}/comment/{commentid}")
    public ResponseEntity<String> deleteComment(@PathVariable Long postid, @PathVariable Long commentid, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.deleteComment(postid, commentid, userDetails.getUser());
    }
}
