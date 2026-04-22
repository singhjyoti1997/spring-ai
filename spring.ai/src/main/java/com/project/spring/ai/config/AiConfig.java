package com.project.spring.ai.config;

import com.project.spring.ai.advisor.TokenPrintAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SafeGuardAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class AiConfig {

    @Bean(name = "openAiChatClient")
    public ChatClient openAiChatModel(OpenAiChatModel openAiChatModel) {
        return ChatClient.builder(openAiChatModel)
                .build();
    }

//    @Bean(name = "ollamaChatClient")
//    public ChatClient ollamaChatClient(OllamaChatModel ollamaChatModel) {
//
////        ChatOptions options = ChatOptions.builder()
////                .temperature(0.1)
////                .model("llama3")
////                .maxTokens(5000)
////                .build();
//
//        return ChatClient.builder(ollamaChatModel)

    /// /                .defaultOptions(options)
//                .build();
//    }
    @Bean(name = "ollamaChatClient")
    public ChatClient ollamaChatClient(OllamaChatModel ollamaChatModel) {

        ChatOptions options = ChatOptions.builder()
                .model("mistral")     // ✅ default model set
                .temperature(0.1)     // ✅ JSON safe
                .build();

        return ChatClient.builder(ollamaChatModel)
//                .defaultAdvisors(new TokenPrintAdvisor(),new SimpleLoggerAdvisor(),new SafeGuardAdvisor(List.of("abuse")))
                .defaultOptions(options)   // ✅ apply defaults
                .build();
    }

}
