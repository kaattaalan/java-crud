package com.quattro.javacrud.repository;

import com.quattro.javacrud.models.Item;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface Itemrepository extends MongoRepository<Item, String>, CustomizedItemRepo {

    @Query(value = "{ 'title' : { $regex: ?0 } }", fields = "{'id': 1,'title' : 1}")
    List<Item> findItemsByRegexpTitle(String regexp);

}
