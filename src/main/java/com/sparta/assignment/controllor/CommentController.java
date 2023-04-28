package com.sparta.assignment.controllor;

import com.sparta.assignment.dto.CommentRequestDto;
import com.sparta.assignment.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comments")
    public String createComment(@RequestBody CommentRequestDto commentRequestDto, Long memoid, HttpServletRequest request) {
        return commentService.createComment(commentRequestDto, memoid, request);
    }

    @PutMapping("/comments/{id}")
    public String updateComment(@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto, Long memoid, HttpServletRequest request){
        return commentService.updateComment(id, commentRequestDto, memoid, request);
    }

    @DeleteMapping("/comments/{id}")
    public String deleteComment(@PathVariable Long id, Long memoid, HttpServletRequest request){
        return commentService.deleteComment(id, memoid, request);
    }
}
