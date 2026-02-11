package com.example.rag.ai.service;

import com.example.rag.ai.llm.OllamaChatClient;

import org.springframework.stereotype.Service;

@Service
public class RerankingService {

    private final OllamaChatClient chatClient;

    public RerankingService(OllamaChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public double score(String question, String chunkText) {


        String prompt = """
                Question:
                %s

                Text:
                %s

                Does the text directly answer the question?
                Give a relevance score between 0 and 1.
                Only return the number.
                """.formatted(question, chunkText);

        String response = chatClient.ask(prompt);

        try {
            return Double.parseDouble(response.trim());
        } catch (Exception e) {
            return 0.0;
        }
    }
}
