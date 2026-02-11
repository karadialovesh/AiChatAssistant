package com.example.rag.ai.controller;

import com.example.rag.ai.service.EmbeddingService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmbeddingController {

    private final EmbeddingService embeddingService;

    public EmbeddingController(EmbeddingService embeddingService) {
        this.embeddingService = embeddingService;
    }


    @PostMapping("/api/admin/embed")
    public String embed() {
        embeddingService.embedAllMissingChunks();
        return "Embeddings generated";
    }
}
