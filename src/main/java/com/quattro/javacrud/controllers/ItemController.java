package com.quattro.javacrud.controllers;

import com.quattro.javacrud.models.Item;
import com.quattro.javacrud.models.ItemInfo;
import com.quattro.javacrud.models.UserInfo;
import com.quattro.javacrud.payload.request.ItemRequest;
import com.quattro.javacrud.repository.Itemrepository;
import com.quattro.javacrud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/item")
public class ItemController {

    @Autowired
    Itemrepository itemrepository;
    @Autowired
    UserRepository userRepository;

    @GetMapping("/")
    public ResponseEntity<?> getAllItems(){
        List<ItemInfo> itemList = itemrepository.getItemInfoList();
        if(itemList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(itemList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getItemById(@PathVariable("id") String id) {
        Optional<Item> item = itemrepository.findById(id);
        if (item.isPresent()) {
            return new ResponseEntity<>(item.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> getItemsByField(@RequestParam("title") String title) {
        List<Item> itemList = itemrepository.findItemsByRegexpTitle(title);
        if (!itemList.isEmpty()) {
            return new ResponseEntity<>(itemList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> insertItem(@Valid @RequestBody ItemRequest itemRequest){
        UserInfo userInfo = userRepository.getUserInfoOnly(itemRequest.getUserId());
        itemrepository.save(new Item(itemRequest,userInfo));
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    /**
     * @param itemRequest
     * @return
     * Method previously used userId obtained by this : @CurrentSecurityContext(expression="authentication?.principal?.id") String userId
     * to determine whether user was able to edit the resource
     */
    @PutMapping("/")
    @PreAuthorize("@customizedItemRepoImpl.isItemCreatedByUser(authentication?.principal.id,#itemRequest.id) or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Item> updateItem(@RequestBody ItemRequest itemRequest) {
        if (itemRequest.getId() != null) {
            itemrepository.addItemUpdate(itemRequest);
            return new ResponseEntity<>( HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@customizedItemRepoImpl.isItemCreatedByUser(authentication?.principal.id,#id) or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteItemById(@PathVariable("id") String id) {
        try {
            itemrepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteAll() {
        try {
            itemrepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
