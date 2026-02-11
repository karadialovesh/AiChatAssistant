package com.example.rag.ai.service;

import com.example.rag.ai.llm.OllamaEmbeddingClient;
import com.example.rag.ai.repository.ChunkRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmbeddingService {

    private final ChunkRepository chunkRepository;
    private final OllamaEmbeddingClient embeddingClient;

    public EmbeddingService(ChunkRepository chunkRepository, OllamaEmbeddingClient embeddingClient) {
        this.chunkRepository = chunkRepository;
        this.embeddingClient = embeddingClient;
    }


    public void embedAllMissingChunks() {

        List<Object[]> rows = chunkRepository.findChunksWithoutEmbedding();

        for (Object[] row : rows) {
            Long chunkId = ((Number) row[0]).longValue();
            String content = (String) row[1];

            List<Double> vector = embeddingClient.embed(content);

            String vectorLiteral = embeddingClient.toPgVector(vector);

            chunkRepository.updateEmbedding(chunkId, vectorLiteral);
        }
    }
}
