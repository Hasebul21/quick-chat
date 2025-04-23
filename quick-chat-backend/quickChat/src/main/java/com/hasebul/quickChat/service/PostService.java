package com.hasebul.quickChat.service;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import com.hasebul.quickChat.dto.PostDto;
import com.hasebul.quickChat.dto.PostFilterDTO;
import com.hasebul.quickChat.model.Post;
import com.hasebul.quickChat.repository.PostRepo;
import com.hasebul.quickChat.utils.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional
public class PostService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    private ElasticsearchOperations elasticsearchOperations;

    public PostService(ElasticsearchOperations elasticsearchOperations) {
        this.elasticsearchOperations = elasticsearchOperations;
    }

    public Post createPost(PostDto postDto) {
        Post post = Helper.PostEntityToDto(postDto);
        post.setCreatedDate(LocalDateTime.now().toString());
        post.setUpdatedDate(LocalDateTime.now().toString());
        Post savedPost = postRepo.save(post);
        findTrendingPost();
        return savedPost;
    }

    public List<Post> getAllPosts() {
        Iterable<Post> iterable = postRepo.findAll();
        return StreamSupport.stream(iterable.spliterator(), false)
                .sorted((a, b) -> b.getCreatedDate().compareTo(a.getCreatedDate()))  // sorted in descending order
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

    public Post updatePostLikeDislikeCount(String postId, int count, boolean isLike) {
        Post post = postRepo.findById(postId).orElse(null);
        if (post != null) {
            if (isLike) {
                post.setLikeCount(count);
            } else {
                post.setDislikeCount(count);
            }
            postRepo.save(post);
        }
        findTrendingPost();
        return post;
    }

    public void deletePost(String id) {
        postRepo.deleteById(id);
        findTrendingPost();
    }

    public void findTrendingPost() {
        List<Post> posts = getMostLikesPost();
        simpMessagingTemplate.convertAndSend("/topic/public/treding-post", posts);
    }

    private List<Post> getMostLikesPost(){
        Query matchQuery = Query.of(q-> q.matchAll(m-> m));
        NativeQuery nativeQuery = NativeQuery.builder()
                .withQuery(matchQuery)
                .withSort(Sort.by(Sort.Order.desc("likeCount")))
                .withMaxResults(6)
                .build();
        SearchHits<Post> searchHits = elasticsearchOperations
                              .search(
                                        nativeQuery,
                                        Post.class,
                                        IndexCoordinates.of("posts")
                              );
        List<Post> posts = new ArrayList<>();
        searchHits.forEach(hit-> posts.add(hit.getContent()));
        System.out.println("Most Liked Posts: " + posts);
        return posts;
    }

    public List<Post> filterResult(PostFilterDTO postFilterDTO) {
        // Initialize variables
        String creatorName = postFilterDTO.getCreatorName();
        String content = postFilterDTO.getContent();

        // Initialize likeCount variables (defaults to null if not present)
        Integer likeCountGte = postFilterDTO.getLikeCount() != null ? postFilterDTO.getLikeCount().getGte() : null;
        Integer likeCountLte = postFilterDTO.getLikeCount() != null ? postFilterDTO.getLikeCount().getLte() : null;

        // Initialize dislikeCount variables (defaults to null if not present)
        Integer dislikeCountGte = postFilterDTO.getDislikeCount() != null ? postFilterDTO.getDislikeCount().getGte() : null;
        Integer dislikeCountLte = postFilterDTO.getDislikeCount() != null ? postFilterDTO.getDislikeCount().getLte() : null;


        // Initialize createdDate variables (defaults to null if not present)
        String createdDateGte = postFilterDTO.getCreatedDate() != null ? postFilterDTO.getCreatedDate().getGte() : null;
        String createdDateLte = postFilterDTO.getCreatedDate() != null ? postFilterDTO.getCreatedDate().getLte() : null;


        // Initialize updatedDate variables (defaults to null if not present)
        String updatedDateGte = postFilterDTO.getUpdatedDate() != null ? postFilterDTO.getUpdatedDate().getGte() : null;
        String updatedDateLte = postFilterDTO.getUpdatedDate() != null ? postFilterDTO.getUpdatedDate().getLte() : null;


        // Print everything
        System.out.println("Creator Name: " + creatorName);
        System.out.println("Content: " + content);
        System.out.println("Like Count GTE: " + likeCountGte);
        System.out.println("Like Count LTE: " + likeCountLte);
        System.out.println("Dislike Count GTE: " + dislikeCountGte);
        System.out.println("Dislike Count LTE: " + dislikeCountLte);
        System.out.println("Created Date GTE: " + createdDateGte);
        System.out.println("Created Date LTE: " + createdDateLte);
        System.out.println("Updated Date GTE: " + updatedDateGte);
        System.out.println("Updated Date LTE: " + updatedDateLte);

        List<Post> posts = new ArrayList<>();

        // Perform filtering logic here
        List<Query> mustQueries = new ArrayList<>();

        if(creatorName != null && !creatorName.isEmpty()) {
            Query creatorNameQuery = Query.of(q -> q
                    .term(t -> t.field("creatorName").value(creatorName)
                    ));
            mustQueries.add(creatorNameQuery);
        }

        if(content != null && !content.isEmpty()) {
            Query contentQuery = Query.of(q -> q
                    .match(m -> m
                            .field("content")
                            .query(content)
                    )
            );
            mustQueries.add(contentQuery);
        }

        Query likeCountQuery = Query.of(q -> q
                .range(r -> r.number(
                                n -> n.field("likeCount")
                                        .gte(likeCountGte != null ? likeCountGte.doubleValue() : null)
                                        .lte(likeCountLte != null ? likeCountLte.doubleValue() : null)
                        )
                ));
        mustQueries.add(likeCountQuery);

        Query dislikeCountQuery = Query.of(q -> q
                .range(r -> r.number(
                                n -> n.field("dislikeCount")
                                        .gte(dislikeCountGte != null ? dislikeCountGte.doubleValue() : null)
                                        .lte(dislikeCountLte != null ? dislikeCountLte.doubleValue() : null)
                        )
                ));
        mustQueries.add(dislikeCountQuery);

        Query createdDateQuery = Query.of(q -> q
                .range(r -> r.date(
                                d -> d.field("createdDate")
                                        .gte(createdDateGte != null ? createdDateGte : null)
                                        .lte(createdDateLte != null ? createdDateLte : null)
                        )
                ));
        mustQueries.add(createdDateQuery);

        Query updatedDateQuery = Query.of(q -> q
                .range(r -> r.date(
                                d -> d.field("updatedDate")
                                        .gte(updatedDateGte != null ? updatedDateGte : null)
                                        .lte(updatedDateLte != null ? updatedDateLte : null)
                        )
                ));
        mustQueries.add(updatedDateQuery);

        Query mustQuery = Query.of(q -> q.bool(b -> b.must(mustQueries)));

        NativeQuery nativeQuery = NativeQuery.builder()
                .withQuery(mustQuery)
                .build();

        SearchHits<Post> productHits =
                elasticsearchOperations
                        .search(nativeQuery,
                                Post.class,
                                IndexCoordinates.of("posts"));

        productHits.forEach(hit -> {
            Post post = hit.getContent();
            System.out.println(post);
            posts.add(post);
        });

        return posts;

    }

}
