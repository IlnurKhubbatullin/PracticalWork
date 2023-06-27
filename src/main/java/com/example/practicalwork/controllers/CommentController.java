package com.example.practicalwork.controllers;

import com.example.practicalwork.models.Comment;
import com.example.practicalwork.services.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/new")
    public ResponseEntity<HttpStatus> createComment (@RequestBody @Valid Comment comment) {
        commentService.saveCreateComment(comment);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<HttpStatus> updateComment (@RequestBody @Valid Comment updateComment) {
        commentService.updateComment(updateComment);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteComment (@PathVariable("id") Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
