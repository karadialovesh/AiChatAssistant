package com.example.rag.ai.repository;


import com.example.rag.ai.domain.DocumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DocumentRepository extends JpaRepository<DocumentEntity, Long> {
    @Query("""
    SELECT d.name
    FROM DocumentEntity d
    WHERE d.id = :docId
""")
    String findDocumentName(@Param("docId") Long docId);

}
