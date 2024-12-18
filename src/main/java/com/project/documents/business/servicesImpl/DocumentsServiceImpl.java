package com.project.documents.business.servicesImpl;

import com.project.documents.business.services.AuteurService;
import com.project.documents.business.services.DocumentsService;
import com.project.documents.business.services.ThemeService;
import com.project.documents.dao.entities.Auteur;
import com.project.documents.dao.entities.Documents;
import com.project.documents.dao.entities.File;
import com.project.documents.dao.entities.Theme;
import com.project.documents.dao.repositories.DocumentsRepository;
import com.project.documents.web.models.requests.DocumentForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentsServiceImpl implements DocumentsService {
        private final DocumentsRepository documentRepository;
        private final ThemeService themeService;
    private final FileServiceImpl fileService;
    private final AuteurService auteurService;

    @Override
    public List<Documents> getAllDocuments() {
            return documentRepository.findAll();
        }
    @Override

    public Documents getDocumentById(Long id) {
        return documentRepository.findById(id).orElse(null);
    }
    @Override

    public void addDocument(DocumentForm documentForm) {
        Documents document = new Documents();
        document.setTitre(documentForm.getTitre());
        document.setMotsCles(documentForm.getMotsCles());
        document.setResume(documentForm.getResume());
        document.setTypeFichier(documentForm.getTypeFichier());
        document.setPublicationDate(documentForm.getPublicationDate());

        Auteur auteur = auteurService.getAuteurById(documentForm.getAuteurId());
        document.setAuteur(auteur);

        Theme theme = themeService.getThemeById(documentForm.getThemeId());
        document.setTheme(theme);

        if (!documentForm.getFichier().isEmpty()) {
            File attachment = null;
            try {
                attachment = fileService.saveAttachment(documentForm.getFichier());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            document.setFichier(attachment);
        }

        documentRepository.save(document);
    }
    @Override

        public void deleteDocument(Long id) {
            documentRepository.deleteById(id);
        }

    public void updateDocument(Long id, DocumentForm documentForm, MultipartFile uploadedFile) {
        Documents document = documentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Document not found with ID: " + id));

        document.setTitre(documentForm.getTitre());
        document.setMotsCles(documentForm.getMotsCles());
        document.setResume(documentForm.getResume());
        document.setTypeFichier(documentForm.getTypeFichier());
        document.setPublicationDate(documentForm.getPublicationDate());

        if (documentForm.getAuteurId() != null) {
            Auteur auteur = auteurService.getAuteurById(documentForm.getAuteurId());
            document.setAuteur(auteur);
        }

        if (documentForm.getThemeId() != null) {
            Theme theme = themeService.getThemeById(documentForm.getThemeId());
            document.setTheme(theme);
        }

        if (uploadedFile != null && !uploadedFile.isEmpty()) {
            if (document.getFichier() != null) {
                File oldFile = document.getFichier();
                document.setFichier(null);
                documentRepository.save(document);
                try {
                    fileService.deleteAttachment(oldFile.getId());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            File newFile = null;
            try {
                newFile = fileService.saveAttachment(uploadedFile);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            document.setFichier(newFile);
        }

        documentRepository.save(document);
    }
    @Override
    public List<Documents> searchDocuments(String titre, String motsCles, Long themeId, Long auteurId) {
        return documentRepository.searchDocuments(titre, motsCles, themeId, auteurId);
    }


    }


