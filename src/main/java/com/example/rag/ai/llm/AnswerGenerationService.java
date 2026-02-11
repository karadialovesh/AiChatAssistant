package com.example.rag.ai.llm;

import com.example.rag.ai.citation.CitedChunk;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerGenerationService {

    private final OllamaChatClient chatClient;

    public AnswerGenerationService(OllamaChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public String generate(
            String question,
            List<CitedChunk> citedChunks) {

        StringBuilder context = new StringBuilder();

        for (int i = 0; i < citedChunks.size(); i++) {
            CitedChunk c = citedChunks.get(i);
            context.append("[SOURCE ").append(i + 1).append("]\n");
            context.append(c.getContent()).append("\n\n");
        }


        String prompt = """
                You are an AI assistant.
                Answer ONLY using the provided sources.
                If the answer is not present, say:
                "I don't know based on the provided document."

                When answering, reference the sources like:
                [SOURCE 1], [SOURCE 2]

                Sources:
                %s

                Question:
                %s
                """.formatted(context, question);

        return chatClient.ask(prompt);
    }
}
