package com.quattro.javacrud.services;

import com.quattro.javacrud.models.EVote;
import com.quattro.javacrud.models.Item;
import com.quattro.javacrud.models.ItemInfo;
import com.quattro.javacrud.payload.request.ItemRequest;

import java.util.List;

public interface ItemService {

    List<Item> searchByTitle(String title);

    void insertItem(ItemRequest itemRequest);

    void updateItem(ItemRequest request);

    List<ItemInfo> getItemInfoList();

    Boolean isUserPermitted(String userId, String itemId);

    Item findById(String id);

    void deleteById(String id);

    void deleteAllItems();

    Item updateViewAndFind(String id);

    void registerVote(String itemId, String userId, EVote vote);

    Integer getVoteCount(String itemId);

}
