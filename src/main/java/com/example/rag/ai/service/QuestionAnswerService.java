package com.example.rag.ai.service;

import com.example.rag.ai.citation.CitedChunk;
import com.example.rag.ai.citation.CitationResolver;
import com.example.rag.ai.domain.ChunkEntity;
import com.example.rag.ai.llm.AnswerGenerationService;
import com.example.rag.ai.llm.ConfidenceGate;
import com.example.rag.ai.llm.OllamaEmbeddingClient;
import com.example.rag.ai.repository.ChunkRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionAnswerService {

    private final ChunkRepository chunkRepository;
    private final OllamaEmbeddingClient embeddingClient;
    private final RerankingService rerankingService;
    private final ConfidenceGate confidenceGate;
    private final AnswerGenerationService answerGenerationService;
    private final CitationResolver citationResolver;

    public QuestionAnswerService(ChunkRepository chunkRepository, OllamaEmbeddingClient embeddingClient,
            RerankingService rerankingService, ConfidenceGate confidenceGate,
            AnswerGenerationService answerGenerationService, CitationResolver citationResolver) {
        this.chunkRepository = chunkRepository;
        this.embeddingClient = embeddingClient;
        this.rerankingService = rerankingService;
        this.confidenceGate = confidenceGate;
        this.answerGenerationService = answerGenerationService;
        this.citationResolver = citationResolver;
    }

    public String answer(String question, String userId) {


        List<Double> questionVector = embeddingClient.embed(question);
        String vectorLiteral = embeddingClient.toPgVector(questionVector);


        List<ChunkEntity> chunks = chunkRepository.findSimilarChunks(vectorLiteral, userId, 5);

        double bestScore = 0.0;
        ChunkEntity bestChunk = null;


        for (ChunkEntity chunk : chunks) {
            double score = rerankingService.score(question, chunk.getContent());

            if (score > bestScore) {
                bestScore = score;
                bestChunk = chunk;
            }
        }

        if (bestChunk == null || !confidenceGate.allow(bestScore)) {
            return "I don't know based on the provided document.";
        }


        CitedChunk citedChunk = citationResolver.resolve(bestChunk);

        String rawAnswer = answerGenerationService.generate(
                question,
                List.of(citedChunk));


        return appendHumanReadableCitations(
                rawAnswer,
                List.of(citedChunk));
    }

    private String appendHumanReadableCitations(
            String answer,
            List<CitedChunk> citedChunks) {
        StringBuilder sb = new StringBuilder(answer);
        sb.append("\n\nSources:\n");

        for (int i = 0; i < citedChunks.size(); i++) {
            CitedChunk c = citedChunks.get(i);
            sb.append("[SOURCE ").append(i + 1).append("] ")
                    .append(c.getDocumentName())
                    .append(", page ")
                    .append(c.getPageNumber())
                    .append("\n");
        }
        return sb.toString();
    }
}
