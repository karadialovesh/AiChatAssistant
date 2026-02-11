package com.example.rag.ai.util;

import java.util.ArrayList;
import java.util.List;

public class TextChunker {

    private static final int CHUNK_SIZE = 500; // characters

    public static List<String> chunk(String text) {
        List<String> chunks = new ArrayList<>();
        int length = text.length();
        int start = 0;

        while (start < length) {
            int end = Math.min(start + CHUNK_SIZE, length);

            // If we are not at the end of the text, try to find a sentence boundary
            if (end < length) {
                // Look for the last period, newline, or question mark within the chunk
                int lastPeriod = text.lastIndexOf('.', end);
                int lastNewline = text.lastIndexOf('\n', end);
                int lastQuestion = text.lastIndexOf('?', end);

                // Find the best cut point (closest to the end of the chunk)
                int cutPoint = Math.max(lastPeriod, Math.max(lastNewline, lastQuestion));

                // If we found a valid proper chunk ending within the last 20% of the chunk
                if (cutPoint > start + (CHUNK_SIZE * 0.8)) {
                    end = cutPoint + 1; // Include the punctuation
                }
            }

            String chunk = text.substring(start, end).trim();
            if (!chunk.isEmpty()) {
                chunks.add(chunk);
            }

            // Move start pointer for next chunk
            // If we cut at a delimiter, start after it. If forced cut, start at end.
            start = end;
        }

        return chunks;
    }
}
