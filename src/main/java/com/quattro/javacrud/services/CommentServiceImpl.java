package com.quattro.javacrud.services;

import com.quattro.javacrud.models.Comment;
import com.quattro.javacrud.payload.request.CommentRequest;
import com.quattro.javacrud.payload.response.CommentResponse;
import com.quattro.javacrud.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("commentServiceImpl")
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Override
    public Comment insertComment(CommentRequest commentRequest) {
        Comment comment = new Comment(commentRequest);
        return commentRepository.save(comment);
    }

    @Override
    public void updateComment(CommentRequest commentRequest) {
        commentRepository.updateComment(commentRequest);
    }

    @Override
    public void deleteById(String id) {
        commentRepository.softDeleteById(id);
    }

    @Override
    public Comment findById(String id) {
        Optional<Comment> comment = commentRepository.findById(id);
        return comment.isPresent() ? comment.get() : null;
    }

    @Override
    public Boolean isUserPermitted(String userId, String commentId) {
        return commentRepository.isCommentCreatedByUser(userId,commentId);
    }

    @Override
    public List<CommentResponse> findAllCommentsforItem(String commentId) {
        return commentRepository.getCommentListforItem(commentId);
    }
}
