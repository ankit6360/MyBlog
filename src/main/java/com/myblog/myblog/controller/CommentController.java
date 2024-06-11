package com.myblog.myblog.controller;

import com.myblog.myblog.payload.CommentDto;
import com.myblog.myblog.servicelayer.impl.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // http://localhost:8080/api/posts/1/comments
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable(value = "postId") long postId,
                                                    @RequestBody CommentDto commentDto) {
        CommentDto comment = commentService.createComment(postId, commentDto);
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }
    //http://localhost:8080/api/posts/{postId}/comments/{id}
    @PutMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> updateComment(
            @PathVariable("postId") long postId,
            @PathVariable("id") long id,
            @RequestBody CommentDto commentdto
    ) {
        CommentDto dto = commentService.updateComment(postId,id, commentdto);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @DeleteMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<String> deleteComment(
            @PathVariable("postId") long postId,
            @PathVariable("id") long id
    ) {
        commentService.deleteComment(postId, id);
        return new ResponseEntity<>("Comment deleted successfully", HttpStatus.OK);
    }

    // http://localhost:8080/api/posts/1/comments/1
    @GetMapping("/post/{postId}/comments")
    public List<CommentDto> getCommentsByPostId(@PathVariable(value = "postId") Long postId) {
        return commentService.getCommentsByPostId(postId);
    }
}