package com.example.blogreview.entity;

import com.example.blogreview.dto.CommentRequestDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "comments")
@NoArgsConstructor
public class Comment extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 값 자동 증가
    private Long id;

    @Column(name = "commentcontents", nullable = false)
    private String commentcontents;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "username", nullable = false)
    private User user;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    public Comment(CommentRequestDto commentRequestDto, User user, Post post) {
        this.commentcontents = commentRequestDto.getCommentcontents();
        this.user = user;
        this.post = post;
    }

    public void update(CommentRequestDto commentRequestDto) {
        this.commentcontents = commentRequestDto.getCommentcontents();
    }
}
