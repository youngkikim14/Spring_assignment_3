package com.sparta.assignment.controllor;

import com.sparta.assignment.dto.CommentRequestDto;
import com.sparta.assignment.dto.CommentResponseDto;
import com.sparta.assignment.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comments/memoid/{memoid}")
    public CommentResponseDto createComment(@PathVariable Long memoid , @RequestBody CommentRequestDto commentRequestDto, HttpServletRequest request) {
        return commentService.createComment(memoid, commentRequestDto, request);
    }

    @PutMapping("/comments/memoid/{memoid}/commentid/{id}")
    public CommentResponseDto updateComment(@PathVariable Long memoid, @PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto, HttpServletRequest request){
        return commentService.updateComment(id, commentRequestDto, memoid, request);
    }

    @DeleteMapping("/comments/memoid/{memoid}/commentid/{id}")
    public String deleteComment(@PathVariable Long memoid, @PathVariable Long id, HttpServletRequest request){
        return commentService.deleteComment(id, memoid, request);
    }
}
