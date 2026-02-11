package com.example.rag.ai.citation;

import com.example.rag.ai.domain.ChunkEntity;
import com.example.rag.ai.repository.DocumentRepository;

import org.springframework.stereotype.Service;

@Service
public class CitationResolver {

    private final DocumentRepository documentRepository;

    public CitationResolver(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }


    public CitedChunk resolve(ChunkEntity chunk) {

        String docName = documentRepository.findDocumentName(chunk.getDocumentId());

        return new CitedChunk(
                chunk.getId(),
                chunk.getDocumentId(),
                docName,
                chunk.getPageNumber(),
                chunk.getContent());
    }
}
