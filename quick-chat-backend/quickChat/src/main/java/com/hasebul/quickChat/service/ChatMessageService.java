package com.hasebul.quickChat.service;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import com.hasebul.quickChat.dto.ChatMessageDto;
import com.hasebul.quickChat.model.ChatMessage;
import com.hasebul.quickChat.repository.ChatMessageRepo;
import com.hasebul.quickChat.utils.Helper;
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

@Service
@Transactional
public class ChatMessageService {

    @Autowired
    private ChatMessageRepo chatMessageRepo;

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    public void saveMessage(ChatMessageDto chatMessageDto){
        ChatMessage chatEntity = Helper.mapChatDtoToChatEntity(chatMessageDto);
        chatMessageRepo.save(chatEntity);
   }

   public List<ChatMessage> chatMessageList(String senderId, String receiverId) throws IOException {
       Query query = Query.of(q -> q.bool(b -> b
               .must(m -> m.term(t -> t.field("senderId").value(senderId)))
               .must(m -> m.term(t -> t.field("receiverId").value(receiverId)))
       ));
       Sort sort = Sort.by(Sort.Order.asc("createdOn"));
       NativeQuery nativeQuery = NativeQuery.builder().withQuery(query).withSort(sort).build();
       SearchHits<ChatMessage> searchHits = elasticsearchOperations
               .search(
                       nativeQuery,
                       ChatMessage.class,
                       IndexCoordinates.of("chat_messages")
               );
       List<ChatMessage> chatMessageList = new ArrayList<>();
       searchHits.forEach(hit-> chatMessageList.add(hit.getContent()));
       return chatMessageList;

   }

}
