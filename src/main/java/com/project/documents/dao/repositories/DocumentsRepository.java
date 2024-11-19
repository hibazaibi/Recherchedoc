package com.project.documents.dao.repositories;

import com.project.documents.dao.entities.Documents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentsRepository extends JpaRepository<Documents, Long> {
        List<Documents> findByTitreOrMotsClesOrTheme(String titre, String motsCles, String theme);
        List<Documents> findByTitreOrMotsClesOrThemeAndAuteurId(String titre, String motsCles, String theme, Long auteurId);
}
