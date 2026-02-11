package com.example.rag.ai.llm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class OllamaEmbeddingClient {

        @Value("${rag.ollama.url}")
        private String ollamaUrl;

        @Value("${rag.ollama.embedding-model}")
        private String model;

        private final RestTemplate restTemplate = new RestTemplate();


        public List<Double> embed(String text) {

                Map<String, Object> request = Map.of(
                                "model", model,
                                "prompt", text);

                EmbeddingResponse response = restTemplate.postForObject(
                                ollamaUrl + "/api/embeddings",
                                request,
                                EmbeddingResponse.class);

                return response.getEmbedding();
        }

        public String toPgVector(List<Double> vector) {
                return vector.stream()
                                .map(String::valueOf)
                                .collect(Collectors.joining(",", "[", "]"));
        }

        private static class EmbeddingResponse {
                private List<Double> embedding;

                public List<Double> getEmbedding() {
                        return embedding;
                }

                public void setEmbedding(List<Double> embedding) {
                        this.embedding = embedding;
                }
        }
}
