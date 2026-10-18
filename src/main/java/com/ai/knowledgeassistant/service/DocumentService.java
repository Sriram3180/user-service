package com.ai.knowledgeassistant.service;

import com.ai.knowledgeassistant.entity.Document;
import com.ai.knowledgeassistant.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
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
public class DocumentService {

    private final DocumentRepository documentRepository;

    public String uploadFile(MultipartFile file) throws IOException {
        Path uploadPath = Paths.get("uploads/");

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String fileName = file.getOriginalFilename();
        if (Objects.nonNull(fileName)) {
            Path filePath = uploadPath.resolve(fileName);

            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            String extractedText = extractText(filePath);

            Document document = Document.builder().fileName(fileName).filePath(filePath.toString()).content(extractedText).contentType(file.getContentType()).fileSize(file.getSize()).uploadedAt(LocalDateTime.now()).build();

            documentRepository.save(document);

            return "File uploaded successfully";
        }
        return "";
    }

    public List<Document> getAll() {
        return documentRepository.findAll();
    }

    public String getContent(Long id) {

        return documentRepository.findById(id).orElseThrow(() -> new RuntimeException("Document not found")).getContent();
    }


    private String extractText(Path filePath) throws IOException {

        try (PDDocument document = Loader.loadPDF(filePath.toFile())) {

            PDFTextStripper pdfTextStripper = new PDFTextStripper();

            return pdfTextStripper.getText(document);
        }
    }


}
