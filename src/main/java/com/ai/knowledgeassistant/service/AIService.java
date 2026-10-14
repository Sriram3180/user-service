package com.ai.knowledgeassistant.service;

import com.ai.knowledgeassistant.entity.Document;
import com.ai.knowledgeassistant.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
@Service
@RequiredArgsConstructor
public class AIService {

    private final ChatClient chatClient;
    private final DocumentRepository documentRepository;


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
}
