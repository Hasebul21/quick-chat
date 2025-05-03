package com.hasebul.quickChat.service;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.json.JsonData;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hasebul.quickChat.dto.PostDto;
import com.hasebul.quickChat.dto.PostFilterDTO;
import com.hasebul.quickChat.model.Post;
import com.hasebul.quickChat.model.User;
import com.hasebul.quickChat.repository.PostRepo;
import com.hasebul.quickChat.utils.Helper;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.*;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.xcontent.XContentType;
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

    @Autowired
    private PostRepo postRepo;
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private UserService userService;

    @Autowired
    private RestHighLevelClient client;

    private final ElasticsearchOperations elasticsearchOperations;

    public PostService(ElasticsearchOperations elasticsearchOperations) {
        this.elasticsearchOperations = elasticsearchOperations;
    }

    public Post createPost(PostDto postDto) throws IOException {
        try {

            Post post = new Post();
            post.setPostId(UUID.randomUUID().toString());
            post.setContent(postDto.getContent());
            post.setCreatorName(postDto.getCreatorName());
            post.setCreatorEmail(postDto.getCreatorEmail());
            post.setCreatedDate(LocalDateTime.now().toString());
            post.setUpdatedDate(LocalDateTime.now().toString());


            post.setLikeCount(0);
            post.setDislikeCount(0);


            RestClient lowLevelClient = client.getLowLevelClient();
            String jsonString = "{\"postId\": \"" + post.getPostId() + "\", " +
                    "\"content\": \"" + post.getContent() + "\", " +
                    "\"creatorName\": \"" + post.getCreatorName() + "\", " +
                    "\"creatorEmail\": \"" + post.getCreatorEmail() + "\", " +
                    "\"createdDate\": \"" + post.getCreatedDate() + "\", " +
                    "\"updatedDate\": \"" + post.getUpdatedDate() + "\", " +
                    "\"likeCount\": " + post.getLikeCount() + ", " +
                    "\"dislikeCount\": " + post.getDislikeCount() + "}";


            Request lowLevelRequest = new Request("POST", "/posts/_create/" + post.getPostId());
            lowLevelRequest.setJsonEntity(jsonString);


            Response response = lowLevelClient.performRequest(lowLevelRequest);
            String rawBody = EntityUtils.toString(response.getEntity());


            User user = userService.findUserByEmail(post.getCreatorEmail());
            user.setPublishedPostCount(Optional.ofNullable(user.getPublishedPostCount()).orElse(0L) + 1);
            userService.updateUserInfo(user.getId(), Helper.userEntityToDto(user));

            simpMessagingTemplate.convertAndSendToUser(
                    user.getId().toString(),
                    "/post-count/queue",
                    user.getPublishedPostCount()
            );

            findTrendingPost();
            return post;

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to create post: " + postDto.getPostId(), e);
        }
    }


    public List<Post> getAllPosts() {
        List<Post> posts = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            SearchRequest searchRequest = new SearchRequest("posts");
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            sourceBuilder.sort("createdDate", SortOrder.DESC);
            sourceBuilder.query(QueryBuilders.matchAllQuery());
            searchRequest.source(sourceBuilder);

            SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);

            for (org.elasticsearch.search.SearchHit hit : response.getHits().getHits()) {
                Post post = objectMapper.readValue(hit.getSourceAsString(), Post.class);
                posts.add(post);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return posts;
    }

    public Post getPostById(String id) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            GetRequest getRequest = new GetRequest("posts", id);
            GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
            if (getResponse.isExists()) {
                String json = getResponse.getSourceAsString();
                return objectMapper.readValue(json, Post.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Post> getPostsByUserName(String userName) {
        List<Post> posts = new ArrayList<>();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            SearchRequest searchRequest = new SearchRequest("posts");
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            sourceBuilder.query(QueryBuilders.matchQuery("creatorName", userName));
            searchRequest.source(sourceBuilder);

            SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
            for (org.elasticsearch.search.SearchHit hit : response.getHits().getHits()) {
                posts.add(objectMapper.readValue(hit.getSourceAsString(), Post.class));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return posts;
    }

    public List<Post> getPostsByUserEmail(String userEmail) {
        return postRepo.findPostByCreatorEmail(userEmail).map(List::of).orElseGet(List::of);
    }

    public Post updatePost(String id, PostDto postDto) {
        try {

            Post post = postRepo.findById(id).orElse(null);
            if (post != null) {
                UpdateRequest request = new UpdateRequest("posts", id);
                String jsonString = "{"
                        + "\"content\": \"" + postDto.getContent() + "\","
                        + "\"creatorName\": \"" + postDto.getCreatorName() + "\","
                        + "\"creatorEmail\": \"" + postDto.getCreatorEmail() + "\","
                        + "\"likeCount\": " + postDto.getLikeCount() + ","
                        + "\"dislikeCount\": " + postDto.getDislikeCount()
                        + "}";
                request.doc(jsonString, XContentType.JSON);

                client.update(request, RequestOptions.DEFAULT);

                post.setContent(postDto.getContent());
                post.setCreatorName(postDto.getCreatorName());
                post.setCreatorEmail(postDto.getCreatorEmail());
                post.setLikeCount(postDto.getLikeCount());
                post.setDislikeCount(postDto.getDislikeCount());
                return postRepo.save(post);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Post updatePostLikeDislikeCount(String postId, int count, boolean isLike) {
        try {
            RestClient lowLevelClient = client.getLowLevelClient();
            String jsonString = "{\"doc\": {\"" + (isLike ? "likeCount" : "dislikeCount") + "\": " + count + "}}";
            Request lowLevelRequest = new Request("POST", "/posts/_update/" + postId);
            lowLevelRequest.setJsonEntity(jsonString);
            Response response = lowLevelClient.performRequest(lowLevelRequest);
            String rawBody = EntityUtils.toString(response.getEntity());
            Post updatedPost = elasticsearchOperations.get(postId, Post.class, IndexCoordinates.of("posts"));
            try {
                Thread.sleep(1000); // Wait for 1 second
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            findTrendingPost();
            if (updatedPost != null) {
                System.out.println("Updated Post - PostId: " + updatedPost.getPostId() +
                        " | LikeCount: " + updatedPost.getLikeCount() +
                        " | DislikeCount: " + updatedPost.getDislikeCount());
                return updatedPost;
            } else {
                Post fallbackPost = new Post();
                fallbackPost.setPostId(postId);
                if (isLike) fallbackPost.setLikeCount(count);
                else fallbackPost.setDislikeCount(count);
                return fallbackPost;
            }

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to update like/dislike count for post: " + postId, e);
        }
    }

    public void deletePost(String id) {
        try {
            DeleteRequest request = new DeleteRequest("posts")
                    .id(id);
            client.delete(request, RequestOptions.DEFAULT);

            findTrendingPost();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void findTrendingPost() {
        try {
            List<Post> posts = getMostLikedPosts();

            if (posts.isEmpty()) {
                System.out.println("No trending posts found.");
            } else {
                System.out.println("Found " + posts.size() + " trending posts.");
            }

            simpMessagingTemplate.convertAndSend("/topic/public/treding-post", posts);
        } catch (Exception e) {
            System.out.println("Error in finding trending posts: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private List<Post> getMostLikedPosts() {
        MatchAllQueryBuilder matchQuery = QueryBuilders.matchAllQuery();

        NativeSearchQuery searchQuery = new NativeSearchQuery(matchQuery);
        searchQuery.setPageable(PageRequest.of(0, 8));
        searchQuery.addSort(Sort.by(Sort.Order.desc("likeCount")));
        SearchHits<Post> searchHits = elasticsearchOperations.search(searchQuery, Post.class, IndexCoordinates.of("posts"));

        List<Post> posts = new ArrayList<>();
        for( SearchHit<Post> hit : searchHits) {
            Post post = hit.getContent();
            posts.add(post);
        }
        return posts;
    }

    public List<Post> filterResult(PostFilterDTO filter) {
        Criteria criteria = new Criteria();

        if (filter.getCreatorName() != null && !filter.getCreatorName().isEmpty()) {
            criteria = criteria.and("creatorName").is(filter.getCreatorName());
        }

        if (filter.getContent() != null && !filter.getContent().isEmpty()) {
            criteria = criteria.and("content").matches(filter.getContent());
        }

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
