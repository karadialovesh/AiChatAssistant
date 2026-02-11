package com.example.rag.ai.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "chunks")

public class ChunkEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "document_id", nullable = false)
    private Long documentId;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "page_number", nullable = false)
    private int pageNumber;

    @Column(columnDefinition = "TEXT", nullable = false)
    // Chunks can be up to 1000 characters long
    private String content;

    public ChunkEntity() {
    }

    public ChunkEntity(Long id, Long documentId, String userId, int pageNumber, String content) {
        this.id = id;
        this.documentId = documentId;
        this.userId = userId;
        this.pageNumber = pageNumber;
        this.content = content;
    }

    public static ChunkEntityBuilder builder() {
        return new ChunkEntityBuilder();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public static class ChunkEntityBuilder {
        private Long id;
        private Long documentId;
        private String userId;
        private int pageNumber;
        private String content;

        ChunkEntityBuilder() {
        }

        public ChunkEntityBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public ChunkEntityBuilder documentId(Long documentId) {
            this.documentId = documentId;
            return this;
        }

        public ChunkEntityBuilder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public ChunkEntityBuilder pageNumber(int pageNumber) {
            this.pageNumber = pageNumber;
            return this;
        }

        public ChunkEntityBuilder content(String content) {
            this.content = content;
            return this;
        }

        public ChunkEntity build() {
            return new ChunkEntity(id, documentId, userId, pageNumber, content);
        }
    }
}
