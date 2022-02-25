package com.quattro.javacrud.repository;

import com.quattro.javacrud.models.Item;
import com.quattro.javacrud.models.ItemInfo;
import com.quattro.javacrud.models.UpdateDetails;
import com.quattro.javacrud.payload.request.ItemRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public class CustomizedItemRepoImpl implements CustomizedItemRepo {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public void addItemUpdate(ItemRequest request) {
        Query query = new Query(Criteria.where("id").is(request.getId()));
        Update update = new Update();
        update.set("title", request.getTitle());
        update.set("description", request.getDescription());
        update.addToSet("updateDetails", new UpdateDetails(request.getUserId(), Instant.now()));
        mongoTemplate.upsert(query, update, Item.class);
    }

    @Override
    public List<ItemInfo> getItemInfoList() {
        Query query = new Query(new Criteria().orOperator(Criteria.where("deleted").exists(false),Criteria.where("deleted").is(false)));
        query.fields().include("id", "title");
        return mongoTemplate.find(query, ItemInfo.class);
    }

    /**
     * Checks whether the Item belongs to the user
     *
     * @param userId
     * @param itemId
     * @return
     */
    @Override
    public Boolean isItemCreatedByUser(String userId, String itemId) {
        Criteria criteria = Criteria.where("id").is(itemId).and("userInfo.id").is(userId);
        Query query = new Query(criteria);
        return mongoTemplate.exists(query, Item.class);
    }

    @Override
    public Item incrementViewAndReturn(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        Update update = new Update().inc("metaData.viewCount", 1);
        return mongoTemplate.findAndModify(query, update, FindAndModifyOptions.options().returnNew(true), Item.class);
    }

    @Override
    public void registerUpVote(String itemId, String userId) {
        //TODO: Check if already downvoted, then neutralize
        Query query = new Query(Criteria.where("id").is(itemId));
        Update update = new Update().addToSet("metaData.upVotes", userId).pull("metaData.downVotes", userId);
        mongoTemplate.upsert(query, update, Item.class);
    }

    @Override
    public void registerDownVote(String itemId, String userId) {
        //TODO: Check if already upvoted, then neutralize
        Query query = new Query(Criteria.where("id").is(itemId));
        Update update = new Update().addToSet("metaData.downVotes", userId).pull("metaData.upVotes", userId);
        mongoTemplate.upsert(query, update, Item.class);
    }

    @Override
    public Integer getVoteCount(String itemId) {
        //TODO: implement aggregation pipeline
        Query query = new Query(Criteria.where("id").is(itemId));
        Item item = mongoTemplate.findOne(query,Item.class);
        return item.getMetaData().getViewCount();
    }

    @Override
    public void softDeleteById(String itemId) {
        Query query = new Query(Criteria.where("id").is(itemId));
        Update update = new Update().set("deleted", true);
        mongoTemplate.upsert(query, update, Item.class);
    }

    @Override
    public void softDeleteAll() {
        Query query = new Query(new Criteria().orOperator(Criteria.where("deleted").exists(false),Criteria.where("deleted").is(false)));
        Update update = new Update().set("deleted", true);
        mongoTemplate.updateMulti(query, update, Item.class);
    }


}
