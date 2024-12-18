package com.project.documents.web.controllers;


import com.project.documents.business.services.AuteurService;
import com.project.documents.business.services.DocumentsService;
import com.project.documents.dao.entities.Documents;
import com.project.documents.business.services.ThemeService;
import com.project.documents.web.models.requests.DocumentForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

@Slf4j
@Controller
    @RequestMapping("/documents")
@RequiredArgsConstructor
    public class DocumentsController {
    private final DocumentsService documentService;
    private final AuteurService auteurService;
    private final ThemeService themeService;


    @GetMapping
        public String listDocuments(Model model) {
        log.info("Fetching the list of documents");
            model.addAttribute("documents", documentService.getAllDocuments());
            model.addAttribute("auteurs", auteurService.getAllAuteurs());
        model.addAttribute("themes", themeService.getAllThemes());
        model.addAttribute("isSearchPage", false);
            return "documents/listdoc";
        }

    @GetMapping("/add")
    public String showAddDocumentForm(Model model) {
        DocumentForm documentForm = new DocumentForm();
        List<String> fileTypes = List.of("PDF", "DOCX", "TXT", "ODT");

        model.addAttribute("documentForm", documentForm);
        model.addAttribute("auteurs", auteurService.getAllAuteurs());
        model.addAttribute("themes", themeService.getAllThemes());
        model.addAttribute("fileTypes", fileTypes);

        return "documents/adddoc";
    }

    @PostMapping("/add")
    public String addDocument(@Valid @ModelAttribute DocumentForm documentForm,
                              BindingResult bindingResult,
                              Model model,
                              RedirectAttributes redirectAttributes) {
        log.info("Adding a new document: {}", documentForm.getTitre());
        MultipartFile file = documentForm.getFichier();
        if (file.isEmpty() || !isAllowedFileType(file.getOriginalFilename())) {
            bindingResult.rejectValue("fichier", "error.documentForm", "Invalid file type. Only PDF, DOCX, TXT, and ODT files are allowed.");
        }
        if (bindingResult.hasErrors()) {
            log.warn("Validation errors occurred while adding a document: {}", bindingResult.getAllErrors());
            model.addAttribute("auteurs", auteurService.getAllAuteurs());
            model.addAttribute("themes", themeService.getAllThemes());
            model.addAttribute("fileTypes", List.of("PDF", "DOCX", "TXT", "ODT"));
            return "documents/adddoc";
        }
        try {
            documentService.addDocument(documentForm);
            redirectAttributes.addFlashAttribute("success", "Document ajouté avec succès.");
            log.info("Document added successfully: {}", documentForm.getTitre());
        } catch (Exception e) {
            log.error("Error occurred while adding document", e);
            redirectAttributes.addFlashAttribute("error", "Erreur lors de l'ajout du document.");
        }
        return "redirect:/documents";
    }


    private boolean isAllowedFileType(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return false;
        }
        String fileExtension = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();
        return List.of("pdf", "docx", "txt", "odt").contains(fileExtension);
    }
    @GetMapping("/edit/{id}")
    public String showEditDocumentForm(@PathVariable Long id, Model model) {
        Documents document = documentService.getDocumentById(id);
        DocumentForm documentForm = new DocumentForm();
        documentForm.setTitre(document.getTitre());
        documentForm.setMotsCles(document.getMotsCles());
        documentForm.setThemeId(document.getTheme().getId());
        documentForm.setAuteurId(document.getAuteur().getId());
        documentForm.setTypeFichier(document.getTypeFichier());
        documentForm.setResume(document.getResume());
        documentForm.setPublicationDate(document.getPublicationDate());

        List<String> fileTypes = List.of("PDF", "DOCX", "TXT", "ODT");

        model.addAttribute("documentForm", documentForm);
        model.addAttribute("document", document);
        model.addAttribute("auteurs", auteurService.getAllAuteurs());
        model.addAttribute("themes", themeService.getAllThemes());
        model.addAttribute("fileTypes", fileTypes);
        return "documents/editdoc";
    }
    @PostMapping("/edit/{id}")
    public String updateDocument(@PathVariable Long id,
                                 @Valid @ModelAttribute DocumentForm documentForm,
                                 BindingResult bindingResult,
                                 @RequestParam("fichier") MultipartFile uploadedFile,
                                 Model model,
                                 RedirectAttributes redirectAttributes) {
        try {
            log.info("Updating document with ID: {}", id);
            if (bindingResult.hasErrors()) {
                model.addAttribute("document", documentService.getDocumentById(id));
                model.addAttribute("auteurs", auteurService.getAllAuteurs());
                model.addAttribute("themes", themeService.getAllThemes());
                model.addAttribute("fileTypes", List.of("PDF", "DOCX", "TXT", "ODT"));
                return "documents/editdoc";
            }
            documentService.updateDocument(id, documentForm, uploadedFile);
            log.info("Document with ID: {} successfully updated.", id);
            redirectAttributes.addFlashAttribute("success", "Document mis à jour avec succès.");
            return "redirect:/documents";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/documents/edit/" + id;
        } catch (Exception e) {
            log.error("Unexpected error occurred while updating document with ID: {}", id, e);
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la mise à jour du document.");
            return "redirect:/documents/edit/" + id;
        }
    }


    @GetMapping("/delete/{id}")
    public String deleteDocument(@PathVariable Long id,RedirectAttributes redirectAttributes) {
        log.info("Attempting to delete document with ID: {}", id);
        log.info("Document with ID: {} deleted successfully", id);
        try {
        documentService.deleteDocument(id);
    redirectAttributes.addFlashAttribute("success", "Document deleted successfully.");
            log.info("Document with ID: {} deleted successfully", id);
        } catch (Exception e) {
            log.error("Error occurred while deleting document ID: {}", id, e);
            redirectAttributes.addFlashAttribute("error", "An error occurred while deleting the document.");
        }
        return "redirect:/documents";
    }

    @PostMapping("/search")
    public String searchDocuments(@RequestParam(required = false) String titre,
                                  @RequestParam(required = false) String motsCles,
                                  @RequestParam(required = false) Long themeId,
                                  @RequestParam(required = false) Long auteurId,
                                  Model model) {
        List<Documents> documents = documentService.searchDocuments(titre, motsCles, themeId, auteurId);

        if (documents.isEmpty()) {
            model.addAttribute("message", "Aucun document trouvé.");
        }
        model.addAttribute("documents", documents);
        model.addAttribute("auteurs", auteurService.getAllAuteurs());
        model.addAttribute("themes", themeService.getAllThemes());
        model.addAttribute("isSearchPage", true);
        return "documents/listdoc";
    }
    }


