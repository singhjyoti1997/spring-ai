package com.project.spring.ai.controller;

import com.project.spring.ai.entity.ChatField;
import com.project.spring.ai.service.ChatServiceImp;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping
public class ChatController {
   //todo: this is for when we use only one model than chatclient builder bean created automatically with configuration false in yml
//
//    private ChatClient chatClient;
//
//    public ChatController(ChatClient.Builder builder) {
//        this.chatClient = builder.build();
//    }

//    private final ChatClient openAiChatClient;
//    private final ChatClient ollamaChatClient;
//
//    public ChatController(@Qualifier("openAiChatClient") ChatClient openAiChatClient, @Qualifier("ollamaChatClient") ChatClient ollamaChatClient) {
//        this.openAiChatClient = openAiChatClient;
//        this.ollamaChatClient = ollamaChatClient;
//    }

//    public ChatController(OpenAiChatModel openAiChatModel, OllamaChatModel ollamaChatModel) {
//
//        this.openAiChatClient = ChatClient.builder(openAiChatModel).build();
//        this.ollamaChatClient = ChatClient.builder(ollamaChatModel).build();
//    }

    @Autowired
    private ChatServiceImp chatServiceImp;

    @GetMapping("chat")
    public ResponseEntity<String> chat(@RequestParam(value = "q") String q, @RequestHeader(value = "userId")String userId) {
//        var resultResponse = openAiChatClient.prompt(q).call().content();
        var resultResponse = chatServiceImp.chat(q,userId);
        return ResponseEntity.ok(resultResponse);
    }

    @GetMapping("entity-response")
    public ChatField responseEntity(@RequestParam(value = "q") String q) {
//        var resultResponse = openAiChatClient.prompt(q).call().content();
        return chatServiceImp.responseEntity(q);
    }


    @GetMapping("/tech")
    public String getTechInfo(@RequestParam String name) {
        return chatServiceImp.getTechDetails(name);
    }

    @GetMapping("/questions")
    public String getQuestions(@RequestParam String topic, @RequestParam String level) {
        return chatServiceImp.generateQuestions(topic, level);
    }

    @GetMapping("/generate")
    public String generateEmail(@RequestParam String purpose, @RequestParam String tone
    ) {
        return chatServiceImp.generateEmail(purpose, tone);
    }

    @GetMapping("steaming-response")
    public ResponseEntity<Flux<String>>  streamChat(@RequestParam(value = "q") String q) {
        var resultResponse = chatServiceImp.streamChat(q);
        return ResponseEntity.ok(resultResponse);
    }
}
