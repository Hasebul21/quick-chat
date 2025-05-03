package com.hasebul.quickChat.repository;

import com.hasebul.quickChat.model.Post;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepo extends ElasticsearchRepository<Post, String> {
    Optional<Post> findPostByCreatorEmail(String creatorEmail);
    Optional<Post> findPostByCreatorName(String creatorName);
}
