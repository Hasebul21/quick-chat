package com.hasebul.quickChat.repository;

import com.hasebul.quickChat.model.ChatMessage;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ChatMessageRepo extends ElasticsearchRepository<ChatMessage, String> {
}
