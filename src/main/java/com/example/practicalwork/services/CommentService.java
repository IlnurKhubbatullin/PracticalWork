package com.example.practicalwork.services;

import com.example.practicalwork.models.Comment;
import com.example.practicalwork.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

//    public Comment findComment (Long id) {
//        return commentRepository.findContractorById(id);
//    }

    public Comment saveCreateComment (Comment createComment) {
        Comment comment = new Comment();
        if (createComment != null) {
            comment.setText(createComment.getText());
            commentRepository.save(comment);
            return comment;
        }
        return null;
    }

//    public Comment updateComment (Long id, Comment updateComment) {
//        Comment comment = findComment(id);
//        comment.setText(updateComment.getText());
//        commentRepository.save(comment);
//        return comment;
//    }
//
//    public Comment deleteComment (Long id) {
//        Comment comment = findComment(id);
//        comment.setRemoved(true);
//        commentRepository.save(comment);
//        return comment;
//    }
}
