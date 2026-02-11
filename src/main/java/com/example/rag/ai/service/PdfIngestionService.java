package com.example.rag.ai.service;

import com.example.rag.ai.domain.DocumentEntity;
import com.example.rag.ai.domain.ChunkEntity;
import com.example.rag.ai.repository.DocumentRepository;
import com.example.rag.ai.repository.ChunkRepository;
import com.example.rag.ai.util.TextChunker;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.Instant;
import java.util.List;

@Service
public class PdfIngestionService {

    private final DocumentRepository documentRepository;
    private final ChunkRepository chunkRepository;

    public PdfIngestionService(DocumentRepository documentRepository, ChunkRepository chunkRepository) {
        this.documentRepository = documentRepository;
        this.chunkRepository = chunkRepository;
    }

    public void ingest(MultipartFile file, String userId) {

        // Save document metadata
        DocumentEntity document = DocumentEntity.builder()
                .name(file.getOriginalFilename())
                .userId(userId)
                .uploadedAt(Instant.now())
                .build();

        document = documentRepository.save(document);

        // Extract text page-by-page
        extractAndStoreChunks(file, document, userId);
    }

    private void extractAndStoreChunks(
            MultipartFile file,
            DocumentEntity document,
            String userId) {
        try (InputStream is = file.getInputStream();
                PDDocument pdf = PDDocument.load(is)) {

            PDFTextStripper stripper = new PDFTextStripper();
            int totalPages = pdf.getNumberOfPages();

            for (int page = 1; page <= totalPages; page++) {
                stripper.setStartPage(page);
                stripper.setEndPage(page);

                String text = stripper.getText(pdf).trim();

                if (text.isBlank()) {
                    continue;
                }

                // Chunk the page text
                List<String> chunks = TextChunker.chunk(text);

                for (String chunk : chunks) {
                    ChunkEntity chunkEntity = ChunkEntity.builder()
                            .documentId(document.getId())
                            .userId(userId)
                            .pageNumber(page)
                            .content(chunk)
                            .build();

                    chunkRepository.save(chunkEntity);
                }
            }

        } catch (java.io.IOException e) {
            // Specific handling for file reading errors (corrupt file, permissions)
            throw new RuntimeException("Failed to read the PDF file. Please verify the file is not corrupt.", e);
        } catch (Exception e) {
            // Catch-all for unexpected parsing errors
            throw new RuntimeException("An unexpected error occurred while processing the PDF.", e);
        }
    }
}
