package com.example.rag.ai.domain;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "documents")

public class DocumentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(nullable = false)
    private String name;

    @Column(name = "uploaded_at", nullable = false)
    private Instant uploadedAt;

    public DocumentEntity() {
    }

    public DocumentEntity(Long id, String userId, String name, Instant uploadedAt) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.uploadedAt = uploadedAt;
    }

    public static DocumentEntityBuilder builder() {
        return new DocumentEntityBuilder();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(Instant uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

    public static class DocumentEntityBuilder {
        private Long id;
        private String userId;
        private String name;
        private Instant uploadedAt;

        DocumentEntityBuilder() {
        }

        public DocumentEntityBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public DocumentEntityBuilder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public DocumentEntityBuilder name(String name) {
            this.name = name;
            return this;
        }

        public DocumentEntityBuilder uploadedAt(Instant uploadedAt) {
            this.uploadedAt = uploadedAt;
            return this;
        }

        public DocumentEntity build() {
            return new DocumentEntity(id, userId, name, uploadedAt);
        }
    }
}
