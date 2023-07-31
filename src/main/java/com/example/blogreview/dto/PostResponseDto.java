package com.example.blogreview.dto;

import com.example.blogreview.entity.Comment;
import com.example.blogreview.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class PostResponseDto {
    private Long id;
    private String username;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private List<CommentResponseDto> commentList = new ArrayList<>();

    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.username = post.getUser().getUsername();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();

        // post에 저장된 commentList Comment들을 하나씩 저장해준다
        // 날짜 거꾸로를 출력하고 싶어서, 거꾸로 리스트에 담아준다
        if (post.getCommentList().size() > 0) {
            for (Comment comment : post.getCommentList()) {
                this.commentList.add(new CommentResponseDto(comment));
            }
        }
    }
}
