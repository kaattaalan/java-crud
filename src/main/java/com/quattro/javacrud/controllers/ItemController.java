package com.quattro.javacrud.controllers;

import com.quattro.javacrud.models.Item;
import com.quattro.javacrud.repository.Itemrepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/item")
public class ItemController {

    @Autowired
    Itemrepository itemrepository;

    @GetMapping("/")
    public ResponseEntity<?> getAllItems(){
        List<Item> itemList = new ArrayList<Item>();
        itemrepository.findAll().forEach(itemList::add);
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
        List<Item> itemList = itemrepository.findAllByTitle(title);
        if (!itemList.isEmpty()) {
            return new ResponseEntity<>(itemList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> insertItem(@Valid @RequestBody Item item){
        itemrepository.save(item);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    @PutMapping("/")
    public ResponseEntity<Item> updateItem(@RequestBody Item item) {
        if (item.getId() != null) {
            return new ResponseEntity<>(itemrepository.save(item), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
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
