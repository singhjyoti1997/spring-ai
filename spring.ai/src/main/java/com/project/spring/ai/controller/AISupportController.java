package com.project.spring.ai.controller;

import com.project.spring.ai.service.AISupportControllerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/support")
@RequiredArgsConstructor
public class AISupportController {

    private final AISupportControllerService aiSupportControllerService;

    @PostMapping("/chat")
    public ResponseEntity<String> chat(
            @RequestHeader String companyId,
            @RequestHeader String userId,
            @RequestParam(required = false) String conversationId,
            @RequestParam String message,
            @RequestParam(defaultValue = "general") String type) {

        return ResponseEntity.ok(
                aiSupportControllerService.chat(companyId, userId, conversationId, message, type)
        );
    }

    @GetMapping("/history")
    public ResponseEntity<Object> history(
            @RequestHeader String companyId,
            @RequestHeader String userId,
            @RequestParam String conversationId) {

        return ResponseEntity.ok(
                aiSupportControllerService.getHistory(companyId, userId, conversationId)
        );
    }


    @DeleteMapping("/clear")
    public ResponseEntity<String> clear(
            @RequestHeader String companyId,
            @RequestHeader String userId,
            @RequestParam String conversationId) {

        aiSupportControllerService.clearChat(companyId, userId, conversationId);
        return ResponseEntity.ok("Chat cleared successfully");
    }
}