package com.quattro.javacrud.repository;

import com.quattro.javacrud.models.Item;
import com.quattro.javacrud.models.ItemInfo;
import com.quattro.javacrud.payload.request.ItemRequest;

import java.util.List;

public interface CustomizedItemRepo {

    public void addItemUpdate(ItemRequest request);

    public List<ItemInfo> getItemInfoList();

    public Boolean isItemCreatedByUser(String itemId, String userId);

    Item incrementViewAndReturn(String id);

    void updateVotes(String id, Integer val);

}
