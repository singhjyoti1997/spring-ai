package com.project.spring.ai.service;

import com.project.spring.ai.entity.ChatField;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SafeGuardAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ChatServiceImp implements ChatService {

    @Value("classpath:prompts/email-generator.st")
    private Resource emailPromptResource;

    private final ChatClient openAiChatClient;
    private final ChatClient ollamaChatClient;

    public ChatServiceImp(@Qualifier("openAiChatClient") ChatClient openAiChatClient, @Qualifier("ollamaChatClient") ChatClient ollamaChatClient) {
        this.openAiChatClient = openAiChatClient;
        this.ollamaChatClient = ollamaChatClient;
    }


    @Override
    public String chat(String query) {
        String prompt = query;
        Prompt prompt1 = new Prompt(prompt);
        String queryStr = "As a system expert :{prompt}";
        var content = ollamaChatClient
                .prompt()
//                .advisors(new SafeGuardAdvisor(List.of("abusewords")))
                .user(promptUserSpec -> promptUserSpec.text(queryStr).param("prompt", queryStr))
//                .system("As a System")
                .call()
                .chatResponse()
                .getResult()
                .getOutput()
                .getText();
//                .content();

        return content;
    }

    @Override
    public ChatField responseEntity(String query) {
        String prompt = query;
        Prompt prompt1 = new Prompt(prompt);

        ChatField chatField = ollamaChatClient
                .prompt(prompt1)
//                .user(prompt)
//                .system("As a System")
                .call()
                .entity(ChatField.class);
//                .content();

        return chatField;
    }

    @Override
    public String getTechDetails(String techName) {


        var systemTemplate = SystemPromptTemplate.builder()
                .template("You are a helpful coding assistant. You are an expert in coding.")
                .build();

        Message systemMessage = systemTemplate.createMessage();


        var userTemplate = PromptTemplate.builder()
                .template("What is {techName}? Tell me also about {techExample}")
                .build();

        Message userMessage = userTemplate.createMessage(Map.of(
                "techName", techName,
                "techExample", techName + " exception"
        ));

        Prompt prompt = new Prompt(List.of(systemMessage, userMessage));

        return ollamaChatClient.prompt(prompt).call().content();

    }


    // Concept of chat client Fluent Api
    @Override
    public String generateQuestions(String topic, String level) {

        return ollamaChatClient.prompt()
                .system(system -> system.text(
                        "You are an expert technical interviewer. Generate clear and structured questions."
                ))
                .user(user -> user.text(
                                        "Generate 5 interview questions on {topic} for {level} level candidate"
                                )
                                .param("topic", topic)
                                .param("level", level)
                )
                .call()
                .content();
    }

    @Override
    public String generateEmail(String purpose, String tone) {

        PromptTemplate template = new PromptTemplate(emailPromptResource);

        String prompt = template.render(Map.of(
                "purpose", purpose,
                "tone", tone
        ));

        return ollamaChatClient.prompt()
                .user(prompt)
                .advisors(new SimpleLoggerAdvisor())
                .call()
                .content();
    }

}
