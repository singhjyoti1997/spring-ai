package com.project.spring.ai;

import com.project.spring.ai.controller.ChatController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class ApplicationTests {
    @Autowired
    private ChatController chatController;

    @Test
    void contextLoads() {
    }

    @Test
    void testGetTechInfo() {
        String response = chatController.getTechInfo("Spring");
        assertThat(response).isNotEmpty();
        System.out.println(response);
    }

}
