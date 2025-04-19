package com.hasebul.quickChat.service;

import com.hasebul.quickChat.dto.PostDto;
import com.hasebul.quickChat.model.Post;
import com.hasebul.quickChat.repository.PostRepo;
import com.hasebul.quickChat.utils.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PostService {

    @Autowired
    private PostRepo postRepo;

    public Post createPost(PostDto postDto) {
        Post post = Helper.PostEntityToDto(postDto);
        post.setCreatedDate(LocalDateTime.now().toString());
        post.setUpdatedDate(LocalDateTime.now().toString());
        return postRepo.save(post);
    }

    public List<Post> getAllPosts() {
        Iterable<Post> iterable = postRepo.findAll();
        return StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }

    public Post getPostById(String id) {
        return postRepo.findById(id).orElse(null);
    }

    public List<Post> getPostsByUserName(String userName) {
        return postRepo.findPostByCreatorName(userName)
                .map(List::of)
                .orElseGet(List::of);
    }

    public List<Post> getPostsByUserEmail(String userEmail) {
        return postRepo.findPostByCreatorEmail(userEmail)
                .map(List::of)
                .orElseGet(List::of);
    }

    public Post updatePost(String id, PostDto postDto) {
        Post post = postRepo.findById(id).orElse(null);
        if (post != null) {
            post.setContent(postDto.getContent());
            post.setCreatorName(postDto.getCreatorName());
            post.setCreatorEmail(postDto.getCreatorEmail());
            post.setLikeCount(postDto.getLikeCount());
            post.setDislikeCount(postDto.getDislikeCount());
            return postRepo.save(post);
        }
        return null;
    }

    public Post updatePostLikeCount(String id, int likeCount) {
        Post post = postRepo.findById(id).orElse(null);
        if (post != null) {
            post.setLikeCount(likeCount);
            postRepo.save(post);
        }
        return post;
    }

    public Post updatePostDislikeCount(String id, int dislikeCount) {
        Post post = postRepo.findById(id).orElse(null);
        if (post != null) {
            post.setDislikeCount(dislikeCount);
            postRepo.save(post);
        }
        return post;
    }

    public void deletePost(String id) {
        postRepo.deleteById(id);
    }
}
