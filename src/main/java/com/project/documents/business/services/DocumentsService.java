package com.project.documents.business.services;

import com.project.documents.dao.entities.Documents;
import com.project.documents.web.models.requests.DocumentForm;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface DocumentsService {

    List<Documents> getAllDocuments();

    Documents getDocumentById(Long id);

    void addDocument(DocumentForm documentForm);
    void deleteDocument(Long id);

    void updateDocument(Long id, DocumentForm documentForm, MultipartFile uploadedFile);
    List<Documents> searchDocuments(String titre, String motsCles, Long themeId, Long auteurId);
}



