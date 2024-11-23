package com.project.documents.dao.repositories;

import com.project.documents.dao.entities.Documents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentsRepository extends JpaRepository<Documents, Long> {
        List<Documents> findByTitreOrMotsCles(String titre, String motsCles);
        List<Documents> findByTitreOrMotsClesOrThemeId(String titre, String motsCles, Long themeId);
        List<Documents> findByTitreOrMotsClesAndAuteurId(String titre, String motsCles, Long auteurId);
        List<Documents> findByTitreOrMotsClesOrThemeIdAndAuteurId(String titre, String motsCles, Long themeId, Long auteurId);
}
