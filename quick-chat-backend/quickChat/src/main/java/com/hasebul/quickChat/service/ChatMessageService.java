package com.hasebul.quickChat.service;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hasebul.quickChat.dto.ChatMessageDto;
import com.hasebul.quickChat.model.ChatMessage;
import com.hasebul.quickChat.repository.ChatMessageRepo;
import com.hasebul.quickChat.utils.Helper;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.*;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ChatMessageService {

    @Autowired
    private ChatMessageRepo chatMessageRepo;

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    @Autowired
    private RestHighLevelClient client;

    public void saveMessage(ChatMessageDto chatMessageDto) throws IOException {
        try {

            ChatMessage chatEntity = Helper.mapChatDtoToChatEntity(chatMessageDto);
            chatEntity.setMessageId(UUID.randomUUID().toString());


            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = objectMapper.writeValueAsString(chatEntity);


            RestClient lowLevelClient = client.getLowLevelClient();
            Request lowLevelRequest = new Request("POST", "/chat_messages/_create/" + chatEntity.getMessageId());
            lowLevelRequest.setJsonEntity(jsonString);

            Response response = lowLevelClient.performRequest(lowLevelRequest);
            String rawBody = EntityUtils.toString(response.getEntity());
            System.out.println("Raw response for message creation: " + rawBody);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to save chat message", e);
        }
    }

    public List<ChatMessage> chatMessageList(String senderId, String receiverId) throws IOException {
        List<ChatMessage> chatMessageList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();


        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery()
                .must(QueryBuilders.termQuery("senderId", senderId))
                .must(QueryBuilders.termQuery("receiverId", receiverId));


        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder()
                .query(boolQuery)
                .sort("createdOn", SortOrder.ASC)
                .size(1000);


        SearchRequest searchRequest = new SearchRequest("chat_messages");
        searchRequest.source(sourceBuilder);


        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

        for (SearchHit hit : searchResponse.getHits().getHits()) {
            ChatMessage message = objectMapper.readValue(hit.getSourceAsString(), ChatMessage.class);
            chatMessageList.add(message);
        }

        return chatMessageList;
    }

}
