package com.project.spring.ai.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AISupportControllerService {

    @Qualifier("ollamaChatClient")
    private final ChatClient ollamaChatClient;
    private final ChatMemory chatMemory;

    public String chat(String companyId,
                       String userId,
                       String conversationId,
                       String message,
                       String type) {

        if (conversationId == null || conversationId.isBlank()) {
            conversationId = UUID.randomUUID().toString();
        }

        String finalConversationId =
                companyId + "_" + userId + "_" + conversationId;

        String systemPrompt = switch (type.toLowerCase()) {

            case "billing" ->
                    "You are a billing support expert. Help with invoices and payments.";

            case "technical" ->
                    "You are a technical support engineer. Solve system issues.";

            default ->
                    "You are a helpful customer support assistant.";
        };

        return ollamaChatClient
                .prompt()
                .advisors(a ->
                        a.param(ChatMemory.CONVERSATION_ID, finalConversationId)
                )
                .system(systemPrompt)
                .user(message)
                .call()
                .content();
    }


    public Object getHistory(String companyId,
                             String userId,
                             String conversationId) {

        String finalConversationId =
                companyId + "_" + userId + "_" + conversationId;

        return chatMemory.get(finalConversationId);
    }


    public void clearChat(String companyId,
                          String userId,
                          String conversationId) {

        String finalConversationId =
                companyId + "_" + userId + "_" + conversationId;

        chatMemory.clear(finalConversationId);
    }
}