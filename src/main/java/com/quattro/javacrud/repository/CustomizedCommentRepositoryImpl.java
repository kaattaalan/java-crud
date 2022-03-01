package com.quattro.javacrud.repository;

import com.quattro.javacrud.models.Comment;
import com.quattro.javacrud.payload.request.CommentRequest;
import com.quattro.javacrud.payload.response.CommentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomizedCommentRepositoryImpl implements CustomizedCommentRepository {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public Boolean isCommentCreatedByUser(String userId, String commentId) {
        Criteria criteria = Criteria.where("id").is(commentId).and("userInfo.id").is(userId);
        Query query = new Query(criteria);
        return mongoTemplate.exists(query, Comment.class);
    }

    @Override
    public List<CommentResponse> getCommentListforItem(String commentId) {
        Criteria isNotDeleted = new Criteria().orOperator(Criteria.where("deleted").exists(false),Criteria.where("deleted").is(false));
        Criteria isItemId = Criteria.where("itemId").is(commentId);
        Query query = new Query(new Criteria().andOperator(isItemId,isNotDeleted));
        query.fields().include("id", "title", "description", "createdDate","userInfo");
        return mongoTemplate.find(query, CommentResponse.class);
    }

    @Override
    public void softDeleteById(String commentId) {
        Query query = new Query(Criteria.where("id").is(commentId));
        Update update = new Update().set("deleted", true);
        mongoTemplate.upsert(query, update, Comment.class);
    }

    @Override
    public void updateComment(CommentRequest request) {
        Query query = new Query(Criteria.where("id").is(request.getId()));
        Update update = new Update();
        update.set("title", request.getTitle());
        update.set("description", request.getDescription());
        mongoTemplate.upsert(query, update, Comment.class);
    }
}
