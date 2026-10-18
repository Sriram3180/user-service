package com.ai.knowledgeassistant.service;

import com.ai.knowledgeassistant.entity.Document;
import com.ai.knowledgeassistant.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AIService {

    private final ChatClient chatClient;
    private final DocumentRepository documentRepository;
    public String ask(String prompt) {

        return chatClient.prompt()
                .user(prompt)
                .call()
                .content();
    }

    public String summarize(long id) {

        Document document =
                documentRepository.findById(id)
                        .orElseThrow();

        return chatClient.prompt()
                .user("""
                    Summarize this document:

                    %s
                    """.formatted(document.getContent()))
                .call()
                .content();
    }

    public String askQuestion(Long id, String prompt) {

        Document document = documentRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Document not found"));

        return chatClient.prompt()
                .user("""
                Context:
                %s

                Question:
                %s

                Answer only from the provided context.
                If the answer is not available in the context,
                mention that clearly.
                """
                        .formatted(document.getContent(), prompt))
                .call()
                .content();
    }
}
