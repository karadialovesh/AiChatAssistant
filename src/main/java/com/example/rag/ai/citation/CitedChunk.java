package com.example.rag.ai.citation;


public class CitedChunk {
    private Long chunkId;
    private Long documentId;
    private String documentName;
    private int pageNumber;
    private String content;

    public CitedChunk() {
    }

    public CitedChunk(Long chunkId, Long documentId, String documentName, int pageNumber, String content) {
        this.chunkId = chunkId;
        this.documentId = documentId;
        this.documentName = documentName;
        this.pageNumber = pageNumber;
        this.content = content;
    }

    public Long getChunkId() {
        return chunkId;
    }

    public void setChunkId(Long chunkId) {
        this.chunkId = chunkId;
    }

    public Long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
