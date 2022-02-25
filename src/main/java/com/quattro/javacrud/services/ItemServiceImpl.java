package com.quattro.javacrud.services;

import com.quattro.javacrud.models.*;
import com.quattro.javacrud.payload.request.ItemRequest;
import com.quattro.javacrud.repository.Itemrepository;
import com.quattro.javacrud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("itemServiceImpl")
public class ItemServiceImpl implements ItemService{

    @Autowired
    Itemrepository itemrepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public List<Item> searchByTitle(String title) {
        return itemrepository.findItemsByRegexpTitle(title);
    }

    @Override
    public void insertItem(ItemRequest itemRequest) {
        UserInfo userInfo = userRepository.getUserInfoOnly(itemRequest.getUserId());
        itemrepository.save(new Item(itemRequest,userInfo));
    }

    @Override
    public void updateItem(ItemRequest request) {
        itemrepository.addItemUpdate(request);
    }

    @Override
    public List<ItemInfo> getItemInfoList() {
       return itemrepository.getItemInfoList();
    }

    @Override
    public Boolean isUserPermitted(String userId, String itemId) {
        return itemrepository.isItemCreatedByUser(userId,itemId);
    }

    @Override
    public Item findById(String id) {
        Optional<Item> item = itemrepository.findById(id);
        if(item.isPresent()){
            return item.get();
        }
        return null;
    }

    @Override
    public void deleteById(String id) {
        itemrepository.softDeleteById(id);
    }

    @Override
    public void deleteAllItems() {
        itemrepository.softDeleteAll();
    }

    @Override
    public Item updateViewAndFind(String id) {
        try {
            return itemrepository.incrementViewAndReturn(id);
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public void registerVote(String itemId, String userId, EVote voteType) {
        switch (voteType){
            case UP:
                itemrepository.registerUpVote(itemId,userId);
                break;
            case DOWN:
                itemrepository.registerDownVote(itemId,userId);
                break;
        }
    }

    @Override
    public Integer getVoteCount(String itemId) {
        return itemrepository.getVoteCount(itemId);
    }


}
