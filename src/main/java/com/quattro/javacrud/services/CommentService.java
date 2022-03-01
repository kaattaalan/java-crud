package com.quattro.javacrud.services;

import com.quattro.javacrud.models.Comment;
import com.quattro.javacrud.payload.request.CommentRequest;
import com.quattro.javacrud.payload.response.CommentResponse;

import java.util.List;

public interface CommentService {

    Comment insertComment(CommentRequest commentRequest);

    void updateComment(CommentRequest commentRequest);

    void deleteById(String id);

    Comment findById(String id);

    Boolean isUserPermitted(String userId, String commentId);

    List<CommentResponse> findAllCommentsforItem(String commentId);
}
