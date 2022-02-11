package com.quattro.javacrud.repository;

import com.quattro.javacrud.models.ItemInfo;
import com.quattro.javacrud.payload.request.ItemRequest;

import java.util.List;

public interface CustomizedItemRepo {

    public void addItemUpdate(ItemRequest request);

    public List<ItemInfo> getItemInfoList();

}
