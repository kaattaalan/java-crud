package com.quattro.javacrud.controllers;

import com.quattro.javacrud.models.Comment;
import com.quattro.javacrud.payload.request.CommentRequest;
import com.quattro.javacrud.payload.response.CommentResponse;
import com.quattro.javacrud.services.CommentService;
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
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired
    CommentService commentService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getCommentById(@PathVariable("id") String id) {
        Comment comment = commentService.findById(id);
        if (null != comment) {
            return new ResponseEntity<>(comment, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/item/{id}")
    public ResponseEntity<?> getCommentsByItem(@PathVariable("id") String itemId) {
        List<CommentResponse> commentList = commentService.findAllCommentsforItem(itemId);
        if (commentList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(commentList, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<?> insertComment(@Valid @RequestBody CommentRequest commentRequest ,@CurrentSecurityContext(expression = "authentication?.principal?.id") String userId) {
        commentRequest.setUserId(userId);
        Comment comment = commentService.insertComment(commentRequest);
        return new ResponseEntity<>(new CommentResponse(comment), HttpStatus.CREATED);
    }

    @PutMapping("/")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or @commentServiceImpl.isUserPermitted(authentication?.principal.id,#commentRequest.id)")
    public ResponseEntity<?> updateComment(@RequestBody CommentRequest commentRequest) {
        if (commentRequest.getId() != null) {
            commentService.updateComment(commentRequest);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or @commentServiceImpl.isUserPermitted(authentication?.principal.id,#id)")
    public ResponseEntity<?> deleteCommentById(@PathVariable("id") String id) {
        try {
            commentService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
