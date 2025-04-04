package com.hasebul.quickChat.repository;

import com.hasebul.quickChat.model.ChatMessage;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ChatMessageRepo extends ElasticsearchRepository<ChatMessage, String> {
}
