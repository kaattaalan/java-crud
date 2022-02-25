package com.quattro.javacrud.repository;

import com.quattro.javacrud.models.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommentRepository extends MongoRepository<Comment,String> , CustomizedCommentRepository {

}
