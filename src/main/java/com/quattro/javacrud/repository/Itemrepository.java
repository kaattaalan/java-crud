package com.quattro.javacrud.repository;

import com.quattro.javacrud.models.Item;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface Itemrepository extends MongoRepository<Item, String> {

    List<Item> findAllByTitle(String title);

}
