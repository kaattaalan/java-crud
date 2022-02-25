package com.quattro.javacrud.repository;

import com.quattro.javacrud.payload.request.CommentRequest;
import com.quattro.javacrud.payload.response.CommentResponse;

import java.util.List;

public interface CustomizedCommentRepository {
    Boolean isCommentCreatedByUser(String userId, String commentId);

    List<CommentResponse> getCommentListforItem(String commentId);

    void softDeleteById(String itemId);

    void updateComment(CommentRequest request);
}
