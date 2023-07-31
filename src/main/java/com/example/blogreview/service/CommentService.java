package com.example.blogreview.service;

import com.example.blogreview.dto.CommentRequestDto;
import com.example.blogreview.dto.CommentResponseDto;
import com.example.blogreview.entity.Comment;
import com.example.blogreview.entity.Post;
import com.example.blogreview.entity.User;
import com.example.blogreview.repository.CommentRepository;
import com.example.blogreview.repository.PostRepository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final MessageSource messageSource;

    // 댓글 작성
    public CommentResponseDto createComment(Long postId, CommentRequestDto requestDto, User user) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new NullPointerException(messageSource.getMessage(
                        "not.exist.post",
                        null,
                        "해당 댓글이 존재하지 않습니다",
                        Locale.getDefault()
                ))
        );

        Comment comment = commentRepository.save(new Comment(requestDto, user, post));
        return new CommentResponseDto(comment);
    }

    @Transactional
    public ResponseEntity<String> updateCommnet(Long id, Long commentid, CommentRequestDto requestDto, User user, HttpServletResponse res) {
        // 해당 게시글이 있는지 확인
        findPost(id);
        // 해당 댓글이 있는지 확인
        Comment comment = findComment(commentid);
        // 해당 댓글을 작성한 작성자 이거나, 권한이 ADMIN인 경우 댓글 수정 가능
        if (comment.getUser().getUsername().equals(user.getUsername())
                || user.getRole().getAuthority().equals("ROLE_ADMIN")) {
            // 있으면 댓글 내용 업데이트
            comment.update(requestDto);
            // ResponseDto에 내용 담아서 반환
            CommentResponseDto commentResponseDto = new CommentResponseDto(comment);
            return ResponseEntity.ok().body("Success");
        } else {
            return ResponseEntity.badRequest().body("Error");
        }
    }

    public ResponseEntity<String> deleteComment(Long id, Long commentid, User user) {
        // 해당 게시글이 있는지 확인
        findPost(id);
        // 해당 댓글이 있는지 확인
        Comment comment = findComment(commentid);
        // 해당 댓글을 작성한 작성자 이거나, 권한이 ADMIN인 경우 댓글 삭제 가능
        if (comment.getUser().getUsername().equals(user.getUsername())
                || user.getRole().getAuthority().equals("ROLE_ADMIN")) {
            // 있으면 댓글 삭제
            commentRepository.delete(comment);
            return ResponseEntity.ok().body("Success");
        } else {
            return ResponseEntity.badRequest().body("Error");
        }
    }

    // 해당 포스트를 찾아서 반환
    private Post findPost(Long id) {
        return postRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 게시글은 존재하지 않습니다."));
    }
    // 해당 댓글을 찾아서 반환
    private Comment findComment(Long commentid) {
        return commentRepository.findById(commentid).orElseThrow(() ->
                new IllegalArgumentException("선택한 댓글은 존재하지 않습니다"));
    }
}
