package com.example.practicalwork.services;

import com.example.practicalwork.models.Comment;
import com.example.practicalwork.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comment findComment (Long id) {
        Optional<Comment> comOpt = commentRepository.findById(id);
        return comOpt.orElseThrow();
    }

    public void saveCreateComment (Comment createComment) {
            commentRepository.save(createComment);
    }

    @Transactional
    public void updateComment (Comment updateComment) {
        Comment comment = findComment(updateComment.getId());
        comment.setText(updateComment.getText());
        commentRepository.save(comment);
    }

    @Transactional
    public void deleteComment (Long id) {
        Comment comment = findComment(id);
        comment.setRemoved(true);
        commentRepository.save(comment);
    }
}
