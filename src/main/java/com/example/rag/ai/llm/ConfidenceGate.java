package com.example.rag.ai.llm;

import org.springframework.stereotype.Component;

@Component
public class ConfidenceGate {


    private static final double THRESHOLD = 0.35;

    public boolean allow(double score) {
        return score >= THRESHOLD;
    }
}
