package com.quattro.javacrud.repository;

import com.quattro.javacrud.models.ItemInfo;
import com.quattro.javacrud.models.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class CustomUserRepoImpl implements CustomUserRepo{

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public UserInfo getUserInfoOnly(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        return mongoTemplate.findOne(query, UserInfo.class);
    }
}
