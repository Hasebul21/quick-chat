package com.hasebul.quickChat.controller;

import com.hasebul.quickChat.dto.LikeDislikeRequest;
import com.hasebul.quickChat.dto.PostDto;
import com.hasebul.quickChat.dto.PostFilterDTO;
import com.hasebul.quickChat.model.Post;
import com.hasebul.quickChat.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/post")
    public ResponseEntity<?> createPost(@RequestBody PostDto postDto) {
       Post savedPost = postService.createPost(postDto);
       return ResponseEntity.ok(savedPost);
    }

    @GetMapping("/post")
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/post/{id}")
    public Post getPostById(@PathVariable("id") String id) {
        return postService.getPostById(id);
    }

    @GetMapping("/post/user/{userName}")
    public List<Post> getPostsByUserName(@PathVariable("userName") String userName) {
        return postService.getPostsByUserName(userName);
    }

    @GetMapping("/post/email/{userEmail}")
    public List<Post> getPostsByUserEmail(@PathVariable("userEmail") String userEmail) {
        return postService.getPostsByUserEmail(userEmail);
    }

    @PutMapping("/post/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable("id") String id, @RequestBody PostDto postDto) {
        Post updatedPost = postService.updatePost(id, postDto);
        if (updatedPost != null) {
            return ResponseEntity.ok(updatedPost);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/posts/{id}/likes")
    public ResponseEntity<Post> updatePostLikeDislikeCount(@PathVariable("id") String postId,
                                                           @RequestBody LikeDislikeRequest likeDislikeRequest) {
        Post updatedPost = postService.updatePostLikeDislikeCount(postId, likeDislikeRequest.getCount(), likeDislikeRequest.isLike());

        if (updatedPost != null) {
            return ResponseEntity.ok(updatedPost);
        }
        return ResponseEntity.notFound().build();
    }
    @DeleteMapping("/post/{id}")
    public ResponseEntity<?> deletePost(@PathVariable("id") String id) {
        postService.deletePost(id);
        return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.OK);
    }

    @PostMapping("/post/filter")
    public ResponseEntity<?> postFilter(@RequestBody PostFilterDTO postFilterDTO){
        List<Post> list = postService.filterResult(postFilterDTO);
        return ResponseEntity.ok(list);
    }

}
