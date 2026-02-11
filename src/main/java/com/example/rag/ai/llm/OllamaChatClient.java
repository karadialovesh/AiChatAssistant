package com.example.rag.ai.llm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Component
public class OllamaChatClient {

        @Value("${rag.ollama.url}")
        private String ollamaUrl;

        @Value("${rag.ollama.chat-model}")
        private String model;

        private final RestTemplate restTemplate = new RestTemplate();


        public String ask(String prompt) {

                Map<String, Object> request = Map.of(
                                "model", model,
                                "prompt", prompt,
                                "stream", false);

                OllamaResponse response = restTemplate.postForObject(
                                ollamaUrl + "/api/generate",
                                request,
                                OllamaResponse.class);

                return response.getResponse();
        }

        private static class OllamaResponse {
                private String response;

                public String getResponse() {
                        return response;
                }

                public void setResponse(String response) {
                        this.response = response;
                }
        }
}
