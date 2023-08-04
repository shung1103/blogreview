package com.example.blogreview.service;

import com.example.blogreview.dto.PostRequestDto;
import com.example.blogreview.dto.PostResponseDto;
import com.example.blogreview.entity.Post;
import com.example.blogreview.entity.User;
import com.example.blogreview.repository.PostRepository;
import com.example.blogreview.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

public interface PostService {

    /**
     * 게시글 목록 조회 API
     * @return 목록 조회 결과
     */
    List<Post> getAllPosts();

    /**
     * 게시글 생성 API
     * @param requestDto 게시글 생성 요청 정보
     * @param userDetails 게시글 생성 요청자 정보
     * @return 게시글 생성 결과
     */
    PostResponseDto createPost(PostRequestDto requestDto, UserDetailsImpl userDetails);

    /**
     * 게시글 수정 API
     * @param id 선택 게시글 정보
     * @param userDetails 게시글 수정 요청자 정보
     * @param requestDto 게시글 수정 요청 정보
     */
    ResponseEntity<String> updatePost(Long id, PostRequestDto requestDto, UserDetailsImpl userDetails);

    /**
     * 게시글 삭제 API
     * @param id 선택 게시글 정보
     * @param userDetails 게시글 삭제 요청자 정보
     */
    ResponseEntity<String> deletePost(Long id, UserDetailsImpl userDetails);

    /**
     * 선택 게시글 조회 API
     * @param id 선택 게시글 정보
     * @return 게시글 선택 결과
     */
    Post findByPostId(Long id);
}
