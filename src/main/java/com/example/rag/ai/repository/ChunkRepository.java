package com.example.rag.ai.repository;

import com.example.rag.ai.domain.ChunkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ChunkRepository extends JpaRepository<ChunkEntity, Long> {


        @Query(value = """
                        SELECT id, content
                        FROM chunks
                        WHERE embedding IS NULL
                        """, nativeQuery = true)
        List<Object[]> findChunksWithoutEmbedding();

        @Modifying
        @Transactional
        @Query(value = """
                        UPDATE chunks
                        SET embedding = CAST(:embedding AS vector)
                        WHERE id = :chunkId
                        """, nativeQuery = true)
        void updateEmbedding(
                        @Param("chunkId") Long chunkId,
                        @Param("embedding") String embedding);

        @Query(value = """
                        SELECT *
                        FROM chunks
                        WHERE document_id IN (
                            SELECT id FROM documents WHERE user_id = :userId
                        )
                        ORDER BY embedding <-> CAST(:embedding AS vector)
                        LIMIT :limit
                        """, nativeQuery = true)
        List<ChunkEntity> findSimilarChunks(
                        @Param("embedding") String embedding,
                        @Param("userId") String userId,
                        @Param("limit") int limit);
}
