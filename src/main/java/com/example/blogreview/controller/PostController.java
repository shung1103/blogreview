package com.example.blogreview.controller;

import com.example.blogreview.dto.PostRequestDto;
import com.example.blogreview.dto.PostResponseDto;
import com.example.blogreview.entity.Post;
import com.example.blogreview.security.UserDetailsImpl;
import com.example.blogreview.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping("/")
    public List<Post> getPostList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.getAllPosts();
    }

    @GetMapping("/api/post/{id}")
    public Post getOnePost(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.findByPostId(id);
    }

    // 게시글 작성
    @PostMapping("/api/post")
    public PostResponseDto createPost(@RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        return postService.createPost(requestDto, userDetails.getUser());
    }

    // 게시글 수정
    @Transactional
    @PutMapping("/api/post/{id}")
    public ResponseEntity<String> updatePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.updatePost(id, requestDto, userDetails.getUser());
    }
    // 게시글 삭제
    @DeleteMapping("/api/post/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.deletePost(id, userDetails.getUser());
    }
}
