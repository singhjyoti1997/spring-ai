package com.project.spring.ai.service;

import com.project.spring.ai.entity.ChatField;
import reactor.core.publisher.Flux;

public interface ChatService {

    String chat(String query);

    ChatField responseEntity(String query);

    String getTechDetails(String query);

    String generateQuestions(String topic , String level);

    String generateEmail(String purpose, String tone );

    Flux<String> streamChat(String q);
}
