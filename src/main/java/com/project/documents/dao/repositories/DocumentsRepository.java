package com.project.documents.dao.repositories;

import com.project.documents.dao.entities.Documents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentsRepository extends JpaRepository<Documents, Long> {

                @Query("SELECT d FROM Documents d " +
                        "WHERE (:titre IS NULL OR LOWER(d.titre) LIKE LOWER(CONCAT('%', :titre, '%'))) " +
                        "AND (:motsCles IS NULL OR LOWER(d.motsCles) LIKE LOWER(CONCAT('%', :motsCles, '%'))) " +
                        "AND (:themeId IS NULL OR d.theme.id = :themeId) " +
                        "AND (:auteurId IS NULL OR d.auteur.id = :auteurId)")
                List<Documents> searchDocuments(@Param("titre") String titre,
                                                @Param("motsCles") String motsCles,
                                                @Param("themeId") Long themeId,
                                                @Param("auteurId") Long auteurId);
        }


