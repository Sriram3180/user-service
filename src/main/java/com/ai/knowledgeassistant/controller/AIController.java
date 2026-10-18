package com.ai.knowledgeassistant.controller;

import com.ai.knowledgeassistant.response.QuestionRequest;
import com.ai.knowledgeassistant.service.AIService;
import com.ai.knowledgeassistant.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AIController {

    private final DocumentService documentService;
    private final AIService aiService;

    @GetMapping("document/{id}/summary")
    public String summarizeDocument(
            @PathVariable Long id) {
        return aiService.summarize(
                id);
    }

    @GetMapping("/ask")
    public String test(@RequestBody QuestionRequest request) {
        return aiService.ask(request.getPrompt());
    }

    @PostMapping("document/{id}/ask")
    public String askQuestion(
            @PathVariable Long id,
            @RequestBody QuestionRequest request) {

        return aiService.askQuestion(
                id,
                request.getPrompt());
    }
}