package com.quattro.javacrud.controllers;

import com.quattro.javacrud.models.EVote;
import com.quattro.javacrud.models.Item;
import com.quattro.javacrud.models.ItemInfo;
import com.quattro.javacrud.payload.request.ItemRequest;
import com.quattro.javacrud.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/item")
public class ItemController {

    @Autowired
    ItemService itemService;

    @GetMapping("/")
    public ResponseEntity<?> getAllItems() {
        List<ItemInfo> itemList = itemService.getItemInfoList();
        if (itemList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(itemList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getItemById(@PathVariable("id") String id) {
        Item item = itemService.updateViewAndFind(id);
        if (null != item) {
            return new ResponseEntity<>(item, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> getItemsByField(@RequestParam("title") String title) {
        List<Item> itemList = itemService.searchByTitle(title);
        if (!itemList.isEmpty()) {
            return new ResponseEntity<>(itemList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> insertItem(@Valid @RequestBody ItemRequest itemRequest,@CurrentSecurityContext(expression = "authentication?.principal?.id") String userId) {
        itemRequest.setUserId(userId);
        itemService.insertItem(itemRequest);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    /**
     * @param itemRequest
     * @return
     */
    @PutMapping("/")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or @itemServiceImpl.isUserPermitted(authentication?.principal.id,#itemRequest.id)")
    public ResponseEntity<Item> updateItem(@RequestBody ItemRequest itemRequest,@CurrentSecurityContext(expression = "authentication?.principal?.id") String userId) {
        itemRequest.setUserId(userId);
        if (itemRequest.getId() != null) {
            itemService.updateItem(itemRequest);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or @itemServiceImpl.isUserPermitted(authentication?.principal.id,#id)")
    public ResponseEntity<?> deleteItemById(@PathVariable("id") String id) {
        try {
            itemService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteAll() {
        try {
            itemService.deleteAllItems();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/vote/{item_id}/{vote}")
    public ResponseEntity<?> registerVote(@PathVariable("item_id") String itemId, @PathVariable("vote") EVote vote
            , @CurrentSecurityContext(expression = "authentication?.principal?.id") String userId) {
        itemService.registerVote(itemId,userId,vote);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/vote/{item_id}")
    public ResponseEntity<?> registerVote(@PathVariable("item_id") String itemId) {
        Integer voteCount = itemService.getVoteCount(itemId);
        return new ResponseEntity<>(voteCount, HttpStatus.OK);
    }

}
