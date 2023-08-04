package com.example.blogreview.service;

import com.example.blogreview.dto.PostRequestDto;
import com.example.blogreview.dto.PostResponseDto;
import com.example.blogreview.entity.Post;
import com.example.blogreview.repository.PostRepository;
import com.example.blogreview.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    // 게시글 목록 조회
    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAllByOrderByCreatedAtDesc();
    }

    // 게시글 생성하기
    @Override
    public PostResponseDto createPost(PostRequestDto requestDto, UserDetailsImpl userDetails) {
        // requestDto로부터 받은 게시글의 제목과 내용을 Post에 넣어줌
        Post post = new Post(requestDto);
        // 위에서 생성한 post에 user를 넣어줌
        post.setUser(userDetails.getUser());
        // Repository에 post 저장하기
        postRepository.save(post);
        // 위에서 생성한 post를 ResponseDto에 담아서 반환하기
        return new PostResponseDto(post);
    } // 게시글 생성

    // 게시글 수정
    @Override
    @Transactional
    public ResponseEntity<String> updatePost(Long id, PostRequestDto requestDto, UserDetailsImpl userDetails) {
        // postRepository에서 id로 해당 게시글 찾아오기
        Post post = findByPostId(id);

        // 해당 post의 작성자가 맞는지 확인
        if (userDetails.getUsername().equals(post.getUser().getUsername())) {
            // requestDto로부터 받은 게시글의 제목과 내용으로 해당 post 내용 수정하기
            post.update(requestDto);
            // responseDto에 post 내용을 담아서 반환하기
            return ResponseEntity.ok("Success"); // 상태 코드 200 반환
        } else {
            // 해당 post의 작성자가 아니라면 null 반환하기
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error"); // 상태 코드 400 반환
        }
    }

    // 게시글 삭제
    @Override
    public ResponseEntity<String> deletePost(Long id, UserDetailsImpl userDetails) {
        // postRepository에서 id로 해당 게시글 찾아오기
        Post post = findByPostId(id);

        // 해당 post의 작성자가 맞는지 확인
        if (userDetails.getUsername().equals(post.getUser().getUsername())) {
            // 맞으면 삭제하기
            postRepository.delete(post);
            return ResponseEntity.ok("Success"); // 상태 코드 200 반환
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error"); // 상태 코드 400 반환
        }
    }

    // post의 ID 값으로 postRepository 에서 post 찾아서 반환하기
    @Override
    public Post findByPostId(Long id) {
        return postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Not Found "+ id)
        );
    }
}
