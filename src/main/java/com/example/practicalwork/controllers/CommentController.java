package com.example.practicalwork.controllers;

import com.example.practicalwork.models.Comment;
import com.example.practicalwork.services.CommentService;
import io.swagger.models.Response;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<Comment> createComment (@RequestBody @Valid Comment comment) {
        Comment newComment = commentService.saveCreateComment(comment);
        return ResponseEntity.accepted().body(newComment);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Comment> updateComment (@RequestBody @Valid Comment updateComment, @PathVariable Long id) {
        Comment newComment = commentService.updateComment(id, updateComment);
        return ResponseEntity.accepted().body(newComment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteComment (@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.accepted().body(new Response());
    }

}
