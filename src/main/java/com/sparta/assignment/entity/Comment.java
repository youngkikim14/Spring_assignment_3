package com.sparta.assignment.entity;

import com.sparta.assignment.dto.CommentRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor

public class Comment extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = false)
    private String contents;

    @Column (nullable = false)
    private String username;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memo_id")
    private Memo memo;

    public Comment(CommentRequestDto commentRequestDto, User username) {
        this.contents = commentRequestDto.getContents();
        this.username = username.getUsername();
    }

    public void updateComment (CommentRequestDto commentRequestDto, User username){
        this.contents = commentRequestDto.getContents();
        this.username = username.getUsername();
    }
}
