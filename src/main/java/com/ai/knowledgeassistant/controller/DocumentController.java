package com.ai.knowledgeassistant.controller;

import com.ai.knowledgeassistant.entity.Document;
import com.ai.knowledgeassistant.service.AIService;
import com.ai.knowledgeassistant.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;
    private final AIService aiService;


    @PostMapping("/upload")
    public ResponseEntity<String> upload(
            @RequestParam("file")
            MultipartFile file)
            throws IOException {

        return ResponseEntity.ok(
                documentService.uploadFile(file));
    }

    @GetMapping
    public List<Document> getAll() {
        return documentService.getAll();
    }

    @GetMapping("/{id}/content")
    public String getContent(@PathVariable Long id) {

        return documentService.getContent(id);
    }

    @GetMapping("/{id}/summary")
    public String summarizeDocument(
            @PathVariable Long id) {



        return aiService.summarize(
                id);
    }
}