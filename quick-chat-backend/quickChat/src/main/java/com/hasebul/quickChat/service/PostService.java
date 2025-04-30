package com.hasebul.quickChat.service;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.json.JsonData;
import com.hasebul.quickChat.dto.PostDto;
import com.hasebul.quickChat.dto.PostFilterDTO;
import com.hasebul.quickChat.model.Post;
import com.hasebul.quickChat.model.User;
import com.hasebul.quickChat.repository.PostRepo;
import com.hasebul.quickChat.utils.Helper;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional
public class PostService {

    @Autowired private PostRepo postRepo;
    @Autowired private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired private UserService userService;
    private final ElasticsearchOperations elasticsearchOperations;

    public PostService(ElasticsearchOperations elasticsearchOperations) {
        this.elasticsearchOperations = elasticsearchOperations;
    }

    public Post createPost(PostDto postDto) throws IOException {
        Post post = Helper.PostEntityToDto(postDto);
        post.setCreatedDate(LocalDateTime.now().toString());
        post.setUpdatedDate(LocalDateTime.now().toString());

        Post savedPost = postRepo.save(post);
        findTrendingPost();

        User user = userService.findUserByEmail(savedPost.getCreatorEmail());
        user.setPublishedPostCount(Optional.ofNullable(user.getPublishedPostCount()).orElse(0L) + 1);
        userService.updateUserInfo(user.getId(), Helper.userEntityToDto(user));

        simpMessagingTemplate.convertAndSendToUser(
                user.getId().toString(),
                "/post-count/queue",
                user.getPublishedPostCount()
        );

        return savedPost;
    }

    public List<Post> getAllPosts() {
        return StreamSupport.stream(postRepo.findAll().spliterator(), false)
                .sorted((a, b) -> b.getCreatedDate().compareTo(a.getCreatedDate()))
                .collect(Collectors.toList());
    }

    public Post getPostById(String id) {
        return postRepo.findById(id).orElse(null);
    }

    public List<Post> getPostsByUserName(String userName) {
        return postRepo.findPostByCreatorName(userName).map(List::of).orElseGet(List::of);
    }

    public List<Post> getPostsByUserEmail(String userEmail) {
        return postRepo.findPostByCreatorEmail(userEmail).map(List::of).orElseGet(List::of);
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

    public Post updatePostLikeDislikeCount(String postId, int count, boolean isLike) throws Exception {
        Map<String, Object> updateFields = new HashMap<>();
        if (isLike) {
            updateFields.put("likeCount", count);
        } else {
            updateFields.put("dislikeCount", count);
        }

        // Prepare partial update
        UpdateQuery updateQuery = UpdateQuery.builder(postId)
                .withDocument(Document.from(updateFields))
                .build();

        try {
            elasticsearchOperations.update(updateQuery, IndexCoordinates.of("posts"));
            System.out.println("Successfully updated like/dislike count for postId: " + postId);

            //findTrendingPost(); // You can keep or remove this based on your design

            // Return the updated count only
            Post updated = new Post();
            updated.setPostId(postId);
            if (isLike) updated.setLikeCount(count);
            else updated.setDislikeCount(count);
            return updated;
        } catch (Exception e) {
            throw new RuntimeException("Failed to update post like/dislike count", e);
        }
    }

    public void deletePost(String id) {
        postRepo.deleteById(id);
        findTrendingPost();
    }

    public void findTrendingPost() {
        try {
            List<Post> posts = getMostLikedPosts();

            if (posts.isEmpty()) {
                System.out.println("No trending posts found.");
            } else {
                System.out.println("Found " + posts.size() + " trending posts.");
            }

            // Send the trending posts to the WebSocket channel
            simpMessagingTemplate.convertAndSend("/topic/public/treding-post", posts);
        } catch (Exception e) {
            System.out.println("Error in finding trending posts: " + e.getMessage());
            // Optionally log more details about the exception, including stack trace if needed
            e.printStackTrace();
        }
    }

    private List<Post> getMostLikedPosts() {
        // Constructing the match_all query
        MatchAllQueryBuilder matchQuery = QueryBuilders.matchAllQuery();
        System.out.println("Created match query: " + matchQuery);

        // Creating the search query with sorting and pagination
        NativeSearchQuery searchQuery = new NativeSearchQuery(matchQuery);
        searchQuery.setPageable(PageRequest.of(0, 8)); // Limit results to 8 posts
        searchQuery.addSort(Sort.by(Sort.Order.desc("likeCount"))); // Sort by like count
        System.out.println("Search query with pagination and sorting: " + searchQuery);

        // Perform the search operation
        SearchHits<Post> searchHits = elasticsearchOperations.search(searchQuery, Post.class, IndexCoordinates.of("posts"));
        System.out.println("Search hits obtained: " + searchHits.getTotalHits());

        // Collect the results
        List<Post> posts = new ArrayList<>();
        searchHits.forEach(hit -> {
            System.out.println("Adding post: " + hit.getContent().getPostId() + " with like count: " + hit.getContent().getLikeCount());
            posts.add(hit.getContent());
        });

        System.out.println("Total posts fetched: " + posts.size());
        return posts;
    }

    public List<Post> filterResult(PostFilterDTO filter) {
        Criteria criteria = new Criteria();

        // Text filters
        if (filter.getCreatorName() != null && !filter.getCreatorName().isEmpty()) {
            criteria = criteria.and("creatorName").is(filter.getCreatorName());
        }

        if (filter.getContent() != null && !filter.getContent().isEmpty()) {
            criteria = criteria.and("content").matches(filter.getContent());
        }

        // Numeric filters
        if (filter.getLikeCount() != null) {
            Criteria likeCriteria = new Criteria("likeCount");
            if (filter.getLikeCount().getGte() != null) {
                likeCriteria = likeCriteria.greaterThanEqual(filter.getLikeCount().getGte());
            }
            if (filter.getLikeCount().getLte() != null) {
                likeCriteria = likeCriteria.lessThanEqual(filter.getLikeCount().getLte());
            }
            criteria = criteria.and(likeCriteria);
        }

        if (filter.getDislikeCount() != null) {
            Criteria dislikeCriteria = new Criteria("dislikeCount");
            if (filter.getDislikeCount().getGte() != null) {
                dislikeCriteria = dislikeCriteria.greaterThanEqual(filter.getDislikeCount().getGte());
            }
            if (filter.getDislikeCount().getLte() != null) {
                dislikeCriteria = dislikeCriteria.lessThanEqual(filter.getDislikeCount().getLte());
            }
            criteria = criteria.and(dislikeCriteria);
        }

        // Date filters
        if (filter.getCreatedDate() != null) {
            Criteria createdDate = new Criteria("createdDate");
            if (filter.getCreatedDate().getGte() != null) {
                createdDate = createdDate.greaterThanEqual(filter.getCreatedDate().getGte());
            }
            if (filter.getCreatedDate().getLte() != null) {
                createdDate = createdDate.lessThanEqual(filter.getCreatedDate().getLte());
            }
            criteria = criteria.and(createdDate);
        }

        if (filter.getUpdatedDate() != null) {
            Criteria updatedDate = new Criteria("updatedDate");
            if (filter.getUpdatedDate().getGte() != null) {
                updatedDate = updatedDate.greaterThanEqual(filter.getUpdatedDate().getGte());
            }
            if (filter.getUpdatedDate().getLte() != null) {
                updatedDate = updatedDate.lessThanEqual(filter.getUpdatedDate().getLte());
            }
            criteria = criteria.and(updatedDate);
        }

        CriteriaQuery query = new CriteriaQuery(criteria);
        SearchHits<Post> hits = elasticsearchOperations.search(query, Post.class, IndexCoordinates.of("posts")); // ✅ Correct usage
        return hits.stream().map(SearchHit::getContent).collect(Collectors.toList());
    }

}
