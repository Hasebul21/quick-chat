package com.hasebul.quickChat.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.hasebul.quickChat.dto.ChatMessageDto;
import com.hasebul.quickChat.model.ChatMessage;
import com.hasebul.quickChat.repository.ChatMessageRepo;
import com.hasebul.quickChat.utils.ElasticSearchUtils;
import com.hasebul.quickChat.utils.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@Service
public class ChatMessageService {

    @Autowired
    private ChatMessageRepo chatMessageRepo;

    @Autowired
    private ElasticsearchClient elasticsearchClient;

    public void saveMessage(ChatMessageDto chatMessageDto){
        ChatMessage chatEntity = Helper.mapChatDtoToChatEntity(chatMessageDto);
        chatMessageRepo.save(chatEntity);
   }

   public List<ChatMessage> chatMessageList(String senderId, String receiverId) throws IOException {
       Supplier<Query> supplier = ElasticSearchUtils.chatMessageSupplier(senderId, receiverId);
       SearchResponse<ChatMessage> searchResponse = elasticsearchClient.search(s->s.index("chat_messages").query(supplier.get()),ChatMessage.class);
       List<Hit<ChatMessage>> hitList = searchResponse.hits().hits();
       List<ChatMessage> chatMessageList = new ArrayList<>();
       for(Hit<ChatMessage> chatMessageHit : hitList){
           chatMessageList.add(chatMessageHit.source());
       }
       return chatMessageList;

   }

}
