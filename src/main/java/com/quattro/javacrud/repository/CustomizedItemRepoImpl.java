package com.quattro.javacrud.repository;

import com.quattro.javacrud.models.Item;
import com.quattro.javacrud.models.ItemInfo;
import com.quattro.javacrud.models.UpdateDetails;
import com.quattro.javacrud.payload.request.ItemRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component("customizedItemRepoImpl")
public class CustomizedItemRepoImpl implements CustomizedItemRepo {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public void addItemUpdate(ItemRequest request) {
        Query query = new Query(Criteria.where("id").is(request.getId()));
        Update update = new Update();
        update.set("title", request.getTitle());
        update.set("description", request.getDescription());
        update.addToSet("updateDetails",new UpdateDetails(request.getUserId(), Instant.now()));
        mongoTemplate.upsert(query, update, Item.class);
    }

    @Override
    public List<ItemInfo> getItemInfoList() {
        return mongoTemplate.findAll(ItemInfo.class);
    }

    /**
     * Checks whether the Item belongs to the user
     * @param userId
     * @param itemId
     * @return
     */
    @Override
    public Boolean isItemCreatedByUser(String userId, String itemId) {
        Criteria criteria = Criteria.where("id").is(itemId).and("userInfo.id").is(userId);
        Query query = new Query(criteria);
        return mongoTemplate.exists(query,Item.class);
    }

}
