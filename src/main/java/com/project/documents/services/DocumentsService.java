package com.project.documents.services;

import com.project.documents.dao.entities.Documents;
import com.project.documents.dao.repositories.DocumentsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentsService {

        @Autowired
        private DocumentsRepository documentRepository;

        public List<Documents> getAllDocuments() {
            return documentRepository.findAll();
        }
    public Documents getDocumentById(Long id) {
        return documentRepository.findById(id).orElse(null);
    }
        public void saveDocument(Documents document) {
            documentRepository.save(document);
        }

        public void deleteDocument(Long id) {
            documentRepository.deleteById(id);
        }
    public void updateDocument(Long id, Documents updatedDocument) {
        Documents document = documentRepository.findById(id).orElse(null);
        if (document != null) {
            document.setTitre(updatedDocument.getTitre());
            document.setMotsCles(updatedDocument.getMotsCles());
            document.setTheme(updatedDocument.getTheme());
            document.setResume(updatedDocument.getResume());
            document.setPublicationDate(updatedDocument.getPublicationDate());
            document.setTypeFichier(updatedDocument.getTypeFichier());
            document.setAuteur(updatedDocument.getAuteur());
            documentRepository.save(document);
        }}

        public List<Documents> searchDocuments(String titre, String motsCles, String theme, Long auteurId) {
            if (auteurId != null) {
                return documentRepository.findByTitreOrMotsClesOrThemeAndAuteurId(titre, motsCles, theme, auteurId);
            }
            return documentRepository.findByTitreOrMotsClesOrTheme(titre, motsCles, theme);
        }
    }


