package com.project.spring.ai.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class AiConfig {

    @Bean(name = "openAiChatClient")
    public ChatClient openAiChatClient(OpenAiChatModel model) {
        return ChatClient.builder(model).build();
    }

    @Bean(name = "ollamaChatClient")
    public ChatClient ollamaChatClient(OllamaChatModel model, ChatMemory chatMemory) {

        log.info("ChatMemory Impl: {}", chatMemory.getClass().getName());

        MessageChatMemoryAdvisor advisor =
                MessageChatMemoryAdvisor.builder(chatMemory).build();

        ChatOptions options = ChatOptions.builder()
                .model("mistral")
                .temperature(0.1)
                .build();

        return ChatClient.builder(model)
                .defaultAdvisors(advisor)
//                .defaultAdvisors(new TokenPrintAdvisor(),new SimpleLoggerAdvisor(),new SafeGuardAdvisor(List.of("abuse")))
                .defaultOptions(options)
                .build();
    }
}