package com.hasebul.quickChat.repository;

import com.hasebul.quickChat.model.Post;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.Optional;

public interface PostRepo extends ElasticsearchRepository<Post, String> {
    Optional<Post> findPostByCreatorEmail(String creatorEmail);
    Optional<Post> findPostByCreatorName(String creatorName);
}
